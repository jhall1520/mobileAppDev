package com.example.group14_inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Group14_InClass10
 * AddCourseFragment.java
 * Joel Hall
 * Jimmy Kropp
 */
public class AddCourseFragment extends Fragment {


    private String grade = "A";
    private AListener aListener;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        ((MainActivity) getActivity()).setHeaderTitle("Add Course");

        EditText courseNumber = view.findViewById(R.id.editTextCourseNum);
        EditText courseName = view.findViewById(R.id.editTextCourseName);
        EditText creditHours = view.findViewById(R.id.editTextCreditHours);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupGrades);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonA:
                        grade = "A";
                        return;
                    case R.id.radioButtonB:
                        grade = "B";
                        return;
                    case R.id.radioButtonC:
                        grade = "C";
                        return;
                    case R.id.radioButtonD:
                        grade = "D";
                        return;
                    case R.id.radioButtonF:
                        grade = "F";
                }
            }
        });

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNum = courseNumber.getText().toString();
                String courseNameString = courseName.getText().toString();
                String credits = creditHours.getText().toString();

                if (courseNum.equals("")) {
                    Toast.makeText(getActivity(), "Enter the course number.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (courseNameString.equals("")) {
                    Toast.makeText(getActivity(), "Enter the course name.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (credits.equals("")) {
                    Toast.makeText(getActivity(), "Enter the credit hours.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Course course = new Course(courseNum, courseNameString, credits, grade);
                aListener.passNewCourseToDatabase(course);

            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aListener.goBackToGradesFragment();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AListener) {
            aListener = (AListener) context;
        } else {
            throw new IllegalStateException("MainActivity does not implement AListener");
        }
    }

    public interface AListener {
        void passNewCourseToDatabase(Course course);
        void goBackToGradesFragment();
    }
}