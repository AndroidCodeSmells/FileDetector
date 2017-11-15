import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileWalker {
    private List<Path> files;


    public List<Path> getFiles(String directoryPath, boolean recursive, String extension) throws IOException {
        files = new ArrayList<>();
        Path startDir = Paths.get(directoryPath);

        if (recursive) {
            Files.walkFileTree(startDir, new FindJavaFilesVisitor(extension));
        } else {
            Files.walk(startDir, 1)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        if (filePath.toString().toLowerCase().endsWith("."+extension)) {
                            files.add(filePath);
                        }
                    });
        }
        return files;
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
}


