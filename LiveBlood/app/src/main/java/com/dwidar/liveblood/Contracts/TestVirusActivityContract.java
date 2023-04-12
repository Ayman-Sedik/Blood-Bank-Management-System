package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.TestVirus;

public class TestVirusActivityContract
{
    public interface IView
    {
        void onSuccessAdd();
        void onFailAdd(String msg);
    }

    public interface IPresenter
    {
        void AddTestVirus(TestVirus testVirus);
        void onSuccessAdd();
        void onFailAdd(String msg);
    }

    public interface IModel
    {
        void AddTestVirus(TestVirus testVirus);
    }
}
