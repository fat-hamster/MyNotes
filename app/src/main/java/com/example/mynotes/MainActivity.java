package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements Config{
    static Notes notes = new Notes();
    static int currentNote = 0;
    private DrawerLayout drawer_layout;
    //private FrameLayout settings_container;

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

        //settings_container = findViewById(R.id.settings_container);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //settings_container.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.list_notes_fragment_container, new ListNotesFragment())
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.list_notes_fragment_container, new SettingsFragment());
        } else {
            //settings_container.setVisibility(View.VISIBLE);
            LinearLayout linearLayout = findViewById(R.id.main_container_land);
            FrameLayout frameLayout = new FrameLayout(this);
            linearLayout.addView(frameLayout);
            transaction.replace(frameLayout.getId(), new SettingsFragment());
        }
        transaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) { // интересно было попробовать Snackbar
            Snackbar.make(drawer_layout,"Добавление заметки", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NOTE, currentNote);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for(Fragment fragment: fragmentManager.getFragments()) {
            if(fragment instanceof OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if(backPressedListener != null) {
            backPressedListener.onBackPressed();
            //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                //settings_container.setVisibility(View.INVISIBLE);
        //} else {
            super.onBackPressed();
        }
    }

    private void initList() {
        for (int i = 0; i < 10; i++) {
            int line = i + 1;
            notes.add("Line " + line, "Основные причины подорожания ремонта машин – " +
                    "это неизбежный рост цен на запчасти, а также на сами услуги.");
        }
    }
}