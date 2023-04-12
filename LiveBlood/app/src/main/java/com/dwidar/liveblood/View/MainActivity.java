package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dwidar.liveblood.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        mainBinding.BtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mainBinding.BtnGoHosLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                goHosptalLogin();
            }
        });

        mainBinding.BtnGoDonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDonorLogin();
            }
        });
    }

    private void goHosptalLogin()
    {
        Intent hoslogin = new Intent(this, HospitalLoginActivity.class);
        startActivity(hoslogin);
        finish();
    }

    private void goDonorLogin()
    {
        Intent login = new Intent(this, UserLoginActivity.class);
        startActivity(login);
        finish();
    }
}
