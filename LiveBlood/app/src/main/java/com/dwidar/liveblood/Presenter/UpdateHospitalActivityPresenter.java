package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.UpdateHospitalActivityContract;
import com.dwidar.liveblood.Model.Component.HospitalComponents.Hospital;
import com.dwidar.liveblood.Model.ViewModel.UpdateHospitalActivityModel;
import com.dwidar.liveblood.View.UpdateHospitalActivity;

public class UpdateHospitalActivityPresenter implements UpdateHospitalActivityContract.IPresenter
{
    private UpdateHospitalActivityContract.IView view;
    private UpdateHospitalActivityContract.IModel model;

    public UpdateHospitalActivityPresenter(UpdateHospitalActivity v)
    {
        this.view = v;
        this.model = new UpdateHospitalActivityModel(this);
    }
    @Override
    public void Updatehospital(int hosID, Hospital hospital) {
        model.Updatehospital(hosID, hospital);
    }

    @Override
    public void onSuccessUpdate() {
        view.onSuccessUpdate();
    }

    @Override
    public void onFailUpdate(String msg) {
        view.onFailUpdate(msg);
    }
}
