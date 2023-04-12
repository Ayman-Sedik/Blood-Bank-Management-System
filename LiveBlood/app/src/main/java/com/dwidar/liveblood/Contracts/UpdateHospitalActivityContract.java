package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.HospitalComponents.Hospital;

public class UpdateHospitalActivityContract
{
    public interface IView
    {
        void onSuccessUpdate();
        void onFailUpdate(String msg);
    }

    public interface IPresenter
    {
        void Updatehospital(int hosID, Hospital hospital);
        void onSuccessUpdate();
        void onFailUpdate(String msg);
    }

    public interface IModel
    {
        void Updatehospital(int hosID, Hospital hospital);
    }
}
