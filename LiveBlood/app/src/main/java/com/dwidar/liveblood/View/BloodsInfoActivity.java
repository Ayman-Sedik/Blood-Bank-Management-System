package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Adapter.BloodAdapter;
import com.dwidar.liveblood.Contracts.BloodsInfoActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.BloodsInfoActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityBloodsInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class BloodsInfoActivity extends AppCompatActivity implements BloodsInfoActivityContract.IView
{

    private ActivityBloodsInfoBinding binding;
    private BloodsInfoActivityContract.IPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityBloodsInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new BloodsInfoActivityPresenter(this);
        setTitle("Bloods");
        presenter.getBloods(DataSettings.getMyHospital().getData().getId());

        binding.BtnAddBlood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(BloodsInfoActivity.this, UploadBloodActivity.class));
            }
        });
    }

    @Override
    public void onSuccessGettingBloods(List<iBlood> bloods)
    {
        DataSettings.setBloods(bloods);

        binding.RVBloods.setLayoutManager(new LinearLayoutManager(this));
        BloodAdapter bloodAdapter = new BloodAdapter(this, (ArrayList<iBlood>) bloods, (BloodsInfoActivityPresenter) presenter);
        binding.RVBloods.setAdapter(bloodAdapter);
        binding.progressCircular.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onFailGettingBloods(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessDelBloods() {
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onFailDelBloods(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}