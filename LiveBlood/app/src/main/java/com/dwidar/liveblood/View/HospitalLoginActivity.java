package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.HospitalLoginActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;
import com.dwidar.liveblood.Presenter.HospitalLoginActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityHospitalLoginBinding;

public class HospitalLoginActivity extends AppCompatActivity implements HospitalLoginActivityContract.IView
{

    private ActivityHospitalLoginBinding loginBinding;
    private HospitalLoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityHospitalLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);

        presenter = new HospitalLoginActivityPresenter(this);

        loginBinding.HosLoginPB.setVisibility(ProgressBar.INVISIBLE);

        loginBinding.BtnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HospitalLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginBinding.BtnHosLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });

    }

    private void Login()
    {
        if (dataValidation())
        {
            loginBinding.HosLoginPB.setVisibility(ProgressBar.VISIBLE);

            String email = loginBinding.TxtHosEmailLogin.getText().toString().trim();
            String pwd = loginBinding.TxtHosPwdLogin.getText().toString().trim();

            presenter.HospitalLogin(email, pwd);
        }
    }

    private boolean dataValidation()
    {
        boolean isValid = true;
        String email = loginBinding.TxtHosEmailLogin.getText().toString().trim();
        String pwd = loginBinding.TxtHosPwdLogin.getText().toString().trim();

        if (email.isEmpty())
        {
            loginBinding.TxtHosEmailLogin.setError("Enter Valid Email");
            isValid = false;
        }

        if (pwd.isEmpty())
        {
            loginBinding.TxtHosPwdLogin.setError("Enter Valid Password");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onSuccessLogin(gHospital hospital)
    {
        DataSettings.setMyHospital(hospital);
        startActivity(new Intent(this, HospitalInfoActivity.class));
        finish();
    }

    @Override
    public void onFailLogin(String msg)
    {
        loginBinding.HosLoginPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
