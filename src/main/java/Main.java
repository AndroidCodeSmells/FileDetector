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


        File[] dictList = getDirectoriesList();



        File[] RangeOfDirectoriesList = Arrays.copyOfRange(dictList, Integer.valueOf(args[0]), Integer.valueOf(args[1]));

        File[] directories = RangeOfDirectoriesList;

        for (File dir: directories){

            //recursively identify all files with the specified extension in the specified directory

            Util.writeOperationLogEntry("Identify all '"+fileExtension+"' java files", Util.OperationStatus.Started);
            FileWalker fw = new FileWalker();
            List<List<Path>> files = fw.getFiles(dir.getPath(),true,fileExtension,secondFileExtension);
            Util.writeOperationLogEntry("Identify all '"+fileExtension+"' java files", Util.OperationStatus.Completed);


         //   foreach of the identified 'java' files, obtain details about the methods that they contain
            Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
            for (Path file : files.get(0)) {

                checkJavaFiles(fileAnalyzer, resultsWriter, file);

            }
            Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);


            Util.writeOperationLogEntry("Obtain Xml details"+files.get(1).size(), Util.OperationStatus.Started);
            for (Path file : files.get(1)) {

                checkXmlFiles(xmlfileAnalyzer, resultsWriter, file);
            }
            Util.writeOperationLogEntry("Obtain Xml details", Util.OperationStatus.Completed);


        }








        resultsWriter.closeOutputFiles();
    }

    public static File[] getDirectoriesList() {
        File rootDirectory = new File("E:\\P");
        FileFilter filter = pathname -> pathname.isDirectory();

        return rootDirectory.listFiles( filter);
    }

    private static void checkJavaFiles(FileAnalyzer fileAnalyzer, ResultsWriter resultsWriter, Path file) throws IOException {
        ClassEntity classEntity;
        try {
            classEntity = fileAnalyzer.runAnalysis(file);
            resultsWriter.outputToCSV(classEntity);
        } catch (Exception e) {
            Util.writeException(e, "File: " + file.toAbsolutePath().toString());
        }
    }

    private static void checkXmlFiles(XmlFileAnalyzer xmlfileAnalyzer, ResultsWriter resultsWriter, Path file) throws IOException {
        XmlEntity xmlEntity;
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
}
