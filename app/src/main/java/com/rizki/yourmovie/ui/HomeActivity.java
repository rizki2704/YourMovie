package com.rizki.yourmovie.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rizki.yourmovie.R;
import com.rizki.yourmovie.ui.fragment.MovieFragment;
import com.rizki.yourmovie.ui.fragment.WhislistFragment;

import org.jetbrains.annotations.NotNull;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MovieFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment= new MovieFragment();
                        break;
                    case R.id.nav_whislist:
                        fragment= new WhislistFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });

    }

}