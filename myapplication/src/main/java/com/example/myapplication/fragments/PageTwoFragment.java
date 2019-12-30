package com.example.myapplication.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ImageAdapter;
import com.example.myapplication.R;

public class PageTwoFragment extends Fragment {

    GridView gridView;

    public PageTwoFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.page_two_fragment,container,false);
        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        gridView = (GridView)rootview.findViewById(R.id.gridView1);
        gridView.setAdapter(imageAdapter);

        return rootview;
    }



}
