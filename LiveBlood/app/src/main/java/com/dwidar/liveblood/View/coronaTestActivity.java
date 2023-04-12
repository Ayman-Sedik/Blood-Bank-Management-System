package com.dwidar.liveblood.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dwidar.liveblood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.HashMap;

public class coronaTestActivity extends AppCompatActivity {
    EditText coronaTestNameTV;
    EditText coronaTestAddressTV;
    EditText coronaTestBloodTypeTV;
    EditText coronaTestPhoneTV;
    Button coronaTestAddBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_test);
        coronaTestNameTV = findViewById(R.id.coronaTestNameTV);
        coronaTestAddressTV = findViewById(R.id.coronaTestAddressTV);
        coronaTestBloodTypeTV = findViewById(R.id.coronaTestBloodTypeTV);
        coronaTestPhoneTV = findViewById(R.id.coronaTestPhoneTV);
        coronaTestAddBTN = findViewById(R.id.coronaTestAddBTN);
        coronaTestAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coronaTestNameTV.getText().toString().trim().isEmpty() || coronaTestAddressTV.getText().toString().trim().isEmpty() || coronaTestBloodTypeTV.getText().toString().trim().isEmpty() || coronaTestPhoneTV.getText().toString().trim().isEmpty()) {
                    if (coronaTestNameTV.getText().toString().trim().isEmpty()) {
                        coronaTestNameTV.setError("empty");
                    }
                    if (coronaTestAddressTV.getText().toString().trim().isEmpty()) {
                        coronaTestAddressTV.setError("empty");
                    }
                    if (coronaTestBloodTypeTV.getText().toString().trim().isEmpty()) {
                        coronaTestBloodTypeTV.setError("empty");
                    }
                    if (coronaTestPhoneTV.getText().toString().trim().isEmpty()) {
                        coronaTestPhoneTV.setError("empty");
                    }
                } else {
                    if (isNetworkConnected()) {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("Name", coronaTestNameTV.getText().toString());
                        stringStringHashMap.put("Address", coronaTestAddressTV.getText().toString());
                        stringStringHashMap.put("BloodType", coronaTestBloodTypeTV.getText().toString());
                        stringStringHashMap.put("Phone", coronaTestPhoneTV.getText().toString());


                        FirebaseDatabase.getInstance("https://bloodsystem-ae1c1-default-rtdb.firebaseio.com/").getReference().child("coronaTest").child(coronaTestNameTV.getText().toString()).setValue(stringStringHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                coronaTestNameTV.setText("");
                                coronaTestAddressTV.setText("");
                                coronaTestBloodTypeTV.setText("");
                                coronaTestPhoneTV.setText("");
                                Toast.makeText(getApplicationContext(), "تم الأضافة", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "خطأ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Toast.makeText(getApplicationContext(), "error no internet", Toast.LENGTH_SHORT).show();

                    }
                }
            }});
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}