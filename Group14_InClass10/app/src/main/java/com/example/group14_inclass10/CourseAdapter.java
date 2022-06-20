package com.example.group14_inclass10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Group14_InClass10
 * CourseAdapter.java
 * Joel Hall
 * Jimmy Kropp
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    ArrayList<Course> courses;
    CListener cListener;

    public CourseAdapter(ArrayList<Course> courses, CListener cListener) {
        this.courses = courses;
        this.cListener = cListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_layout, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.grade.setText(course.getCourseGrade());
        holder.courseNum.setText(course.getCourseNumber());
        holder.courseName.setText(course.getCourseName());
        holder.credits.setText(course.getCredits() + " Credit Hours");

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cListener.deleteCourse(course);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView grade;
        TextView courseName;
        TextView courseNum;
        TextView credits;
        ImageView trash;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            grade = itemView.findViewById(R.id.textViewGrade);
            courseName = itemView.findViewById(R.id.textViewCourseName);
            courseNum = itemView.findViewById(R.id.textViewCourseNum);
            credits = itemView.findViewById(R.id.textViewCreditHours);
            trash = itemView.findViewById(R.id.imageViewTrash);
        }
    }
    public interface CListener {
        void deleteCourse(Course course);
    }
}
