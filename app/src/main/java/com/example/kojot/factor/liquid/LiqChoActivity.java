package com.example.kojot.factor.liquid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kojot.ProgActivity;
import com.example.kojot.R;
import com.example.kojot.factor.liquid.db.SQLiteDB;
import com.example.kojot.wyniki;

import java.util.List;

public class LiqChoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Button liqButton;
    private int i;
    private static int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liq_cho);

        liqButton = (Button) findViewById(R.id.liqDalej);
        liqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProgActivity.getFlag() == 1)
                    openPoolCalcActivity();
                else if (ProgActivity.getFlag() == 2)
                    openBoilCalcActivity();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        i = 0;
        pos = 0;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openPoolCalcActivity() {
        setPac(pos);
        Intent intent = new Intent(this, LiqPoolActivity.class);
        startActivity(intent);
    }

    public void openBoilCalcActivity() {
        setPac(pos);
        Intent intent = new Intent(this, LiqBoilActivity.class);
        startActivity(intent);
    }

    private void loadSpinnerData() {

        SQLiteDB db = new SQLiteDB(getApplicationContext());

        List<String> labels = db.getAllLabels();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String label = parent.getItemAtPosition(position).toString();
        pos = spinner.getSelectedItemPosition();

        if (i > 0) {
            Toast.makeText(parent.getContext(), "Wybrano: " + label,
                    Toast.LENGTH_LONG).show();
        }
        i++;
    }

    public static int getPos() {
        return pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setPac(int p) {
        String cp;
        SQLiteDB db = new SQLiteDB(getApplicationContext());
        Cursor curs = db.retrieve();
        curs.moveToPosition(p);
        if (!curs.getString(4).isEmpty())
            wyniki.p1 = Double.parseDouble(curs.getString(4));
        else
            wyniki.p1 = 0;
        if (!curs.getString(5).isEmpty())
            wyniki.p2 = Double.parseDouble(curs.getString(5));
        else
            wyniki.p2 = 0;
        if (!curs.getString(6).isEmpty())
            wyniki.p3 = Double.parseDouble(curs.getString(6));
        else
            wyniki.p3 = 0;
        wyniki.m = Double.parseDouble(curs.getString(2));
        curs.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
