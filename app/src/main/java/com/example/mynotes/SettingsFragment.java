package com.example.mynotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        initSwitchBackStack(view);
        initRadioButtonAdd(view);
        initRadioButtonReplace(view);
        initSwitchBackAsRemove(view);
        initSwitchDeleteBeforeAdd(view);
    }

    private void initSwitchBackStack(View view) {
        SwitchCompat switchBackStack = view.findViewById(R.id.switchBackStack);

        switchBackStack.setChecked(Settings.isBackStack);
        switchBackStack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackStack = isChecked;
            writeSettings();
        });
    }

    private void initRadioButtonAdd(View view) {
        RadioButton rbAdd = view.findViewById(R.id.rbAdd);

        rbAdd.setChecked(Settings.isAddFragment);
        rbAdd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isAddFragment = isChecked;
            writeSettings();
        });
    }

    private void initRadioButtonReplace(View view) {
        RadioButton rbReplace = view.findViewById(R.id.rbReplace);

        rbReplace.setChecked(!Settings.isAddFragment);
        rbReplace.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isAddFragment = !isChecked;
            writeSettings();
        });
    }

    private void initSwitchBackAsRemove(View view) {
        SwitchCompat switchBackAsRemove = view.findViewById(R.id.switchBackAsRemove);

        switchBackAsRemove.setChecked(Settings.isBackAsRemove);
        switchBackAsRemove.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackAsRemove = isChecked;
            writeSettings();
        });
    }

    private void initSwitchDeleteBeforeAdd(View view) {
        SwitchCompat switchDeleteBeforeAdd = view.findViewById(R.id.switchDeleteBeforeAdd);

        switchDeleteBeforeAdd.setChecked(Settings.isDeleteBeforeAdd);
        switchDeleteBeforeAdd.setOnCheckedChangeListener((buttonView, isChecked) ->{
            Settings.isDeleteBeforeAdd = isChecked;
            writeSettings();
        });
    }

    private void writeSettings() {
        requireActivity()
                .getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment)
                .putBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, Settings.isBackAsRemove)
                .putBoolean(Settings.IS_BACK_STACK_USED, Settings.isBackStack)
                .putBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, Settings.isDeleteBeforeAdd)
                .apply();
    }
}