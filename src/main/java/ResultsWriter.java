import com.opencsv.CSVWriter;
import entity.ClassEntity;
import entity.MethodEntity;
import entity.XmlEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ResultsWriter {

    private CSVWriter classCSVWriter, methodCSVWriter,xmlCSVWriter;

    public static ResultsWriter createResultsWriter() throws IOException {
        return new ResultsWriter();
    }

    private ResultsWriter() throws IOException {
        String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String classFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Class", time, "csv");
        String methodFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Method", time, "csv");
        String xmlFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Xml", time, "csv");

        methodCSVWriter = new CSVWriter(new FileWriter(methodFileName), ',');
        classCSVWriter = new CSVWriter(new FileWriter(classFileName), ',');
        xmlCSVWriter = new CSVWriter(new FileWriter(xmlFileName), ',');

        createClassFile();
        createMethodFile();
        createXmlFile();
    }

    private void createXmlFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "className"
        };
        fileLines.add(columnNames);

        xmlCSVWriter.writeAll(fileLines, false);
        xmlCSVWriter.flush();
    }
    private void createClassFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "ClassName",
                "TotalImports",
                "TotalMethods",
                "TotalMethodStatements",
                "PackageClassName"
        };
        fileLines.add(columnNames);

        classCSVWriter.writeAll(fileLines, false);
        classCSVWriter.flush();
    }

    private void createMethodFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "ClassName",
                "MethodName",
                "TotalStatements",
                "TotalParameters",
                "ReturnType",
                "AccessModifier",
                "IsConcrete"

        };
        fileLines.add(columnNames);

        methodCSVWriter.writeAll(fileLines, false);
        methodCSVWriter.flush();
    }

    public void outputToCSV(ClassEntity classEntity) throws IOException {
        outputClassDetails(classEntity);
        outputMethodDetails(classEntity);
    }
    public void outputXmlToCSV(XmlEntity xmlEntity) throws IOException {
        outputXmlDetails(xmlEntity);
    }
    public void closeOutputFiles() throws IOException {
        classCSVWriter.close();
        methodCSVWriter.close();

    }

    private void outputMethodDetails(ClassEntity classEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;
        for (MethodEntity methodEntity : classEntity.getMethods()) {

            dataLine = new String[12];
            dataLine[0] = classEntity.getAppName();
            dataLine[1] = classEntity.getTagName();
            dataLine[2] = classEntity.getFilePath();
            dataLine[3] = classEntity.getRelativeFilePath();
            dataLine[4] = classEntity.getFileName();
            dataLine[5] = classEntity.getClassName();
            dataLine[6] = methodEntity.getMethodName();
            dataLine[7] = String.valueOf(methodEntity.getTotalStatements());
            dataLine[8] = String.valueOf(methodEntity.getParameterCount());
            dataLine[9] = methodEntity.getReturnType();
            dataLine[10] = methodEntity.getAccessModifier();
            dataLine[11] = methodEntity.getIsConcrete();

            fileLines.add(dataLine);
        }
        methodCSVWriter.writeAll(fileLines, false);
        methodCSVWriter.flush();
    }

    private void  outputClassDetails(ClassEntity classEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;

        dataLine = new String[10];
        dataLine[0] = classEntity.getAppName();
        dataLine[1] = classEntity.getTagName();
        dataLine[2] = classEntity.getFilePath();
        dataLine[3] = classEntity.getRelativeFilePath();
        dataLine[4] = classEntity.getFileName();
        dataLine[5] = classEntity.getClassName();
        dataLine[6] = String.valueOf(classEntity.getTotalImports());
        dataLine[7] = String.valueOf(classEntity.getTotalMethods());
        dataLine[8] = String.valueOf(classEntity.getTotalMethodStatement());
        dataLine[9] = String.valueOf(classEntity.getPackageName());

        fileLines.add(dataLine);

        classCSVWriter.writeAll(fileLines, false);
        classCSVWriter.flush();
    }

    private void  outputXmlDetails(XmlEntity xmlEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;

        dataLine = new String[6];
        dataLine[0] = xmlEntity.getAppName();
        dataLine[1] = xmlEntity.getTagName();
        dataLine[2] = xmlEntity.getFilePath();
        dataLine[3] = xmlEntity.getRelativeFilePath();
        dataLine[4] = xmlEntity.getFileName();
        dataLine[5] = xmlEntity.getLayoutName();

        fileLines.add(dataLine);

        xmlCSVWriter.writeAll(fileLines, false);
        xmlCSVWriter.flush();
    }
}
