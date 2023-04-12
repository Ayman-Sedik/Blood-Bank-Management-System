package com.dwidar.liveblood.Model.ViewModel;

import androidx.annotation.NonNull;

import com.dwidar.liveblood.Contracts.TestVirusActivityContract;
import com.dwidar.liveblood.Model.Component.TestVirus;
import com.dwidar.liveblood.Presenter.TestVirusActivityPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestVirusActivityModel implements TestVirusActivityContract.IModel
{
    private TestVirusActivityContract.IPresenter presenter;
    private DatabaseReference dbRef;

    public TestVirusActivityModel(TestVirusActivityPresenter p)
    {
        this.presenter = p;
        dbRef = FirebaseDatabase.getInstance("https://bloodsystem-ae1c1-default-rtdb.firebaseio.com/").getReference("Database").child("TestVirus");
    }

    @Override
    public void AddTestVirus(TestVirus testVirus)
    {
        dbRef.push().setValue(testVirus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                presenter.onSuccessAdd();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                presenter.onFailAdd(e.getMessage());
            }
        });
    }
}
