package com.dwidar.liveblood.Model.ViewModel;

import android.util.Log;

import com.dwidar.liveblood.Contracts.UpdateHospitalActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.HospitalComponents.Hospital;
import com.dwidar.liveblood.Model.Component.HospitalApi;
import com.dwidar.liveblood.Presenter.UpdateHospitalActivityPresenter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateHospitalActivityModel implements UpdateHospitalActivityContract.IModel
{
    private UpdateHospitalActivityContract.IPresenter presenter;
    private final String BASE_URL = "http://el-carnaval.online/hospital/api/";
    private Retrofit retrofit;
    private HospitalApi hospitalApi;


    public UpdateHospitalActivityModel(UpdateHospitalActivityPresenter p)
    {
        this.presenter = p;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalApi = retrofit.create(HospitalApi.class);
    }

    @Override
    public void Updatehospital(int hosID, final Hospital hospital)
    {
        Call<ResponseBody> call = hospitalApi.UpdateHospital(hosID, hospital);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful())
                {
                    Log.e("TEST UPDATE", "onResponse: " + response.body());
                    UpdateLocalData(hospital);
                    presenter.onSuccessUpdate();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                presenter.onFailUpdate(t.getMessage());
            }
        });
    }

    private void UpdateLocalData(Hospital hospital)
    {
        DataSettings.getMyHospital().getData().setName(hospital.getName());
        DataSettings.getMyHospital().getData().setEmail(hospital.getEmail());
        DataSettings.getMyHospital().getData().setAddress(hospital.getAddress());
        DataSettings.getMyHospital().getData().setPhone(hospital.getPhone());
        DataSettings.setHospitalPwd(hospital.getPassword());
    }
}
