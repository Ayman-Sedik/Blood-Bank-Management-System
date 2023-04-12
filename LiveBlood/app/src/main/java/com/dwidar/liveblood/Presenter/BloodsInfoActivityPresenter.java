package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.BloodsInfoActivityContract;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Model.ViewModel.BloodsInfoActivityModel;
import com.dwidar.liveblood.View.BloodsInfoActivity;

import java.util.List;

public class BloodsInfoActivityPresenter implements BloodsInfoActivityContract.IPresenter
{
    private BloodsInfoActivityContract.IView view;
    private BloodsInfoActivityContract.IModel model;

    public BloodsInfoActivityPresenter (BloodsInfoActivity v)
    {
        this.view = v;
        this.model = new BloodsInfoActivityModel(this);
    }

    @Override
    public void getBloods(int HospitalId) {
        model.getBloods(HospitalId);
    }

    @Override
    public void onSuccessGettingBloods(List<iBlood> bloods) {
        view.onSuccessGettingBloods(bloods);
    }

    @Override
    public void onFailGettingBloods(String msg) {
        view.onFailGettingBloods(msg);
    }

    @Override
    public void deleteBlood(int hosID) {
        model.deleteBlood(hosID);
    }

    @Override
    public void onSuccessDelBloods() {
        view.onSuccessDelBloods();
    }

    @Override
    public void onFailDelBloods(String msg) {
        view.onFailDelBloods(msg);
    }
}
