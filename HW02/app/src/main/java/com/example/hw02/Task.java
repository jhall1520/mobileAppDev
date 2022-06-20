package com.example.hw02;

import java.io.Serializable;

/**
 * HW02
 * Task
 * Joel Hall
 * Jimmy Kropp
 **/

public class Task implements Serializable {


    private String name;
    private String date;
    private String priority;

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
