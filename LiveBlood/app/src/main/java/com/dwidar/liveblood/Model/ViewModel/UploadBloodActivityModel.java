package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.UploadBloodActivityContract;
import com.dwidar.liveblood.Model.Component.Blood;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.UploadBloodActivityPresenter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadBloodActivityModel implements UploadBloodActivityContract.IModel
{
    private UploadBloodActivityContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    HospitalApi hospitalApi;

    public UploadBloodActivityModel(UploadBloodActivityPresenter p)
    {
        presenter = p;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }

    @Override
    public void AddBlood(Blood blood)
    {
        Call<ResponseBody> call = hospitalApi.AddBlood(blood);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful())
                {
                    Log.e("TEST BLOOD ADD", "onResponse: " + response.body());
                    presenter.onSuccessAdd();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                presenter.onFailAdd(t.getMessage());
            }
        });
    }

}
