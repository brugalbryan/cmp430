package com.example.tabbedactivity;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GeneralCourses extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Course> lstCourses;



    public GeneralCourses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_courses_general, container, false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.courses_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstCourses);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity() ));
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstCourses = new ArrayList<>();
        String [] courseName = getResources().getStringArray(R.array.course_names);
        String [] numCredits = getResources().getStringArray(R.array.course_credits);
        String [] reqCourse = getResources().getStringArray(R.array.course_prerequisite);
        String [] courseProfs = getResources().getStringArray(R.array.course_profs);

        for (int i = 0; i < courseName.length ; i++) {
            lstCourses.add(new Course(courseName[i], numCredits[i], reqCourse[i], courseProfs[i], false));
        }

    }
}