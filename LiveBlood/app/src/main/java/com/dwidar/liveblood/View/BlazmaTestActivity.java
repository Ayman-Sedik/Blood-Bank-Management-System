package com.dwidar.liveblood.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dwidar.liveblood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BlazmaTestActivity extends AppCompatActivity {
    EditText BlazmaNameTV;
    EditText BlazmaAddressTV;
    EditText BlazmaPhoneTV;
    Button BlazmaTestAddBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blazma_test);
        BlazmaNameTV = findViewById(R.id.BlazmaNameTV);
        BlazmaAddressTV = findViewById(R.id.BlazmaAddressTV);
        BlazmaPhoneTV = findViewById(R.id.BlazmaPhoneTV);
        BlazmaTestAddBTN = findViewById(R.id.BlazmaAddBTN);
        BlazmaTestAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });
    }

    private void AddData() {
        if (BlazmaNameTV.getText().toString().trim().isEmpty() || BlazmaAddressTV.getText().toString().trim().isEmpty() || BlazmaPhoneTV.getText().toString().trim().isEmpty()) {
            if (BlazmaNameTV.getText().toString().trim().isEmpty()) {
                BlazmaNameTV.setError("empty");
            }
            if (BlazmaAddressTV.getText().toString().trim().isEmpty()) {
                BlazmaAddressTV.setError("empty");
            }
            if (BlazmaPhoneTV.getText().toString().trim().isEmpty()) {
                BlazmaPhoneTV.setError("empty");
            }
        } else {
            if (isNetworkConnected()) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://bloodsystem-ae1c1-default-rtdb.firebaseio.com/").getReference().child("Blood plasma test").child(BlazmaNameTV.getText().toString());
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("Name", BlazmaNameTV.getText().toString());
                stringStringHashMap.put("Address", BlazmaAddressTV.getText().toString());
                stringStringHashMap.put("Phone", BlazmaPhoneTV.getText().toString());
                databaseReference.setValue(stringStringHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        BlazmaNameTV.setText("");
                        BlazmaAddressTV.setText("");
                        BlazmaPhoneTV.setText("");
                        Toast.makeText(getApplicationContext(), "تم الأضافة", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "خطأ", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "error no internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}