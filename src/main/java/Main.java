import entity.ClassEntity;
import entity.XmlEntity;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;


public class Main {
    public static long getDateDiff(long timeUpdate) {
        return  Math.abs(System.currentTimeMillis() - timeUpdate);
    }
    public static void main(String[] args) throws IOException {
        String fileExtension = "java";
        String secondFileExtension = "xml";


        FileAnalyzer fileAnalyzer = FileAnalyzer.createFileAnalyzer();
        XmlFileAnalyzer xmlfileAnalyzer = XmlFileAnalyzer.createFileAnalyzer();

        ResultsWriter resultsWriter = ResultsWriter.createResultsWriter();
        ClassEntity classEntity;
        XmlEntity xmlEntity;


        File rootDirectory = new File("G:\\Android\\Apps");
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        File[] directorie = rootDirectory.listFiles( filter);
//        System.out.println(directorie.length);
//
        File[] p1 = Arrays.copyOfRange(directorie, Integer.valueOf(args[0]), Integer.valueOf(args[1]));

        File[] directories = p1;

        for (File dir: directories){

            //recursively identify all files with the specified extension in the specified directory

            Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Started);
            FileWalker fw = new FileWalker();
            List<List<Path>> files = fw.getFiles(dir.getPath(),true,fileExtension,secondFileExtension);
            Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Completed);


            //foreach of the identified 'java' files, obtain details about the methods that they contain
//            Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
//            for (Path file : files.get(0)) {
//                try {
//                    classEntity = fileAnalyzer.runAnalysis(file);
//                    resultsWriter.outputToCSV(classEntity);
//                } catch (Exception e) {
//                    Util.writeException(e, "File: " + file.toAbsolutePath().toString());
//                }
//            }
//            Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);

            Util.writeOperationLogEntry("Obtain Xml details"+files.get(1).size(), Util.OperationStatus.Started);
            for (Path file : files.get(1)) {
                try {


                    if (file.toAbsolutePath().toString().toLowerCase().contains("\\res\\")
                            || file.getFileName().toString().equalsIgnoreCase("AndroidManifest.xml")
                            || file.toAbsolutePath().toString().toLowerCase().contains("\\resources\\")) {


                            if (file.toAbsolutePath().toString().toLowerCase().contains("\\layout\\")
                                    ||  file.getFileName().toString().equalsIgnoreCase("AndroidManifest.xml")){

                                xmlEntity = xmlfileAnalyzer.runAnalysis(file);

                                resultsWriter.outputXmlToCSV(xmlEntity);

                            }

                    }


                } catch (Exception e) {
                    Util.writeException(e, "XML: " + file.toAbsolutePath().toString());
                }
            }
            Util.writeOperationLogEntry("Obtain Xml details", Util.OperationStatus.Completed);


        }








        resultsWriter.closeOutputFiles();
    }
}
