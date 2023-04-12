package com.dwidar.liveblood.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.DonnerFragmentContract;
import com.dwidar.liveblood.Model.Component.HospitalComponents.iHospital;
import com.dwidar.liveblood.Presenter.DonnerFragmentPresenter;
import com.dwidar.liveblood.R;
import com.dwidar.liveblood.databinding.FragmentDonnerBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class DonnerFragment extends Fragment implements OnMapReadyCallback, DonnerFragmentContract.IView
{

    private FragmentDonnerBinding donnerBinding;
    private DonnerFragmentContract.IPresenter presenter;

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private LatLng myLocation;
    private List<iHospital> hospitals;
    private ArrayList<LatLng> locations;
    private ArrayList<String> locationNames;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    public DonnerFragment()
    {
        myLocation = new LatLng(0.0, 0.0);
        locations = new ArrayList<>();
        locationNames = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new DonnerFragmentPresenter(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        Log.e("TEST GET LOCATIONS", "onMapReady: 1111111111111111111111");
        presenter.getNearestHospital(myLocation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donnerBinding.myMapView.onCreate(savedInstanceState);
        donnerBinding.myMapView.onResume();
        donnerBinding.myMapView.getMapAsync(this);
        getLocationPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        donnerBinding = FragmentDonnerBinding.inflate(getLayoutInflater());


        return donnerBinding.getRoot();
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

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

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        donnerBinding.myMapView.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
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
    public void onStart()
    {
        super.onStart();
        donnerBinding.myMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        donnerBinding.myMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        donnerBinding.myMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        donnerBinding.myMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        donnerBinding.myMapView.onDestroy();
    }

    @Override
    public void onSuccessGettingHospital(List<iHospital> hospitals)
    {
        this.hospitals = hospitals;

        initInfoForMap(hospitals);
    }

    private void initInfoForMap(List<iHospital> hospitals)
    {

        for (iHospital itr : hospitals)
        {
            locations.add(new LatLng(Double.parseDouble(itr.getLatitude()), Double.parseDouble(itr.getLongitude())));
            locationNames.add(itr.getName());
        }
        showHospitals();
    }

    @Override
    public void onFailGettingHospital(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void showHospitals()
    {
        int lnth = this.locations.size();
        for (int i=0; i < lnth; i++)
        {
            LatLng itr = locations.get(i);
            String name = locationNames.get(i);
            mMap.addMarker(new MarkerOptions().position(itr).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospitalmaplogoiii)));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
