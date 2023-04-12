package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.UserLoginActivityContract;
import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Model.Component.gUser;
import com.dwidar.liveblood.Model.ViewModel.UserLoginActivityModel;
import com.dwidar.liveblood.View.UserLoginActivity;

public class UserLoginActivityPresenter implements UserLoginActivityContract.IPresenter
{
    private UserLoginActivityContract.IView view;
    private UserLoginActivityContract.IModel model;

    public UserLoginActivityPresenter(UserLoginActivity v)
    {
        this.view = v;
        model = new UserLoginActivityModel(this);
    }


    @Override
    public void UserLogin(String email, String pwd) {
        model.UserLogin(email, pwd);
    }

    @Override
    public void onSuccessLogin(gUser user) {
        view.onSuccessLogin(user);
    }

    @Override
    public void onFailLogin(String msg) {
        view.onFailLogin(msg);
    }
}
