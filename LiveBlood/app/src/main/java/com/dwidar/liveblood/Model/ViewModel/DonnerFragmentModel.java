package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.DonnerFragmentContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;
import com.dwidar.liveblood.Presenter.DonnerFragmentPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonnerFragmentModel implements DonnerFragmentContract.IModel
{
    private DonnerFragmentContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    private HospitalApi hospitalApi;


    public DonnerFragmentModel(DonnerFragmentPresenter p)
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
                    Log.d("TEST GET HOSPITAL", "onResponse: " + hospitals.get(0).getName().toString());
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
