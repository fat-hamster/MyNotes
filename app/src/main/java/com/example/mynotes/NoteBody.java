package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NoteBody extends Fragment implements Config{
    Note note = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        if(note == null)
            note = MainActivity.notes.getNote(0);

        TextView titleView = view.findViewById(R.id.note_title_view);
        titleView.setText(note.getTitle());
        TextView tv = view.findViewById(R.id.note_body_view);
        tv.setText(note.getBody());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.body_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            editBody();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editBody() {
        Intent intent = new Intent(getContext(), EditNoteActivity.class);
        intent.putExtra(Config.CURRENT_NOTE, MainActivity.notes.getBody(MainActivity.currentNote));
        startActivity(intent);
    }
}