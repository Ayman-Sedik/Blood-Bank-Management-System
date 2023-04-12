package com.dwidar.liveblood.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dwidar.liveblood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.util.HashMap;

public class resultActivity extends AppCompatActivity {
    EditText ResultNameTV;
    EditText ResultAddressTV;
    EditText ResultPhoneTV;
    EditText ResultTV;
    Button   ResultAddBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ResultNameTV=findViewById(R.id.nameAddResultTv);
        ResultAddressTV=findViewById(R.id.addressResultTv);
        ResultPhoneTV=findViewById(R.id.phoneResultTv);
        ResultTV=findViewById(R.id.resultTv);
        ResultAddBTN=findViewById(R.id.BtnAddTheResult);
        ResultAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });
    }
    private void AddData(){
        if (ResultNameTV.getText().toString().trim().isEmpty()||ResultAddressTV.getText().toString().trim().isEmpty()||ResultPhoneTV.getText().toString().trim().isEmpty()||ResultTV.getText().toString().trim().isEmpty()){
            if (ResultNameTV.getText().toString().trim().isEmpty()){
                ResultNameTV.setError("empty");
            }
            if (ResultAddressTV.getText().toString().trim().isEmpty()){
                ResultAddressTV.setError("empty");
            }
            if (ResultPhoneTV.getText().toString().trim().isEmpty()){
                ResultPhoneTV.setError("empty");
            }
            if (ResultTV.getText().toString().trim().isEmpty()){
                ResultTV.setError("empty");
            }
        } else {
            if (isNetworkConnected()) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://bloodsystem-ae1c1-default-rtdb.firebaseio.com/").getReference().child("corona Result").child(ResultNameTV.getText().toString());
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("Name", ResultNameTV.getText().toString());
                stringStringHashMap.put("Address", ResultAddressTV.getText().toString());
                stringStringHashMap.put("Phone", ResultPhoneTV.getText().toString());
                stringStringHashMap.put("Result", ResultTV.getText().toString());
                databaseReference.setValue(stringStringHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ResultNameTV.setText("");
                        ResultAddressTV.setText("");
                        ResultPhoneTV.setText("");
                        ResultTV.setText("");
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
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}