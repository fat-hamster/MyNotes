package com.example.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListNotesFragment extends Fragment implements Config {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.currentNote = savedInstanceState.getInt(CURRENT_NOTE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_lines);
        recyclerView.setAdapter(new NotesListAdaptor(MainActivity.notes, (view1, position) -> showNote(position)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        initList(view);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            noteLandDetails(MainActivity.currentNote);
    }

//    private void initList(View view) {
//        LinearLayout linearLayout = view.findViewById(R.id.list_linear_layout);
//        for (int i = 0; i < MainActivity.notes.getSize(); i++) {
//            TextView tv = new TextView(getContext());
//            tv.setTextSize(24);
//            tv.setText(MainActivity.notes.getTitle(i));
//            final int index = i;
//            tv.setOnClickListener(v -> showNote(index));
//            linearLayout.addView(tv);
//        }
//    }

    private void showNote(int index) {
        MainActivity.currentNote = index;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            noteLandDetails(index);
        } else {
            notePortDetails(index);
        }
    }

    private void noteLandDetails(int index) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_land_body_notes, NoteBody.newInstance(MainActivity.notes.getNote(index)))
                .commit();
    }

    private void notePortDetails(int index) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.list_notes_fragment_container, NoteBody.newInstance(MainActivity.notes.getNote(index)))
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, MainActivity.currentNote);
        super.onSaveInstanceState(outState);
    }
}