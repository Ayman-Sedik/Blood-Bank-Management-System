package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;

public class HospitalLoginActivityContract
{
    public interface IView
    {
        void onSuccessLogin(gHospital hospital);
        void onFailLogin(String msg);
    }

    public interface IPresenter
    {
        void HospitalLogin(String email, String pwd);
        void onSuccessLogin(gHospital hospital);
        void onFailLogin(String msg);
    }

    public interface IModel
    {
        void HospitalLogin(String email, String pwd);
    }
}
