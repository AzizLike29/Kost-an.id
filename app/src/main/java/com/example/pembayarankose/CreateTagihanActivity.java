package com.example.pembayarankose;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTagihanActivity extends AppCompatActivity {

    private EditText etNamaTagihan, etJumlahTagihan;
    private Button btnSimpanTagihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tagihan);

        etNamaTagihan = findViewById(R.id.etNamaTagihan);
        etJumlahTagihan = findViewById(R.id.etJumlahTagihan);
        btnSimpanTagihan = findViewById(R.id.btnSimpanTagihan);

        btnSimpanTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaTagihan = etNamaTagihan.getText().toString().trim();
                String jumlahTagihan = etJumlahTagihan.getText().toString().trim();
                Toast.makeText(CreateTagihanActivity.this, "Tagihan disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}