package com.dwidar.liveblood.Presenter;

import com.dwidar.liveblood.Contracts.TestVirusActivityContract;
import com.dwidar.liveblood.Model.Component.TestVirus;
import com.dwidar.liveblood.Model.ViewModel.TestVirusActivityModel;
import com.dwidar.liveblood.View.TestVirusActivity;

public class TestVirusActivityPresenter implements TestVirusActivityContract.IPresenter
{
    private TestVirusActivityContract.IModel model;
    private TestVirusActivityContract.IView view;

    public TestVirusActivityPresenter(TestVirusActivity v)
    {
        model = new TestVirusActivityModel(this);
        this.view = v;
    }
    @Override
    public void AddTestVirus(TestVirus testVirus) {
        model.AddTestVirus(testVirus);
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
