import entity.XmlEntity;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class XmlFileAnalyzer {

    private XmlEntity classEntity;
    private FileInputStream fileInputStream;

    /**
     * Constructor
     */
    private XmlFileAnalyzer() {
        initialize();
    }

    /**
     * Factory method that provides a new instance of the FileAnalyzer
     *
     * @return new FileAnalyzer instance
     */
    public static XmlFileAnalyzer createFileAnalyzer() {
        return new XmlFileAnalyzer();
    }

    private void initialize(){

    }

    public XmlEntity runAnalysis(Path filePath) throws IOException, DocumentException {

        if(filePath != null){
            classEntity = new XmlEntity(filePath);
        }else {
            return classEntity;
        }
            XmlParser xmlParser = new XmlParser(filePath.toAbsolutePath().toString());


            XmlParser.ElementsCollection layoutClassName = xmlParser.FindAttribute();

            if (layoutClassName.getElementsWithAttribute().size() == 0){
                return classEntity;
            }

        Element className = layoutClassName.getElementsWithAttribute().stream().filter(x->x.attributeValue("context")!=null).findAny().orElse(null);

        if (className != null){

            classEntity.setLayoutName(className.attributeValue("context"));
            return classEntity;

        }else {
            classEntity.setLayoutName("null");

            return classEntity;
        }

    }

}
