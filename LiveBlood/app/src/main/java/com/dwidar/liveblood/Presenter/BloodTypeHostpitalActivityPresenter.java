package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.BloodTypeHostpitalActivityContract;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Model.ViewModel.BloodTypeHostpitalActivityModel;
import com.dwidar.liveblood.View.BloodTypeHostpitalActivity;

import java.util.List;

public class BloodTypeHostpitalActivityPresenter implements BloodTypeHostpitalActivityContract.IPresenter
{
    private BloodTypeHostpitalActivityContract.IView view;
    private BloodTypeHostpitalActivityContract.IModel model;

    public BloodTypeHostpitalActivityPresenter(BloodTypeHostpitalActivity v)
    {
        this.view = v;
        model = new BloodTypeHostpitalActivityModel(this);
    }

    @Override
    public void getBloods(int HospitalId) {
        model.getBloods(HospitalId);
    }

    @Override
    public void onSuccess(List<iBlood> bloods) {
        view.onSuccess(bloods);
    }

    @Override
    public void onFail(String msg) {
        view.onFail(msg);
    }
}
