package com.example.group14_inclass05;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Group14_InClass05
 * AppNamesFragment.java
 * Joel Hall
 * Jimmy Kropp
 */

public class AppNamesFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";

    String category;
    ListView listView;
    AppAdapter appAdapter;
    ArrayList<DataServices.App> appNames = new ArrayList<>();
    NListener nListener;


    public AppNamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1
     * @return A new instance of fragment TopPaidAppsFragment.
     */
    public static AppNamesFragment newInstance(String category) {
        AppNamesFragment fragment = new AppNamesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps_names, container, false);
        appNames = DataServices.getAppsByCategory(category);
        listView = view.findViewById(R.id.listViewAppNames);
        appAdapter = new AppAdapter(getContext(), R.layout.apps_layout, appNames);
        listView.setAdapter(appAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nListener.startAppDetailsFragment(appNames.get(i));
            }
        });

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((MainActivity) getActivity()).setActionBar(category);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(category);
    }

    public class AppAdapter extends ArrayAdapter<DataServices.App> {

        public AppAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<DataServices.App> objects) {
            super(context, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.apps_layout, parent, false);
            }

            DataServices.App app = getItem(position);

            TextView appName = convertView.findViewById(R.id.textViewAppName);
            TextView artistName = convertView.findViewById(R.id.textViewArtistName);
            TextView releaseDate = convertView.findViewById(R.id.textViewReleaseDate);

            appName.setText(app.name);
            artistName.setText(app.artistName);
            releaseDate.setText(app.releaseDate);

            return convertView;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof NListener) {
            nListener = (NListener) context;
        } else {
            throw new IllegalArgumentException("MainActivity needs to implement CListener from AppCategoriesFragment.java");
        }
    }

    public interface NListener {
        void startAppDetailsFragment(DataServices.App app);
    }
}