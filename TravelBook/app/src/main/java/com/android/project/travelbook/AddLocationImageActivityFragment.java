package com.android.project.travelbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anu on 06-09-2016.
 */
public class AddLocationImageActivityFragment extends android.support.v4.app.Fragment {
    public AddLocationImageActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_addlocation_images, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
