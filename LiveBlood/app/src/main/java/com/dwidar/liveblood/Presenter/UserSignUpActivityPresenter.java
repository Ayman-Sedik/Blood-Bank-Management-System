package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.UserSignUpActivityContract;
import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Model.ViewModel.UserSignUpActivityModel;
import com.dwidar.liveblood.View.UserSignUpActivity;

public class UserSignUpActivityPresenter implements UserSignUpActivityContract.IPresenter
{
    private UserSignUpActivityContract.IView view;
    private UserSignUpActivityModel model;

    public UserSignUpActivityPresenter(UserSignUpActivity v)
    {
        this.view = v;
        model = new UserSignUpActivityModel(this);
    }

    @Override
    public void UserRegister(User user) {
        model.UserRegister(user);
    }

    @Override
    public void onsuccessRegister() {
        view.onsuccessRegister();
    }

    @Override
    public void onFailSuccessRegister(String msg) {
        view.onFailSuccessRegister(msg);
    }
}
