package entity;

import java.nio.file.Path;


public class XmlEntity extends ClassEntity{
    private String layoutName;
    public XmlEntity(Path path) {
        super(path);
    }


    public void setLayoutName(String layoutName) {

        String className = layoutName.substring(layoutName.lastIndexOf(".")+1);

        // set the package value
        if (layoutName.lastIndexOf(".") >= 1){
            String packageName = layoutName.substring(layoutName.lastIndexOf(".")-1);

            this.setPackageName(packageName);

        }else {
            this.setPackageName("null");

        }

        this.layoutName = className;
    }
    public String getLayoutName() {
        return layoutName;
    }

    @Override
    public String getPackageName() {

        return layoutName;
    }

//    private String[] spilteString(String layoutName){
//
//        return ["",""];
//
//        }
}
