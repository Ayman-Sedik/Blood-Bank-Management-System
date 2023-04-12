package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.HospitalLoginActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Presenter.HospitalLoginActivityPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HospitalLoginActivityModel implements HospitalLoginActivityContract.IModel
{
    private HospitalLoginActivityContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    private HospitalApi hospitalApi;

    public HospitalLoginActivityModel(HospitalLoginActivityPresenter presenter)
    {
        this.presenter = presenter;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }


    @Override
    public void HospitalLogin(String email, final String pwd)
    {
        Call<gHospital> call = hospitalApi.HospitalLogin(email, pwd);

        call.enqueue(new Callback<gHospital>()
        {
            @Override
            public void onResponse(Call<gHospital> call, Response<gHospital> response)
            {
                if (response.isSuccessful())
                {
                    gHospital hospital = response.body();
                    Log.e("TEST LOGIN", "onResponse: Status : " + hospital.getStatus());

                    if (hospital.getStatus() == 1)
                    {
                        DataSettings.setHospitalPwd(pwd);
                        presenter.onSuccessLogin(hospital);
                    }
                    else presenter.onFailLogin(hospital.getMessage());
                }
                else presenter.onFailLogin(response.message());
            }

            @Override
            public void onFailure(Call<gHospital> call, Throwable t)
            {
                presenter.onFailLogin(t.getMessage());
            }
        });
    }
}
