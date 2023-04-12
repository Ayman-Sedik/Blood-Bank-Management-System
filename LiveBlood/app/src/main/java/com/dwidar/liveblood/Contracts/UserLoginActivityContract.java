package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Model.Component.gUser;

public class UserLoginActivityContract
{
    public interface IView
    {
        void onSuccessLogin(gUser user);
        void onFailLogin(String msg);
    }

    public interface IPresenter
    {
        void UserLogin(String email, String pwd);
        void onSuccessLogin(gUser user);
        void onFailLogin(String msg);
    }

    public interface IModel
    {
        void UserLogin(String email, String pwd);
    }
}
