package com.example.gridlayout;

public class PostItem {
    String tid,tname,content,date,courseCode,courseTitle;

    public PostItem(String tid, String tname, String content, String date, String courseCode, String courseTitle) {
        this.tid = tid;
        this.tname = tname;
        this.content = content;
        this.date = date;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
    }

    public PostItem() {
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
