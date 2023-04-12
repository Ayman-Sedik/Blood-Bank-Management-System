package com.dwidar.liveblood.Presenter;

import android.util.Log;

import com.dwidar.liveblood.Contracts.PatientFragmentContract;
import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;
import com.dwidar.liveblood.Model.ViewModel.PatientFragmentModel;
import com.dwidar.liveblood.View.PatientFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PatientFragmentPresenter implements PatientFragmentContract.IPresenter
{
    private PatientFragmentContract.IView view;
    private PatientFragmentContract.IModel model;
    private LatLng UserLocation;

    public PatientFragmentPresenter(PatientFragment v)
    {
        this.view = v;
        model = new PatientFragmentModel(this);
    }

    @Override
    public void getNearestHospital(LatLng myLocation)
    {
        this.UserLocation = myLocation;
        model.getHospitals();
    }

    @Override
    public void onSuccessGettingHospital(List<iHospital> hospitals)
    {
        // List<iHospital> sorted = sortLocations(hospitals);
        view.onSuccessGettingHospital(hospitals);
    }

    private List<iHospital> sortLocations(List<iHospital> hospitals)
    {
        double Mindist = getDistance(Double.parseDouble(hospitals.get(0).getLatitude()), Double.parseDouble(hospitals.get(0).getLongitude()));
        int Minidx = 0;
        int lnth = hospitals.size();

        for (int i=0; i < lnth; i++)
        {
            double lat = Double.parseDouble(hospitals.get(i).getLatitude());
            double lng = Double.parseDouble(hospitals.get(i).getLongitude());

            double dist = getDistance(lat, lng);
            Log.e("TEST DIST", "sortLocations: " + hospitals.get(i).getName() + " " + dist);
            if (dist < Mindist)
            {
                Mindist = dist;
                Minidx = i;
            }
        }

        iHospital iHospital = hospitals.get(0);
        iHospital hospitalMin = hospitals.get(Minidx);
        hospitals.set(Minidx, iHospital);
        hospitals.set(0, hospitalMin);

        return hospitals;
    }

    private double getDistance(double lat, double lng)
    {
        return (
                Math.sqrt(
                        (Math.pow(lat - this.UserLocation.latitude,2)) +
                                (Math.pow(lng - this.UserLocation.longitude,2))));
    }

    @Override
    public void onFailGettingHospital(String msg)
    {
        view.onFailGettingHospital(msg);
    }
}
