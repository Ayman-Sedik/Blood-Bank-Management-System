package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.UploadBloodActivityContract;
import com.dwidar.liveblood.Model.Component.Blood;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Model.ViewModel.UploadBloodActivityModel;
import com.dwidar.liveblood.View.UploadBloodActivity;

import java.util.List;

public class UploadBloodActivityPresenter implements UploadBloodActivityContract.IPresenter
{
    private UploadBloodActivityContract.IView view;
    private UploadBloodActivityContract.IModel model;

    public UploadBloodActivityPresenter(UploadBloodActivity v)
    {
        this.view = v;
        model = new UploadBloodActivityModel(this);
    }

    @Override
    public void AddBlood(Blood blood) {
        model.AddBlood(blood);
    }

    @Override
    public void onSuccessAdd() {
        view.onSuccessAdd();
    }

    @Override
    public void onFailAdd(String msg) {
        view.onFailAdd(msg);
    }

}
