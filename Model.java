package com.example.todo_list;

public class Model {
    private   int classId;
   private String className;
  private String profName;
  private Boolean isActive;

    public Model( int classId,String className, String profName, Boolean isActive) {
        this.classId = classId;
        this.className = className;
        this.profName = profName;
        this.isActive = isActive;
    }


    public Model() {
    }

    @Override
    public String toString() {
//        return "Model{" +
//                "classId='" + classId + '\'' +
//                "className='" + className + '\'' +
//                ", profName='" + profName + '\'' +
//                ", isActive=" + isActive +
//                '}';
        return (getClassId()+" "+ getClassName() +" "+ getProfName()+ " "+ isActive());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        className = className;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }


    public boolean isActive() {
        return  isActive;
    }

    public void SetIsActive(boolean active){
        isActive = active;
    }
}
