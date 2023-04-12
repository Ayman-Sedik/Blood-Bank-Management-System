package com.dwidar.liveblood.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwidar.liveblood.Contracts.UserSignUpActivityContract;
import com.dwidar.liveblood.Model.Component.User;
import com.dwidar.liveblood.Presenter.UserSignUpActivityPresenter;
import com.dwidar.liveblood.databinding.ActivityUserSignUpBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserSignUpActivity extends AppCompatActivity implements UserSignUpActivityContract.IView
{
    private final int PHONE_LENGTH = 11;
    private final int PWD_MIN_LENGTH = 8;
    private ActivityUserSignUpBinding signUpBinding;
    private UserSignUpActivityPresenter presenter;

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LatLng curLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivityUserSignUpBinding.inflate(getLayoutInflater());
        View view = signUpBinding.getRoot();
        setContentView(view);

        curLocation = new LatLng(0.0, 0.0);

        presenter = new UserSignUpActivityPresenter(this);
        signUpBinding.UserRegPB.setVisibility(ProgressBar.INVISIBLE);

        signUpBinding.BtnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                backToUserLogin();
            }
        });

        signUpBinding.BtnUserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                userSignUp();
            }
        });

        getLocationPermission();
        getLocation();

    }

    private void backToUserLogin()
    {
        Intent intent = new Intent(UserSignUpActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void userSignUp()
    {
        if (dataValidation())
        {
            signUpBinding.UserRegPB.setVisibility(ProgressBar.VISIBLE);

            String name = signUpBinding.TxtUserNameReg.getText().toString().trim();
            String email = signUpBinding.TxtUserEmailReg.getText().toString().trim();
            String address = signUpBinding.TxtUserAddressReg.getText().toString().trim();
            String phone = signUpBinding.TxtUserPhoneReg.getText().toString().trim();
            String pwd = signUpBinding.TxtUserPwdReg.getText().toString().trim();
            String blood = "";

            // String lat = signUpBinding.TxtUserLatReg.getText().toString().trim();
            // String lng = signUpBinding.TxtUserLngReg.getText().toString().trim();

            User user = new User(name, email, pwd, phone, address, blood, String.valueOf(curLocation.latitude), String.valueOf(curLocation.longitude));

            presenter.UserRegister(user);
        }
    }

    private boolean dataValidation()
    {
        boolean isValid = true;

        String name = signUpBinding.TxtUserNameReg.getText().toString().trim();
        String email = signUpBinding.TxtUserEmailReg.getText().toString().trim();
        String address = signUpBinding.TxtUserAddressReg.getText().toString().trim();
        String phone = signUpBinding.TxtUserPhoneReg.getText().toString().trim();

        String pwd = signUpBinding.TxtUserPwdReg.getText().toString().trim();
        String repwd = signUpBinding.TxtUserRePwdReg.getText().toString().trim();

        if (name.isEmpty())
        {
            signUpBinding.TxtUserNameReg.setError("Enter your name");
            isValid = false;
        }

        if (email.isEmpty())
        {
            signUpBinding.TxtUserEmailReg.setError("Enter your email");
            isValid = false;
        }

        if (address.isEmpty())
        {
            signUpBinding.TxtUserAddressReg.setError("Enter your address");
            isValid = false;
        }

        if (phone.isEmpty())
        {
            signUpBinding.TxtUserPhoneReg.setError("Enter your phone");
            isValid = false;
        }

        if (phone.length() != PHONE_LENGTH)
        {
            signUpBinding.TxtUserPhoneReg.setError("Invalid phone number, must contain 11 numbers only");
            isValid = false;
        }

        if (pwd.isEmpty())
        {
            signUpBinding.TxtUserPwdReg.setError("Enter suitable password");
            isValid = false;
        }

        if (pwd.length() < PWD_MIN_LENGTH)
        {
            signUpBinding.TxtUserPwdReg.setError("Password is too short, it must at least 8 chars");
            isValid = false;
        }

        if (!pwd.equals(repwd))
        {
            signUpBinding.TxtUserRePwdReg.setError("Password does not match");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onsuccessRegister()
    {
        signUpBinding.UserRegPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
        backToUserLogin();
    }

    @Override
    public void onFailSuccessRegister(String msg)
    {
        signUpBinding.UserRegPB.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void getLocation()
    {
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
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
                        if(task.isSuccessful() && task.getResult() != null)
                        {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            double latitude = currentLocation.getLatitude();
                            double longitude = currentLocation.getLongitude();

                            UserSignUpActivity.this.curLocation = new LatLng(latitude, longitude);

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

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
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
                }
            }
        }
    }

}
