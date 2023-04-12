package com.dwidar.liveblood.Contracts;

import com.dwidar.liveblood.Model.Component.User;

public class UserSignUpActivityContract
{
    public interface IView
    {
        void onsuccessRegister();
        void onFailSuccessRegister(String msg);
    }

    public interface IPresenter
    {
        void UserRegister(User user);
        void onsuccessRegister();
        void onFailSuccessRegister(String msg);
    }

    public interface IModel
    {
        void UserRegister(User user);
    }
}
