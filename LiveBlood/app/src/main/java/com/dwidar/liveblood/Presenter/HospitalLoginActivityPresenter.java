package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.HospitalLoginActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalComponents.gHospital;
import com.dwidar.liveblood.Model.ViewModel.HospitalLoginActivityModel;
import com.dwidar.liveblood.View.HospitalLoginActivity;

public class HospitalLoginActivityPresenter implements HospitalLoginActivityContract.IPresenter
{
    private HospitalLoginActivityContract.IView view;
    private HospitalLoginActivityContract.IModel model;

    public HospitalLoginActivityPresenter(HospitalLoginActivity v)
    {
        this.view = v;
        model = new HospitalLoginActivityModel(this);
    }

    @Override
    public void HospitalLogin(String email, String pwd) {
        model.HospitalLogin(email, pwd);
    }

    @Override
    public void onSuccessLogin(gHospital hospital) {
        view.onSuccessLogin(hospital);
    }

    @Override
    public void onFailLogin(String msg) {
        view.onFailLogin(msg);
    }
}
