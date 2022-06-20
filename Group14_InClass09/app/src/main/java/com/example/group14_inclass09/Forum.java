package com.example.group14_inclass09;

/**
 * Group14_InClass09
 * Forum.java
 * Joel Hall
 * Jimmy Kropp
 */
public class Forum {

    String title;
    String userName;
    String description;
    String date;
    String documentId;

    public Forum(String title, String userName, String description, String date, String documentId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.userName = userName;
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
