package com.pb.myapplication3.activity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pb.myapplication3.R;
import com.pb.myapplication3.helper.DataHelper;

public class SewaMobilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String API="http://localhost/sistem-jmsport-web/BookingCar";
    EditText nama, noreg, no_hp, lama;
    RadioGroup promo;
    RadioButton weekday, weekend;

    Button selesai;

    String sNama, sNoreg, sNo, sMerk, sLama;
    double dPromo;
    int iLama, iPromo, iHarga;
    double dTotal;

    private Spinner spinner;
    DataHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        dbHelper = new DataHelper(this);


        nama = findViewById(R.id.eTNama);
        noreg = findViewById(R.id.eTNoreg);
        selesai = findViewById(R.id.btnSelesai);
//        no_hp = findViewById(R.id.eTHP);


//        spinner.setOnItemSelectedListener(this);

//        loadSpinnerData();

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNama = nama.getText().toString();
                sNoreg = noreg.getText().toString();
                sNo = no_hp.getText().toString();
                sLama = lama.getText().toString();
                if (sNama.isEmpty() || sNoreg.isEmpty() || sNo.isEmpty() || sLama.isEmpty()) {
                    Toast.makeText(SewaMobilActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (weekday.isChecked()) {
                    dPromo = 0.1;
                } else if (weekend.isChecked()) {
                    dPromo = 0.25;
                }

                if (sMerk.equals("Avanza")) {
                    iHarga = 400000;
                } else if (sMerk.equals("Xenia")) {
                    iHarga = 400000;
                } else if (sMerk.equals("Ertiga")) {
                    iHarga = 400000;
                } else if (sMerk.equals("APV")) {
                    iHarga = 450000;
                } else if (sMerk.equals("Innova")) {
                    iHarga = 500000;
                } else if (sMerk.equals("Xpander")) {
                    iHarga = 550000;
                } else if (sMerk.equals("Pregio")) {
                    iHarga = 550000;
                } else if (sMerk.equals("Elf")) {
                    iHarga = 700000;
                } else if (sMerk.equals("Alphard")) {
                    iHarga = 1500000;
                }

                iLama = Integer.parseInt(sLama);
                iPromo = (int) (dPromo * 100);
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);

                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                        sNama + "','" +
                        sNoreg + "','" +
                        sNo + "');");
                dbH.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" +
                        sMerk + "','" +
                        sNama + "','" +
                        iPromo + "','" +
                        iLama + "','" +
                        dTotal + "');");
                PenyewaActivity.m.RefreshList();
                finish();

            }
        });

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaMobl);
        toolbar.setTitle("Sewa Mobil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void loadSpinnerData() {
//        DataHelper db = new DataHelper(getApplicationContext());
//        List<String> categories = db.getAllCategories();
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}