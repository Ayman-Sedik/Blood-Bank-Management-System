package com.dwidar.liveblood.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dwidar.liveblood.R;


public class AddFragment extends Fragment {

   Button coronaTestBTN;
   Button bloodPlasmaBTN;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add, container, false);

        coronaTestBTN=v.findViewById(R.id.coronaTestBTN);
        bloodPlasmaBTN=v.findViewById(R.id.bloodPlasmaBTN);
        coronaTestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), coronaTestActivity.class));
            }

        });
        bloodPlasmaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BlazmaTestActivity.class));
            }

        });
        return v;
    }
}