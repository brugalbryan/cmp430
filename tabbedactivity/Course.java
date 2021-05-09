package com.example.tabbedactivity;

public class Course
{
    private String courseName;
    private String numCredits;
    private String reqCourse;
    private String courseProfs;
    private boolean isAdvanced;

    public Course(String courseName, String numCredits, String reqCourse, String courseProfs, boolean isAdvanced) {
        this.courseName = courseName;
        this.numCredits = numCredits;
        this.reqCourse = reqCourse;
        this.courseProfs = courseProfs;
        this.isAdvanced = isAdvanced;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(String numCredits) {
        this.numCredits = numCredits;
    }

    public String getReqCourse() {
        return reqCourse;
    }

    public void setReqCourse(String reqCourse) {
        this.reqCourse = reqCourse;
    }

    public String getCourseProfs() {
        return courseProfs;
    }

    public void setCourseProfs(String courseProfs) {
        this.courseProfs = courseProfs;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public void setAdvanced(boolean advanced) {
        isAdvanced = advanced;
    }
}
