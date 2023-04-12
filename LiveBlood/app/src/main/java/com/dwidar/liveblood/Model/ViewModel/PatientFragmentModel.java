package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.PatientFragmentContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;
import com.dwidar.liveblood.Presenter.PatientFragmentPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientFragmentModel implements PatientFragmentContract.IModel
{
    private PatientFragmentContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    private HospitalApi hospitalApi;


    public PatientFragmentModel(PatientFragmentPresenter p)
    {
        this.presenter = p;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }

    @Override
    public void getHospitals()
    {
        Call<List<iHospital>> call = hospitalApi.getAllHospitals();
        call.enqueue(new Callback<List<iHospital>>()
        {
            @Override
            public void onResponse(Call<List<iHospital>> call, Response<List<iHospital>> response)
            {
                if (response.isSuccessful())
                {
                    List<iHospital> hospitals = response.body();
                    Log.e("TEST GET HOSPITAL", "onResponse: " + hospitals.get(0).getName());
                    presenter.onSuccessGettingHospital(hospitals);
                }
            }

            @Override
            public void onFailure(Call<List<iHospital>> call, Throwable t)
            {
                presenter.onFailGettingHospital(t.getMessage());
            }
        });
    }
}
