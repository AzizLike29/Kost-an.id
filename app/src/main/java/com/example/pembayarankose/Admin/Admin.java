package com.example.pembayarankose.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pembayarankose.CreateTagihanActivity;
import com.example.pembayarankose.LoginDanRegister.Login;
import com.example.pembayarankose.Entitas.User;
import com.example.pembayarankose.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private ImageView ivKeluar;
    private Button btnBuatTagihan;
    private RecyclerView rvUser;

    private DatabaseReference database;
    private UserAdapter adapter;
    private ArrayList<User> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ivKeluar = findViewById(R.id.ivKeluar);
        btnBuatTagihan = findViewById(R.id.btnBuatTagihan);
        rvUser = findViewById(R.id.rvUser);

        ivKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        btnBuatTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, CreateTagihanActivity.class);
                startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("users");

        rvUser.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(layoutManager);
        rvUser.setItemAnimator(new DefaultItemAnimator());

        // Ambil data dan tampilkan
        tampilData();
    }

    // Metode untuk menampilkan data
    private void tampilData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrayList = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()) {
                    User user = item.getValue(User.class);
                    arrayList.add(user);
                }

                adapter = new UserAdapter(arrayList, new UserAdapter.OnItemClickListener() {
                    @Override
                    public void onEditClick(User user) {
                        // Intent untuk edit data user
                        Intent editIntent = new Intent(Admin.this, EditDataUser.class);
                        editIntent.putExtra("username", user.getUsername());
                        editIntent.putExtra("noHp", user.getNoHp());
                        editIntent.putExtra("password", user.getPassword());
                        startActivity(editIntent);
                    }

                    @Override
                    public void onDeleteClick(User user) {
                        deleteUserData(user.getUsername());
                    }
                });

                rvUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void deleteUserData(String username) {
        database.child(username).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}