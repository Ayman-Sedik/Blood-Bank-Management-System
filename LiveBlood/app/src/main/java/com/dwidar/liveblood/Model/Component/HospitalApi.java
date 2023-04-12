package com.dwidar.liveblood.Model.Component;

import com.dwidar.liveblood.Model.Component.HospitalComponents.Hospital;
import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;
import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HospitalApi
{
    @POST("register")
    Call<ResponseBody> createUser(@Body User user);


    @POST("addBlood")
    Call<ResponseBody> AddBlood(@Body Blood blood);

    @GET("hospitals")
    Call<List<iHospital>> getAllHospitals();

    @GET("allBloods/{id}")
    Call<List<iBlood>> getBloods(@Path("id") int id);

    @GET("deleteBlood/{id}")
    Call<ResponseBody> DeleteBlood(@Path("id") int id);

    @FormUrlEncoded
    @POST("login")
    Call<gUser> userLogin(
            @Field("email") String email,
            @Field("password") String pwd
    );

    @FormUrlEncoded
    @POST("hospitalLogin")
    Call<gHospital> HospitalLogin(
            @Field("email") String email,
            @Field("password") String pwd
    );

    @POST("editHospital/{id}")
    Call<ResponseBody> UpdateHospital(@Path("id") int id, @Body Hospital hospital);
}
