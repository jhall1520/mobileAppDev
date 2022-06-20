package com.example.group14_inclass10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Group14_InClass10
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MainActivity extends AppCompatActivity implements AddCourseFragment.AListener,
        GradesFragment.GListener, CourseAdapter.CListener {

    private AppDatabase db;
    private ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courses = new ArrayList<>();
        db = Room.databaseBuilder(this, AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        courses = (ArrayList<Course>) db.courseDao().getAll();

        getSupportActionBar().setTitle("MainActivity");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, GradesFragment.newInstance(courses), "grades")
                .commit();

    }

    @Override
    public void passNewCourseToDatabase(Course course) {
        db.courseDao().insertAll(course);
        courses = (ArrayList<Course>) db.courseDao().getAll();
        GradesFragment gradesFragment = (GradesFragment) getSupportFragmentManager().findFragmentByTag("grades");
        gradesFragment.setCourses(courses);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goBackToGradesFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToAddCourseFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AddCourseFragment(), "course")
                .addToBackStack(null)
                .commit();
    }

    public void setHeaderTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void deleteCourse(Course course) {
        courses.remove(course);
        db.courseDao().delete(course);
        GradesFragment gradesFragment = (GradesFragment) getSupportFragmentManager().findFragmentByTag("grades");
        gradesFragment.setCourses(courses);

    }
}