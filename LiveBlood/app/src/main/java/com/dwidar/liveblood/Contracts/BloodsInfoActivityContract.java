package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.Blood;
import com.dwidar.liveblood.Model.Component.iBlood;

import java.util.List;

public class BloodsInfoActivityContract
{
    public interface IView
    {
        void onSuccessGettingBloods(List<iBlood> bloods);
        void onFailGettingBloods(String msg);

        void onSuccessDelBloods();
        void onFailDelBloods(String msg);
    }

    public interface IPresenter
    {
        void getBloods(int HospitalId);
        void onSuccessGettingBloods(List<iBlood> bloods);
        void onFailGettingBloods(String msg);

        void deleteBlood(int hosID);
        void onSuccessDelBloods();
        void onFailDelBloods(String msg);
    }

    public interface IModel
    {
        void getBloods(int HosptalId);
        void deleteBlood(int hosID);
    }
}
