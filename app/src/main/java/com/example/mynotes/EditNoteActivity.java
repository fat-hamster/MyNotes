package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Bundle arguments = getIntent().getExtras();
        String body = arguments.getString(Config.CURRENT_NOTE);

        AppCompatEditText editText = findViewById(R.id.edit_body);

        editText.setText(body);
    }
}