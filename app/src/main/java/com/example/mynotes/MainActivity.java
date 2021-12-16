package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.telecom.Conference;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements Config{
    public static Notes notes = new Notes();
    public static int currentNote = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null)
            initList();
        else
            currentNote = savedInstanceState.getInt(CURRENT_NOTE);

        getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_notes_fragment_container, new ListNotesFragment())
                    .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NOTE, currentNote);
    }

    private void initList() {
        for (int i = 0; i < 10; i++) {
            int line = i + 1;
            notes.add("Line " + line, "Основные причины подорожания ремонта машин – " +
                    "это неизбежный рост цен на запчасти, а также на сами услуги.");
        }
    }
}