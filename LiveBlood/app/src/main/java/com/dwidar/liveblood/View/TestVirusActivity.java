package com.dwidar.liveblood.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.TestVirusActivityContract;
import com.dwidar.liveblood.Model.Component.TestVirus;
import com.dwidar.liveblood.Presenter.TestVirusActivityPresenter;
import com.dwidar.liveblood.R;
import com.dwidar.liveblood.databinding.ActivityTestVirusBinding;

public class TestVirusActivity extends AppCompatActivity implements TestVirusActivityContract.IView
{

    private ActivityTestVirusBinding binding;
    private TestVirusActivityContract.IPresenter presenter;
    private String neg = "Negative";
    private String Bloodtype = "O-";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityTestVirusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new TestVirusActivityPresenter(this);
        binding.progressCircular.setVisibility(ProgressBar.INVISIBLE);

        binding.BtnTestVirus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ClickTestVirus();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Nv, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnerUserNegativity.setAdapter(adapter);

        binding.SpinnerUserNegativity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                neg = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> BloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.BloodTypes, android.R.layout.simple_spinner_item);
        BloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnerUserBloodType.setAdapter(BloodAdapter);

        binding.SpinnerUserBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Bloodtype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ClickTestVirus()
    {
        if (!neg.equals("Positive"))
        {
            binding.progressCircular.setVisibility(ProgressBar.VISIBLE);
            String name = binding.TxtTstUserName.getText().toString().trim();
            presenter.AddTestVirus(new TestVirus(name, this.Bloodtype));
        }
        else
        {
            Toast.makeText(this, "You can not add positive test virus.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessAdd()
    {
        binding.progressCircular.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, "Test Done!", Toast.LENGTH_SHORT).show();
        clearData();
    }

    @Override
    public void onFailAdd(String msg)
    {
        binding.progressCircular.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        clearData();
    }

    private void clearData()
    {
        binding.TxtTstUserName.setText("");
    }
}
