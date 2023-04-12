package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.iBlood;

import java.util.List;

public class BloodTypeHostpitalActivityContract
{
    public interface IView
    {
        void onSuccess(List<iBlood> bloods);
        void onFail(String msg);
    }

    public interface IPresenter
    {
        void getBloods(int HospitalId);
        void onSuccess(List<iBlood> bloods);
        void onFail(String msg);
    }

    public interface IModel
    {
        void getBloods(int HosptalId);
    }
}
