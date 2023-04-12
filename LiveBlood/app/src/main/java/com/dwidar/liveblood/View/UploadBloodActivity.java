package com.dwidar.liveblood.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.UploadBloodActivityContract;
import com.dwidar.liveblood.Model.Component.Blood;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.UploadBloodActivityPresenter;
import com.dwidar.liveblood.R;
import com.dwidar.liveblood.databinding.ActivityUploadBloodBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class UploadBloodActivity extends AppCompatActivity implements OnMapReadyCallback , UploadBloodActivityContract.IView
{

    private ActivityUploadBloodBinding bloodBinding;
    private UploadBloodActivityPresenter presenter;

    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String BloodType = "O-";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bloodBinding = ActivityUploadBloodBinding.inflate(getLayoutInflater());
        View view = bloodBinding.getRoot();
        setContentView(view);

        presenter = new UploadBloodActivityPresenter(this);

        bloodBinding.BloodPB.setVisibility(ProgressBar.INVISIBLE);

        bloodBinding.myMapView.onCreate(savedInstanceState);
        bloodBinding.myMapView.onResume();
        bloodBinding.myMapView.getMapAsync(this);
        getLocationPermission();

        bloodBinding.BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                UploadBlood();
            }
        });

        ArrayAdapter<CharSequence> BloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.BloodTypes, android.R.layout.simple_spinner_item);
        BloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodBinding.SpinnerBloodType.setAdapter(BloodAdapter);

        bloodBinding.SpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                BloodType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

    }

    @Override
    public void onStart()
    {
        super.onStart();
        bloodBinding.myMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        bloodBinding.myMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        bloodBinding.myMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        bloodBinding.myMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bloodBinding.myMapView.onDestroy();
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            double longitude = currentLocation.getLongitude();
                            double latitude = currentLocation.getLatitude();

                            moveCamera(new LatLng(latitude, longitude),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        bloodBinding.myMapView.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onSuccessAdd()
    {
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
        bloodBinding.TxtAvailBlood.setText("");
        bloodBinding.TxtNeedBlood.setText("");
        bloodBinding.BloodPB.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onFailAdd(String msg)
    {
        bloodBinding.BloodPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean dataValidation()
    {
        boolean isValid = true;
        String need = bloodBinding.TxtNeedBlood.getText().toString().trim();
        String avail = bloodBinding.TxtAvailBlood.getText().toString().trim();

        if (isBloodExists(this.BloodType))
        {
            isValid = false;
            Toast.makeText(this, "This blood type already exists", Toast.LENGTH_LONG).show();
        }

        if (need.isEmpty())
        {
            isValid = false;
            bloodBinding.TxtNeedBlood.setError("Enter blood needed");
        }

        int intNeed = Integer.parseInt(bloodBinding.TxtNeedBlood.getText().toString());
        if (intNeed < 1)
        {
            isValid = false;
            bloodBinding.TxtNeedBlood.setError("Enter blood needed correctly");
        }

        if (avail.isEmpty())
        {
            isValid = false;
            bloodBinding.TxtAvailBlood.setError("Enter available blood");
        }
        int intAvail = Integer.parseInt(bloodBinding.TxtAvailBlood.getText().toString());

        if (intAvail == 0)
        {
            isValid = false;
            bloodBinding.TxtAvailBlood.setError("Enter Valid Number");
        }



        return isValid;
    }

    private boolean isBloodExists(String blood)
    {
        for (iBlood itr : DataSettings.getBloods())
        {
            if (itr.getType().equals(blood)) return true;
        }

        return false;
    }

    private void UploadBlood()
    {
        if (dataValidation())
        {
            bloodBinding.BloodPB.setVisibility(ProgressBar.VISIBLE);

            int need = Integer.parseInt(bloodBinding.TxtNeedBlood.getText().toString().trim());
            int avail = Integer.parseInt(bloodBinding.TxtAvailBlood.getText().toString().trim());
            int hosId = DataSettings.getMyHospital().getData().getId();

            Blood blood = new Blood(this.BloodType, hosId, need, avail);

            presenter.AddBlood(blood);
        }
    }
}
