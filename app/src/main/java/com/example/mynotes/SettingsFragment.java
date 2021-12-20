package com.example.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SettingsFragment extends Fragment implements OnBackPressedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onBackPressed() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}