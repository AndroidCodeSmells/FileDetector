package entity;

import java.nio.file.Path;


public class XmlEntity extends ClassEntity{
    private String layoutName;
    public XmlEntity(Path path) {
        super(path);
    }

    public void setLayoutName(String layoutName) {
        System.out.println(layoutName);
        this.layoutName = layoutName;
    }

    public String getLayoutName() {
        return layoutName;
    }

    @Override
    public String getPackageName() {
        return layoutName;
    }
}
