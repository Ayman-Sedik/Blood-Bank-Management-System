package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.UserLoginActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Model.Component.gUser;
import com.dwidar.liveblood.Presenter.UserLoginActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityUserLoginBinding;

public class UserLoginActivity extends AppCompatActivity implements UserLoginActivityContract.IView
{

    private ActivityUserLoginBinding loginBinding;
    private UserLoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);

        presenter = new UserLoginActivityPresenter(this);

        loginBinding.UserLoginPB.setVisibility(ProgressBar.INVISIBLE);

        loginBinding.BtnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginBinding.BtnDonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });

        loginBinding.BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Login()
    {
        if (dataValidation())
        {
            loginBinding.UserLoginPB.setVisibility(ProgressBar.VISIBLE);

            String email = loginBinding.TxtDonEmailLogin.getText().toString().trim();
            String pwd = loginBinding.TxtDonPwdLogin.getText().toString().trim();

            presenter.UserLogin(email, pwd);
        }
    }

    private boolean dataValidation()
    {
        boolean isValid = true;
        String email = loginBinding.TxtDonEmailLogin.getText().toString().trim();
        String pwd = loginBinding.TxtDonPwdLogin.getText().toString().trim();

        if (email.isEmpty())
        {
            loginBinding.TxtDonEmailLogin.setError("Enter Valid Email");
            isValid = false;
        }

        if (pwd.isEmpty())
        {
            loginBinding.TxtDonPwdLogin.setError("Enter Valid Password");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onSuccessLogin(gUser user)
    {
        Intent intent = new Intent(this, UserMainActivity.class);
        DataSettings.setMyUser(user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailLogin(String msg)
    {
        loginBinding.UserLoginPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
