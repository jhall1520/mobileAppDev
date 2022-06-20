package com.example.group14_inclass10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * Group14_InClass10
 * GradesFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class GradesFragment extends Fragment {

    private static final String ARG_COURSES = "courses";

    private GListener gListener;
    private ArrayList<Course> courses;
    TextView gpa;
    TextView totalHours;
    double curGPA;
    double curTotalHours;
    RecyclerView recyclerView;
    CourseAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public GradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static GradesFragment newInstance(ArrayList<Course> param1) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSES, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courses = (ArrayList<Course>) getArguments().getSerializable(ARG_COURSES);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        ((MainActivity) getActivity()).setHeaderTitle("Grades");
        gpa = view.findViewById(R.id.textViewGPA);
        totalHours = view.findViewById(R.id.textViewTotalHours);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CourseAdapter(courses, (CourseAdapter.CListener) getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (courses != null && !courses.isEmpty()) {
            calculateGPA();
            gpa.setText("GPA: " + String.format("%.2f", curGPA));
            totalHours.setText("Hours: " + curTotalHours);

        }

        view.findViewById(R.id.imageViewAddCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gListener.goToAddCourseFragment();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof GListener) {
            gListener = (GListener) context;
        } else {
            throw new IllegalStateException("MainActivity does not implement GListener");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
        if (courses != null && !courses.isEmpty()) {
            calculateGPA();
            gpa.setText("GPA: " + String.format("%.2f", curGPA));
            totalHours.setText("Hours: " + curTotalHours);
        }
        adapter.notifyDataSetChanged();
    }

    public void calculateGPA() {
        double totalGradePoints = 0;
        curTotalHours = 0;
        for (int i = 0; i < courses.size(); i++) {
            Course curCourse = courses.get(i);
            String curGrade = curCourse.getCourseGrade();
            double curCredits = Double.parseDouble(curCourse.getCredits());
            curTotalHours += curCredits;

            if (curGrade.equals("A")) {
                totalGradePoints += curCredits * 4;
            } else if (curGrade.equals("B")) {
                totalGradePoints += (curCredits * 3);
            } else if (curGrade.equals("C")) {
                totalGradePoints += (curCredits * 2);
            } else if (curGrade.equals("D")) {
                totalGradePoints += curCredits;
            }
        }
        curGPA = totalGradePoints / curTotalHours;
    }

    public interface GListener {
        void goToAddCourseFragment();
    }
}