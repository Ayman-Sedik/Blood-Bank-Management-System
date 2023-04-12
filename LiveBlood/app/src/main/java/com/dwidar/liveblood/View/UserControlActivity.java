package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.databinding.ActivityUserControlBinding;

public class UserControlActivity extends AppCompatActivity {

    private ActivityUserControlBinding controlBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        controlBinding = ActivityUserControlBinding.inflate(getLayoutInflater());
        View view = controlBinding.getRoot();
        setContentView(view);

        setTitle(DataSettings.getMyUser().getData().getName());
    }
}
