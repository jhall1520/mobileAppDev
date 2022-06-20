package com.example.group14_inclass05;

/**
 * Group14_InClass05
 * AppCategoriesFragment.java
 * Joel Hall
 * Jimmy Kropp
 */

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AppCategoriesFragment extends Fragment {


    public AppCategoriesFragment() {
        // Required empty public constructor
    }
    CListener cListener;
    ListView listView;
    ArrayList<String> categories = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_app_categories, container, false);

        listView = view.findViewById(R.id.listViewCategories);
        categories = DataServices.getAppCategories();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category = categories.get(i);

                cListener.startAppNamesFragment(category);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CListener) {
            cListener = (CListener) context;
        } else {
            throw new IllegalArgumentException("MainActivity needs to implement CListener from AppCategoriesFragment.java");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("App Categories");

    }

    public interface CListener {
        void startAppNamesFragment(String category);
    }
}