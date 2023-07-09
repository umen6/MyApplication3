package com.pb.myapplication3.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pb.myapplication3.R;
import com.pb.myapplication3.helper.DataHelper;


public class DetailPenyewaActivity extends AppCompatActivity {
    String API="http://localhost/sistem-jmsport-web/BookingCar";
    String sNama, sNoreg, sHP, sMerk, sHarga;
    int iLama, iPromo, iTotal;
    double dTotal;

    protected Cursor cursor;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);

        dbHelper = new DataHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from penyewa, mobil, sewa where penyewa.nama = sewa.nama AND mobil.merk = sewa.merk AND penyewa.nama = '" + getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sNama = cursor.getString(0);
            sNoreg = cursor.getString(1);
            sHP = cursor.getString(2);
            sMerk = cursor.getString(3);
            sHarga = cursor.getString(4);
            iPromo = cursor.getInt(7);
            iLama = cursor.getInt(8);
            dTotal = cursor.getDouble(9);
        }

        TextView tvNama = findViewById(R.id.HNama);
        TextView tvNoreg = findViewById(R.id.HNoreg);
        TextView tvHP = findViewById(R.id.HTelp);

        TextView tvMerk = findViewById(R.id.HMerk);

        TextView tvLama = findViewById(R.id.HLamaSewa);

        tvNama.setText("     " + sNama);
        tvNoreg.setText("     " + sNoreg);
        tvHP.setText("     " + sHP);

        tvMerk.setText("     " + sMerk);


        tvLama.setText("     " + iLama + " hari");
        iTotal = (int) dTotal;

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailPenyewa);
        toolbar.setTitle("Detail PenyewaActivity");
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

}