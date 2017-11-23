import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import entity.ClassEntity;
import entity.MethodEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileAnalyzer {
    private List<MethodEntity> methods;
    private ArrayList<String> imports;
    private ClassEntity classEntity;
    private FileInputStream fileInputStream;

    /**
     * Constructor
     */
    private FileAnalyzer() {
        initialize();
    }

    /**
     * Factory method that provides a new instance of the FileAnalyzer
     *
     * @return new FileAnalyzer instance
     */
    public static FileAnalyzer createFileAnalyzer() {
        return new FileAnalyzer();
    }

    private void initialize(){
        methods = new ArrayList<>();
        imports = new ArrayList<>();
    }

    public ClassEntity runAnalysis(Path filePath) throws IOException {
        initialize();

        CompilationUnit compilationUnit=null;

        if(filePath != null){
            classEntity = new ClassEntity(filePath);
            fileInputStream = new FileInputStream(classEntity.getFilePath());
            compilationUnit = JavaParser.parse(fileInputStream);
            ClassVisitor cv = new ClassVisitor();
            cv.visit(compilationUnit,null);

            classEntity.setMethods(methods);
            classEntity.setImports(imports);
        }

        return classEntity;
    }


    private class ClassVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            classEntity.setClassName(n.getNameAsString());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            MethodEntity method = new MethodEntity(n.getNameAsString());
            method.setTotalStatements(n.getBody().get().getStatements().size());
            method.setParameterCount(n.getParameters().size());
            method.setReturnType(n.getType().toString());
            method.setAccessModifier(n.getModifiers().stream().map(i -> i.asString()).collect(Collectors.joining("; ")));

            methods.add(method);

            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            imports.add(n.getNameAsString());

            super.visit(n, arg);
        }
    }
}
