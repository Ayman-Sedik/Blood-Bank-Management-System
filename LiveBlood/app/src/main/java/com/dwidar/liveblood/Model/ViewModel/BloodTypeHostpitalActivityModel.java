package com.dwidar.liveblood.Model.ViewModel;

import com.dwidar.liveblood.Contracts.BloodTypeHostpitalActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.BloodTypeHostpitalActivityPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BloodTypeHostpitalActivityModel implements BloodTypeHostpitalActivityContract.IModel
{
    private BloodTypeHostpitalActivityContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    private HospitalApi hospitalApi;


    public BloodTypeHostpitalActivityModel(BloodTypeHostpitalActivityPresenter p)
    {
        this.presenter = p;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }

    @Override
    public void getBloods(int HospitalId)
    {
        Call<List<iBlood>> call = hospitalApi.getBloods(HospitalId);
        call.enqueue(new Callback<List<iBlood>>() {
            @Override
            public void onResponse(Call<List<iBlood>> call, Response<List<iBlood>> response)
            {
                if (response.isSuccessful())
                {
                    List<iBlood> bloods = response.body();
                    presenter.onSuccess(bloods);
                }
            }

            @Override
            public void onFailure(Call<List<iBlood>> call, Throwable t)
            {
                presenter.onFail(t.getMessage());
            }
        });
    }
}
