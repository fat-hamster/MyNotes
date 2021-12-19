package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSettings();
        initView();
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        Settings.isDeleteBeforeAdd = sharedPreferences.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
        Settings.isBackStack = sharedPreferences.getBoolean(Settings.IS_BACK_STACK_USED, true);
        Settings.isBackAsRemove = sharedPreferences.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, false);
        Settings.isAddFragment = sharedPreferences.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
    }

    private void initView() {
        initButtonBack();
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
    }

    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (Settings.isBackAsRemove) {
                Fragment visibleFragment = getVisibleFragment(fragmentManager);
                if (visibleFragment != null) {
                    fragmentManager.beginTransaction().remove(visibleFragment).commit();
                } else {
                    fragmentManager.popBackStack();
                }
            }
            if (Settings.isBackStack) {
                fragmentManager.popBackStack();
            }
        });
    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(v -> showFragment(new MainFragment()));
    }

    private void initButtonFavorite() {
        Button buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> showFragment(new FavoriteFragment()));
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> showFragment(new SettingsFragment()));
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (Settings.isDeleteBeforeAdd) {
            Fragment visibleFragment = getVisibleFragment(fragmentManager);
            if (visibleFragment != null) {
                transaction.remove(visibleFragment);
            }
        }

        if (Settings.isAddFragment) {
            transaction.add(R.id.fragment_container, fragment);
        } else {
            transaction.replace(R.id.fragment_container, fragment);
        }

        if (Settings.isBackStack) {
            transaction.addToBackStack("");
        }

        transaction.commit();
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        Fragment fragment;
        int i = fragmentList.size() - 1;
        for (; i >= 0; i--) {
            fragment = fragmentList.get(i);
            if (fragment.isVisible())
                return fragment;
        }

        return null;
    }
}