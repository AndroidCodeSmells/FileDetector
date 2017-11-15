import entity.ClassEntity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String fileExtension = "java";
        final String rootDirectory = "C:\\Projects\\AndroidCodeSmells\\FileDetector\\src\\main\\java\\entity";
        FileAnalyzer fileAnalyzer = FileAnalyzer.createFileAnalyzer();
        ResultsWriter resultsWriter = ResultsWriter.createResultsWriter();
        ClassEntity classEntity;

        //recursively identify all files with the specified extension in the specified directory
        Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Started);
        FileWalker fw = new FileWalker();
        List<Path> files = fw.getFiles(rootDirectory, true,fileExtension);
        Util.writeOperationLogEntry("Identify all '"+fileExtension+"' test files", Util.OperationStatus.Completed);


        //foreach of the identified 'java' files, obtain details about the methods that they contain
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
        for (Path file : files) {
            try {
                classEntity = fileAnalyzer.runAnalysis(file);
                resultsWriter.outputToCSV(classEntity);
            } catch (Exception e) {
                Util.writeException(e, "File: " + file.toAbsolutePath().toString());
            }
        }
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);


        resultsWriter.closeOutputFiles();
    }
}
