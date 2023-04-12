package com.dwidar.liveblood.Model.ViewModel;

import com.dwidar.liveblood.Contracts.BloodsInfoActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.BloodsInfoActivityPresenter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BloodsInfoActivityModel implements BloodsInfoActivityContract.IModel
{
    private BloodsInfoActivityContract.IPresenter presenter;

    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    HospitalApi hospitalApi;

    public BloodsInfoActivityModel(BloodsInfoActivityPresenter p)
    {
        this.presenter = p;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }

    @Override
    public void getBloods(int HosptalId)
    {
        Call<List<iBlood>> call = hospitalApi.getBloods(HosptalId);
        call.enqueue(new Callback<List<iBlood>>() {
            @Override
            public void onResponse(Call<List<iBlood>> call, Response<List<iBlood>> response)
            {
                if (response.isSuccessful())
                {
                    List<iBlood> bloods = response.body();
                    presenter.onSuccessGettingBloods(bloods);
                }
            }

            @Override
            public void onFailure(Call<List<iBlood>> call, Throwable t)
            {
                presenter.onFailGettingBloods(t.getMessage());
            }
        });
    }

    @Override
    public void deleteBlood(int hosID)
    {
        Call<ResponseBody> call = hospitalApi.DeleteBlood(hosID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful())
                {
                    presenter.onSuccessDelBloods();
                }else
                {
                    presenter.onFailDelBloods(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                presenter.onFailGettingBloods(t.getMessage());
            }
        });
    }
}
