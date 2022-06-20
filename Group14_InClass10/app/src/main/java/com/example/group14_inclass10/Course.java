package com.example.group14_inclass10;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String courseNumber;

    @ColumnInfo
    public String courseName;

    @ColumnInfo
    public String credits;

    @ColumnInfo
    public String courseGrade;

    public Course() {
    }

    public Course(String courseNumber, String courseName, String credits, String courseGrade) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.credits = credits;
        this.courseGrade = courseGrade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }
}
