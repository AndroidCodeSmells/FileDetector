import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.Name;
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

    private void initialize() {
        methods = new ArrayList<>();
        imports = new ArrayList<>();
    }

    public ClassEntity runAnalysis(Path filePath) throws IOException {
        initialize();

        CompilationUnit compilationUnit = null;

        classEntity = new ClassEntity(filePath);

        // disable to get the file details
        fileInputStream = new FileInputStream(classEntity.getFilePath());
        compilationUnit = JavaParser.parse(fileInputStream);


        FileAnalyzer.ClassVisitor classVisitor;
        classVisitor = new FileAnalyzer.ClassVisitor();

        classVisitor.visit(compilationUnit, null);

        classEntity.setMethods(methods);
        classEntity.setImports(imports);


        return classEntity;
    }


    private class ClassVisitor extends VoidVisitorAdapter<Void> {


        @Override
        public void visit(PackageDeclaration n, Void arg) {
            classEntity.setPackageName(n.getNameAsString());
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {

            if (!n.isInterface()){
                classEntity.setClassName(n.getNameAsString());
            }else {
                classEntity.setClassName(n.getNameAsString());

            }

            super.visit(n, arg);



        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {


            MethodEntity method = new MethodEntity(n.getNameAsString());
            if (n.getBody().isPresent()) {
                method.setTotalStatements(n.getBody().get().getStatements().size());
                method.setIsConcrete("true");

            }else {
                method.setIsConcrete("false");
            }
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

