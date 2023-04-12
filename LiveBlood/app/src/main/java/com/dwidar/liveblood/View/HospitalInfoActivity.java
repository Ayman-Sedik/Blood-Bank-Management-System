package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;
import com.dwidar.liveblood.R;
import com.dwidar.liveblood.databinding.ActivityHospitalInfoBinding;

public class HospitalInfoActivity extends AppCompatActivity {

    private ActivityHospitalInfoBinding infoBinding;
    private gHospital myHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        infoBinding = ActivityHospitalInfoBinding.inflate(getLayoutInflater());
        View view = infoBinding.getRoot();
        setContentView(view);

        myHospital = DataSettings.getMyHospital();
        setTitle(myHospital.getData().getName());

        infoBinding.BtnGoUploadBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HospitalInfoActivity.this, BloodsInfoActivity.class));
            }
        });

        infoBinding.BtnGoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HospitalInfoActivity.this, UpdateHospitalActivity.class));
            }
        });

        infoBinding.BtnGoTestVirus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HospitalInfoActivity.this, TestVirusActivity.class));
            }
        });
        findViewById(R.id.BtnGoTestCoronaResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalInfoActivity.this, resultActivity.class));
            }
        });
    }

}
