package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.Blood;
import com.dwidar.liveblood.Model.Component.iBlood;

import java.util.List;

public class UploadBloodActivityContract
{
    public interface IView
    {
        void onSuccessAdd();
        void onFailAdd(String msg);
    }

    public interface IPresenter
    {
        void AddBlood(Blood blood);
        void onSuccessAdd();
        void onFailAdd(String msg);
    }

    public interface IModel
    {
        void AddBlood(Blood blood);
    }
}
