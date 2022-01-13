package com.example.mynotes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListNotesFragment extends Fragment implements Config {
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        recyclerView = view.findViewById(R.id.recycle_view_lines);
        recyclerView.setAdapter(new NotesListAdaptor(MainActivity.notes, (view1, position) -> showNote(position)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            noteLandDetails(MainActivity.currentNote);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem edit = menu.findItem(R.id.action_edit);
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.currentNote != 0) {
            recyclerView.getLayoutManager().scrollToPosition(MainActivity.currentNote);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Toast.makeText(getContext(), "Добавление заметки", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem edit = menu.findItem(R.id.action_edit);
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }

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