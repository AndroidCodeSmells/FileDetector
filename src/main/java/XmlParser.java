import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.util.ArrayList;
import java.util.List;

public class XmlParser {



    private Document document;
    private  boolean isWordList;

    public List<Node> getElements() {
        return elements;
    }

    List<Node> elements;

    public  XmlParser(String filePath) throws DocumentException {


        isWordList = false;

        SAXReader reader = new SAXReader();


        document = reader.read(filePath);

        Element rootElement = document.getRootElement();

        elements = document.selectNodes(rootElement.getName());




    }

    public  ElementCollection FindAttribute( String attributeName) throws DocumentException {
        List<Element> elementsWithAttribute = new ArrayList<>();
        List<Element> elementsWithoutAttribute = new ArrayList<>();
        boolean hasAttribute;


        for (Node element: elements) {
            hasAttribute = false;
            for(Attribute attribute: ((DefaultElement) element).attributes()){
                if(attribute.getName().equalsIgnoreCase(attributeName)){
                    if(attribute.getValue()!=null && !attribute.getValue().isEmpty()) {
                        hasAttribute = true;
                    }
                }
            }
            if (hasAttribute){
                elementsWithAttribute.add((DefaultElement) element);
            }
            else {
                elementsWithoutAttribute.add((DefaultElement) element);
            }
        }

        return new ElementCollection(elementsWithAttribute,elementsWithoutAttribute);
    }
    public ElementsCollection FindAttribute() throws DocumentException {
        List<Element> elementsWithAttribute = new ArrayList<>();

            for (Node element: elements) {

                elementsWithAttribute.add((DefaultElement) element);



            }



        return new ElementsCollection(elementsWithAttribute);
    }
    public  ElementCollection FindNode(String Name) throws DocumentException {
        List<Element> elementsWithAttribute = new ArrayList<>();
        List<Element> elementsWithoutAttribute = new ArrayList<>();
        boolean hasAttribute;



        for (Node element: elements) {
            hasAttribute = false;

            if (element.getName().equalsIgnoreCase(Name)){

                for(Attribute attribute: ((DefaultElement) element).attributes()){
                        if(attribute.getValue()!=null && !attribute.getValue().isEmpty()) {

                            hasAttribute = true;
                        }

                }

                if (hasAttribute){
                    elementsWithAttribute.add((DefaultElement) element);
                }
                else {
                    elementsWithoutAttribute.add((DefaultElement) element);
                }
            }


        }
        System.out.println(elementsWithAttribute.size());

        return new ElementCollection(elementsWithAttribute,elementsWithoutAttribute);
    }
    public static class ElementsCollection {
        public  ElementsCollection(List<Element> elementsWithAttribute){
            this.elementsWithAttribute = elementsWithAttribute;

        }
        List<Element> elementsWithAttribute = new ArrayList<>();


        public List<Element> getElementsWithAttribute(){
            return elementsWithAttribute;
        }

    }
    public static class ElementCollection{
        List<Element> elementsWithAttribute = new ArrayList<>();
        List<Element> elementsWithoutAttribute = new ArrayList<>();

        ElementCollection( List<Element> elementsWithAttribute,List<Element> elementsWithoutAttribute ){
            this.elementsWithAttribute = elementsWithAttribute;
            this.elementsWithoutAttribute = elementsWithoutAttribute;
        }

        public List<Element> getElementsWithAttribute(){
            return elementsWithAttribute;
        }

        public List<Element> getElementsWithoutAttribute(){
            return elementsWithoutAttribute;
        }
    }
}
