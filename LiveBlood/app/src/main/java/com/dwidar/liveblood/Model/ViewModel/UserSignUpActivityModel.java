package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.UserSignUpActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Presenter.UserSignUpActivityPresenter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserSignUpActivityModel implements UserSignUpActivityContract.IModel
{
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    HospitalApi hospitalApi;
    private UserSignUpActivityContract.IPresenter presenter;


    public UserSignUpActivityModel(UserSignUpActivityPresenter p)
    {
        this.presenter = p;

        retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
        .build();
    hospitalApi = retrofit.create(HospitalApi.class);
}

    @Override
    public void UserRegister(User user)
    {
        Call<ResponseBody> call = hospitalApi.createUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful())
                {
                    Log.e("TEST REG", "onResponse: Done");
                    presenter.onsuccessRegister();
                }
                else {
                    try {
                        Log.e("TEST REG", "onResponse: " + response.errorBody().string());
                        presenter.onFailSuccessRegister(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                presenter.onFailSuccessRegister(t.getMessage());
            }
        });
    }
}
