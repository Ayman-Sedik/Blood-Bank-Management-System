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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.UpdateHospitalActivityContract;
import com.dwidar.liveblood.Model.Component.DataSettings;
import com.dwidar.liveblood.Model.Component.HospitalComponents.Hospital;
import com.dwidar.liveblood.Presenter.UpdateHospitalActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityUpdateHospitalBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UpdateHospitalActivity extends AppCompatActivity implements OnMapReadyCallback, UpdateHospitalActivityContract.IView
{

    private final int PHONE_LENGTH = 11;
    private final int PWD_MIN_LENGTH = 8;
    private ActivityUpdateHospitalBinding binding;
    private UpdateHospitalActivityPresenter presenter;
    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng myLocation;
    private LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myLocation = new LatLng(0.0, 0.0);
        selectedLocation = new LatLng(0.0, 0.0);

        presenter = new UpdateHospitalActivityPresenter(this);

        binding.UpdateHosPB.setVisibility(ProgressBar.INVISIBLE);

        binding.myMapView.onCreate(savedInstanceState);
        binding.myMapView.onResume();
        binding.myMapView.getMapAsync(this);
        getLocationPermission();

        binding.BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                UpdateHospital();
            }
        });

        binding.getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                moveCamera(new LatLng(selectedLocation.latitude, selectedLocation.longitude), DEFAULT_ZOOM);
            }
        });

        initializeInfo();
    }

    private void initializeInfo()
    {
        binding.TxtHosName.setText(DataSettings.getMyHospital().getData().getName());
        binding.TxtHosEmail.setText(DataSettings.getMyHospital().getData().getEmail());
        binding.TxtHosAddress.setText(DataSettings.getMyHospital().getData().getAddress());
        binding.TxtHosPhone.setText(DataSettings.getMyHospital().getData().getPhone());
        binding.TxtHosPwd.setText(DataSettings.getHospitalPwd());

        LatLng location =
                new LatLng(Double.parseDouble(DataSettings.getMyHospital().getData().getLatitude()),
                        Double.parseDouble(DataSettings.getMyHospital().getData().getLongitude()));

        this.selectedLocation = location;
    }

    private void initSelectedLocation()
    {
        String name = DataSettings.getMyHospital().getData().getName();
        mMap.addMarker(new MarkerOptions().position(this.selectedLocation).title(name));
        moveCamera(this.selectedLocation, DEFAULT_ZOOM);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng)
            {
                mMap.clear();
                selectedLocation = latLng;
                mMap.addMarker(new MarkerOptions().position(latLng).title("Selected location"));
            }
        });

        initSelectedLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.selectedLocation.latitude, this.selectedLocation.longitude), DEFAULT_ZOOM));
    }

    @Override
    public void onStart()
    {
        super.onStart();
        binding.myMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.myMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.myMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.myMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.myMapView.onDestroy();
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

                            myLocation = new LatLng(latitude, longitude);

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

    private void moveCamera(LatLng latLng, float zoom)
    {
        Log.e("TEST MOVE CAMERA", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        binding.myMapView.getMapAsync(this);
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
    public void onSuccessUpdate()
    {
        binding.UpdateHosPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailUpdate(String msg)
    {
        binding.UpdateHosPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean dataValidation()
    {
        boolean isValid = true;
        String name = binding.TxtHosName.getText().toString().trim();
        String address = binding.TxtHosAddress.getText().toString().trim();
        String email = binding.TxtHosEmail.getText().toString().trim();
        String phone = binding.TxtHosPhone.getText().toString().trim();
        String pwd = binding.TxtHosPwd.getText().toString().trim();

        if (name.isEmpty())
        {
            isValid = false;
            binding.TxtHosName.setError("Enter name");
        }

        if (address.isEmpty())
        {
            isValid = false;
            binding.TxtHosAddress.setError("Enter address");
        }

        if (email.isEmpty())
        {
            isValid = false;
            binding.TxtHosEmail.setError("Enter email");
        }

        if (phone.isEmpty())
        {
            isValid = false;
            binding.TxtHosPhone.setError("Enter phone");
        }

        if (phone.length() != PHONE_LENGTH)
        {
            isValid = false;
            binding.TxtHosPhone.setError("Invalid number, it must contain 11 digits");
        }

        if (pwd.isEmpty())
        {
            isValid = false;
            binding.TxtHosPwd.setError("Enter password");
        }

        if (pwd.length() < PWD_MIN_LENGTH)
        {
            isValid = false;
            binding.TxtHosPwd.setError("Password is too short, it must contain at least 8 chars");
        }

        return isValid;
    }

    private void UpdateHospital()
    {
        if (dataValidation())
        {
            binding.UpdateHosPB.setVisibility(ProgressBar.VISIBLE);

            String name = binding.TxtHosName.getText().toString().trim();
            String address = binding.TxtHosAddress.getText().toString().trim();
            String email = binding.TxtHosEmail.getText().toString().trim();
            String phone = binding.TxtHosPhone.getText().toString().trim();
            String pwd = binding.TxtHosPwd.getText().toString().trim();

            Hospital hospital = new Hospital(name, email, pwd, address, phone,
                    String.valueOf(selectedLocation.longitude),
                    String.valueOf(selectedLocation.latitude));

            presenter.Updatehospital(DataSettings.getMyHospital().getData().getId(), hospital);
        }
    }
}
