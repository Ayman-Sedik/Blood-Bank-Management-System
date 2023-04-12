package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.UserLoginActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.gUser;
import com.dwidar.liveblood.Presenter.UserLoginActivityPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginActivityModel implements UserLoginActivityContract.IModel
{
    private UserLoginActivityPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    HospitalApi hospitalApi;

    public UserLoginActivityModel(UserLoginActivityPresenter p)
    {
        this.presenter = p;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }


    @Override
    public void UserLogin(String email, String pwd)
    {
        Call<gUser> call = hospitalApi.userLogin(email, pwd);
        call.enqueue(new Callback<gUser>()
        {
            @Override
            public void onResponse(Call<gUser> call, Response<gUser> response)
            {
                if (response.isSuccessful())
                {
                    gUser user = response.body();

                    Log.e("TEST LOGIN", "onResponse: " + user.getMessage());

                    if (user.getStatus() == 1) presenter.onSuccessLogin(user);
                    else presenter.onFailLogin(user.getMessage());
                }
                else presenter.onFailLogin(response.message());
            }

            @Override
            public void onFailure(Call<gUser> call, Throwable t)
            {
                presenter.onFailLogin(t.getMessage());
            }
        });
    }
}
