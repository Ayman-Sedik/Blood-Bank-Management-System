package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.BloodTypeHostpitalActivityContract;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.BloodTypeHostpitalActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityBloodTypeHostpitalBinding;

import java.util.ArrayList;
import java.util.List;

public class BloodTypeHostpitalActivity extends AppCompatActivity implements BloodTypeHostpitalActivityContract.IView
{

    private ActivityBloodTypeHostpitalBinding binding;
    private BloodTypeHostpitalActivityContract.IPresenter presenter;
    private int hispitalID = 0;
    private ArrayList<String> bloodsTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBloodTypeHostpitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new BloodTypeHostpitalActivityPresenter(this);

        hispitalID = getIntent().getIntExtra("ID", 0);
        String title = getIntent().getStringExtra("TITLE");
        setTitle("Bloods in " + title);

        presenter.getBloods(hispitalID);
    }

    @Override
    public void onSuccess(List<iBlood> bloods)
    {
        bloodsTitles = new ArrayList<>();
        for (iBlood itr : bloods)
        {
            bloodsTitles.add(itr.getType());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bloodsTitles);
        binding.LVBloods.setAdapter(arrayAdapter);
    }

    @Override
    public void onFail(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
