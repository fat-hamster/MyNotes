package com.example.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListNotesFragment extends Fragment {

    private Notes notes = new Notes();
    private int currentNote = 0;
    public static String CURRENT_NOTE = "CURRENT_NOTE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            currentNote = savedInstanceState.getInt(CURRENT_NOTE);
        }
        initList(view);
    }

    private void initList(View view) {
        for (int i = 0; i < 10; i++) {
            int line = i + 1;
            notes.add("Line " + line, "Основные причины подорожания ремонта машин – " +
                    "это неизбежный рост цен на запчасти, а также на сами услуги.");
        }

        LinearLayout linearLayout = view.findViewById(R.id.list_linear_layout);
        for (int i = 0; i < notes.getSize(); i++) {
            TextView tv = new TextView(getContext());
            tv.setTextSize(24);
            tv.setText(notes.getTitle(i));
            final int index = i;
            tv.setOnClickListener(v -> noteDetails(index));
            linearLayout.addView(tv);
        }
    }

    private void noteDetails(int index) {
        currentNote = index;
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.list_notes_fragment_container, NoteBody.newInstance(notes.getNote(index)))
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}