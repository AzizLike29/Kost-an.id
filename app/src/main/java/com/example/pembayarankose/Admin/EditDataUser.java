package com.example.pembayarankose.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pembayarankose.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.HashMap;

public class EditDataUser extends AppCompatActivity {

    private EditText etUsername, etNoHp, etPassword;
    private Button btnSimpan, btnBatal;

    private String username, noHp, password;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_user);

        etUsername = findViewById(R.id.etUsername);
        etNoHp = findViewById(R.id.etNoHp);
        etPassword = findViewById(R.id.etPassword);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        if (getIntent().hasExtra("username") && getIntent().hasExtra("noHp") && getIntent().hasExtra("password")) {
            username = getIntent().getStringExtra("username");
            noHp = getIntent().getStringExtra("noHp");
            password = getIntent().getStringExtra("password");

            etUsername.setText(username);
            etNoHp.setText(noHp);
            etPassword.setText(password);
        }

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noHpBaru = etNoHp.getText().toString();
                String passwordBaru = etPassword.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("noHp", noHpBaru);
                hashMap.put("password", passwordBaru);

                database.child(username).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Update Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
