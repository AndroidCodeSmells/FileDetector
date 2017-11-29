import entity.ClassEntity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String fileExtension = "java";
        String secondFileExtension = "xml";

        final String rootDirectory = "\\\\Mac\\Home\\Desktop\\SampleTesting";
        FileAnalyzer fileAnalyzer = FileAnalyzer.createFileAnalyzer();
        ResultsWriter resultsWriter = ResultsWriter.createResultsWriter();
        ClassEntity classEntity;
        ClassEntity xmlEntity;

        //recursively identify all files with the specified extension in the specified directory
        Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Started);
        FileWalker fw = new FileWalker();
        List<List<Path>> files = fw.getFiles(rootDirectory, true,fileExtension,secondFileExtension);
        Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Completed);


        //foreach of the identified 'java' files, obtain details about the methods that they contain
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
        for (Path file : files.get(0)) {
            try {
                classEntity = fileAnalyzer.runAnalysis(file);
                resultsWriter.outputToCSV(classEntity);
            } catch (Exception e) {
                Util.writeException(e, "File: " + file.toAbsolutePath().toString());
            }
        }
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);

        Util.writeOperationLogEntry("Obtain Xml details", Util.OperationStatus.Started);

        for (Path file : files.get(1)) {
            try {

                xmlEntity = new ClassEntity(file);
                resultsWriter.outputXmlToCSV(xmlEntity);

            } catch (Exception e) {
               Util.writeException(e, "XML: " + file.toAbsolutePath().toString());
            }
        }
        Util.writeOperationLogEntry("Obtain Xml details", Util.OperationStatus.Completed);



        resultsWriter.closeOutputFiles();
    }
}
