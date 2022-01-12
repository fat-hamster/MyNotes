package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements Config{
    static Notes notes = new Notes();
    static int currentNote = 0;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = initToolBar();
        initDrawer(toolbar);

        if(savedInstanceState == null)
            initList();
        else
            currentNote = savedInstanceState.getInt(CURRENT_NOTE);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_notes_fragment_container, new ListNotesFragment())
                .commit();
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @SuppressLint("NonConstantResourceId")
    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (id) {
                case R.id.action_alphabet_up:
                    Snackbar.make(drawer_layout,"Сортировка от A-Z", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                case R.id.action_alphabet_down:
                    Snackbar.make(drawer_layout,"Сортировка от Z-A", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                case R.id.action_sort_creation_date:
                    Snackbar.make(drawer_layout,"Сортировка по дате создания", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                case R.id.action_settings:
                    runSettings();
                    return true;
            }

            return false;
        });
    }

    private void runSettings() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NOTE, currentNote);
    }

    private void initList() {
        for (int i = 0; i < 50; i++) {
            int line = i + 1;
            notes.add("Line " + line, "Основные причины подорожания ремонта машин – " +
                    "это неизбежный рост цен на запчасти, а также на сами услуги.");
        } // Android Studio предлагает автоматическое приведение типов, а не String.valueOf(line)
    }
}