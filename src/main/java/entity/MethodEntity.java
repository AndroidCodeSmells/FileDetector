package entity;

public class MethodEntity {
    private String methodName;
    private String accessModifier;
    private int totalStatements;
    private int parameterCount;
    private String returnType;

    public MethodEntity(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getTotalStatements() {
        return totalStatements;
    }

    public void setTotalStatements(int totalStatements) {
        this.totalStatements = totalStatements;
    }

    public int getParameterCount(){return  parameterCount;}

    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    public String getReturnType(){return returnType;}

    public void setReturnType(String returnType){this.returnType = returnType;}

    public String getAccessModifier(){return accessModifier;}

    public void setAccessModifier(String accessModifier){this.accessModifier = accessModifier;}
}
