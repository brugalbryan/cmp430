package com.example.tabbedactivity;

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
public class AdvancedCmp extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Course> lstCourses;



    public AdvancedCmp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_advanced_cmpcourses, container, false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstCourses);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity() ));
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstCourses = new ArrayList<>();
        String [] courseName = getResources().getStringArray(R.array.advanced_cmp_course_names);
        String [] numCredits = getResources().getStringArray(R.array.advanced_cmp_course_credits);
        String [] reqCourse = getResources().getStringArray(R.array.advanced_cmp_course_prerequisite);
        String [] courseProfs = getResources().getStringArray(R.array.advanced_cmp_course_profs);

        for (int i = 0; i < courseName.length ; i++) {
            lstCourses.add(new Course(courseName[i], numCredits[i], reqCourse[i], courseProfs[i], true));
        }

    }
}