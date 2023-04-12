package com.dwidar.liveblood.View;

import android.os.Bundle;

import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.R;
import com.dwidar.liveblood.databinding.ActivityUserMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dwidar.liveblood.View.ui.main.SectionsPagerAdapter;

public class UserMainActivity extends AppCompatActivity {

    private ActivityUserMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityUserMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        setTitle(DataSettings.getMyUser().getData().getName());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        mainBinding.viewPager.setAdapter(sectionsPagerAdapter);
        mainBinding.tabs.setupWithViewPager(mainBinding.viewPager);
    }


}