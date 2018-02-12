package entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ClassEntity {
    private Path path;
    private List<MethodEntity> methods;
    private ArrayList<String> imports;
    private String className;


    private String ClassPackageName;


    public ClassEntity(Path path) {
        this.path = path;
    }

    public List<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public void setImports(ArrayList<String> imports) {
        this.imports = imports;
    }

    public long getTotalImports() {
        return imports.stream().distinct().count();
    }

    public int getTotalMethods(){return methods.size();}

    public String getFilePath() {
        return path.toAbsolutePath().toString();
    }

    public long getTotalMethodStatement() {
        return methods.stream().mapToLong(i -> i.getTotalStatements()).sum();
    }

    public String getFileName() {
        return path.getFileName().toString();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className){this.className = className;}

    public String getPackageName() {
        return ClassPackageName;
    }

    public void setPackageName(String classPackageName) {
        ClassPackageName = classPackageName;
    }

    public String getFileNameWithoutExtension() {
        String fileName = path.getFileName().toString().substring(0, path.getFileName().toString().toLowerCase().lastIndexOf(".java"));
        return fileName;
    }


    public String getRelativeFilePath() {
        String filePath = path.toAbsolutePath().toString();
        String[] splitString = filePath.split("\\\\");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(splitString[i] + "\\");
        }
        return filePath.substring(stringBuilder.toString().length()).replace("\\", "/");
    }

    public String getAppName() {
        String filePath = path.toAbsolutePath().toString();
        return filePath.split("\\\\")[3];
    }

    public String getTagName() {
        String filePath = path.toAbsolutePath().toString();

        return filePath.split("\\\\")[4];
    }
}
