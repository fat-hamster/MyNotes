package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;

public class EditNoteActivity extends AppCompatActivity {

    AppCompatEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initView();
    }

    private void initView() {
        Bundle arguments = getIntent().getExtras();
        String body = arguments.getString(Config.CURRENT_NOTE);
        editText = findViewById(R.id.edit_body);
        editText.setText(body);
        AppCompatButton btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(v -> finish());
        AppCompatButton btnSave = findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(v -> sendResult());
    }

    private void sendResult() {
        Intent data = new Intent();
        data.putExtra(Config.CURRENT_NOTE, editText.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}