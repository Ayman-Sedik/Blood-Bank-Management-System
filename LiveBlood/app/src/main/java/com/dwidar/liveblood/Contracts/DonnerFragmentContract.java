package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class DonnerFragmentContract
{
    public interface IView
    {
        void onSuccessGettingHospital(List<iHospital> hospitals);
        void onFailGettingHospital(String msg);
    }

    public interface IPresenter
    {
        void getNearestHospital(LatLng myLocation);
        void onSuccessGettingHospital(List<iHospital> hospitals);
        void onFailGettingHospital(String msg);
    }

    public interface IModel
    {
        void getHospitals();
    }
}
