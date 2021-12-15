package com.example.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteBody extends Fragment {

    static final String CURRENT_NOTE = "CURRENT_NOTE";
    Note note = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    public static NoteBody newInstance(Note note) {
        NoteBody nb = new NoteBody();
        Bundle arg = new Bundle();
        arg.putParcelable(CURRENT_NOTE, note);
        nb.setArguments(arg);
        return nb;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arg = getArguments();
        if(arg != null) {
            note = arg.getParcelable(CURRENT_NOTE);
        }

        TextView titleView = view.findViewById(R.id.note_title_view);
        titleView.setText(note.getTitle());
        TextView tv = view.findViewById(R.id.note_body_view);
        tv.setText(note.getBody());
    }
}