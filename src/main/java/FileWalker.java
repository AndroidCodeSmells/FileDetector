import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileWalker {
    private List<Path> files;
    private List<Path>  SecondExtFiles;


    public List<List<Path>> getFiles(String directoryPath, boolean recursive, String extension, String SecondExtension) throws IOException {
        files = new ArrayList<>();
        SecondExtFiles = new ArrayList<>();
        ArrayList container = new ArrayList<>();

        Path startDir = Paths.get(directoryPath);

        if (recursive) {
            Files.walkFileTree(startDir, new FindJavaFilesVisitor(extension));
            Files.walkFileTree(startDir, new FindXmlFilesVisitor(SecondExtension));

        } else {
            Files.walk(startDir, 1)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        if (filePath.toString().toLowerCase().endsWith("."+extension)) {
                            files.add(filePath);
                        }
                        if (filePath.toString().toLowerCase().endsWith("."+SecondExtension)){
                            SecondExtFiles.add(filePath);

                        }

                    });
        }
        container.add(files);
        container.add(SecondExtFiles);

        return container;
    }

    private class FindJavaFilesVisitor extends SimpleFileVisitor<Path> {
        private String extension;

        FindJavaFilesVisitor(String extension) {
            this.extension = extension;
        }

        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs)
                throws IOException {

            if (file.toString().toLowerCase().endsWith("."+extension)) {
               files.add(file);
            }

            return FileVisitResult.CONTINUE;
        }
    }
    private class FindXmlFilesVisitor extends SimpleFileVisitor<Path> {
        private String extension;

        FindXmlFilesVisitor(String extension) {
            this.extension = extension;
        }

        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs)
                throws IOException {

            if (file.toString().toLowerCase().endsWith("."+extension)) {
                SecondExtFiles.add(file);
            }

            return FileVisitResult.CONTINUE;
        }
    }
}


