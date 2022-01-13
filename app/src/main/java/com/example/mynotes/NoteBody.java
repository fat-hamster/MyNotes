package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteBody extends Fragment implements Config{
    Note note = null;
    TextView body;

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
        body = view.findViewById(R.id.note_body_view);
        body.setText(note.getBody());
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem edit = menu.findItem(R.id.action_edit);
        MenuItem add = menu.findItem(R.id.action_add);
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
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
        startForResult.launch(intent);
    }

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    String editBody = intent.getStringExtra(Config.CURRENT_NOTE);
                    MainActivity.notes.getNote(MainActivity.currentNote).setBody(editBody);
                    body.setText(editBody);
                }
            });
}