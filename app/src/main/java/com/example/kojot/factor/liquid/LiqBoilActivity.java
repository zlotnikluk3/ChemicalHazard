package com.example.kojot.factor.liquid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kojot.Algorithmes;
import com.example.kojot.R;
import com.example.kojot.factor.liquid.db.SQLiteDB;
import com.example.kojot.stclass;
import com.example.kojot.wyniki;


public class LiqBoilActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText temp, Ro, ah, ad, pz, pa, hs, twrz, cpar;
    private Button stcButton;
    private Algorithmes a;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liq_boil);
        temp = (EditText) findViewById(R.id.editTemp);
        Ro = (EditText) findViewById(R.id.editRoB);
        ad = (EditText) findViewById(R.id.editSr);
        ah = (EditText) findViewById(R.id.editAh);
        pz = (EditText) findViewById(R.id.editPz);
        pa = (EditText) findViewById(R.id.editPa);
        hs = (EditText) findViewById(R.id.editHwys);
        twrz = (EditText) findViewById(R.id.editWrz);
        cpar = (EditText) findViewById(R.id.editPar);

        stcButton = (Button) findViewById(R.id.dalej);
        stcButton.setOnClickListener(this);

        pa.setText(Integer.toString(101325));

        ad.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ad.isEnabled()) {
                    ah.setEnabled(false);
                } else {
                    ah.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (ad.isEnabled()) {
                    if (ad.length() > 0)
                        try {
                            ah.setText(Double.toString(Math.round(10000 * 3.14 * 0.25 * Math.pow(Double.parseDouble(ad.getText().toString()), 2)) / 10000.0));
                        } catch (NumberFormatException e) {
                            ad.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        ah.getText().clear();
                        ah.setEnabled(true);
                    }
                }

            }
        });

        ah.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ah.isEnabled()) {
                    ad.setEnabled(false);
                } else {
                    ad.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (ah.isEnabled()) {
                    if (ah.length() > 0)
                        try {
                            ad.setText(Double.toString(Math.round(10000 * Math.sqrt(4 / 3.14 * Double.parseDouble(ah.getText().toString()))) / 10000.0));
                        } catch (NumberFormatException e) {
                            ah.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        ad.getText().clear();
                        ad.setEnabled(true);
                    }
                }
            }
        });

        a = new Algorithmes();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onClick(View v) {

        if (v == stcButton) {
            if (temp.getText().length() > 0 && Ro.getText().length() > 0 && ah.getText().length() > 0 && pz.getText().length() > 0
                    && pa.getText().length() > 0 && hs.getText().length() > 0 && cpar.getText().length() > 0 && twrz.getText().length() > 0) {
                try {
                    double Qc = a.boilLiqQc(Double.parseDouble(String.valueOf(ah.getText())), Double.parseDouble(String.valueOf(Ro.getText())),
                            Double.parseDouble(String.valueOf(pz.getText())), Double.parseDouble(String.valueOf(pa.getText())),
                            Double.parseDouble(String.valueOf(hs.getText())));
                    wyniki.Q = Math.round(100 * a.boilLiqQq(getCpLiq(), Double.parseDouble(String.valueOf(temp.getText())),
                            Double.parseDouble(String.valueOf(twrz.getText())), Double.parseDouble(String.valueOf(cpar.getText())), Qc)) / 100.0;
                    openStClassActivity();
                } catch (NumberFormatException e) {
                    temp.getText().clear();
                    Ro.getText().clear();
                    pa.setText(Integer.toString(101325));
                    pz.getText().clear();
                    hs.getText().clear();
                    twrz.getText().clear();
                    cpar.getText().clear();
                    Toast.makeText(getApplicationContext(), "Znaleniono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Double getCpLiq() {
        String cp;
        SQLiteDB db = new SQLiteDB(getApplicationContext());
        Cursor curs = db.retrieve();
        curs.moveToPosition(LiqChoActivity.getPos());
        cp = curs.getString(3);
        curs.close();
        db.close();
        return Double.parseDouble(cp);
    }

    public void openStClassActivity() {
        Intent intent = new Intent(this, stclass.class);
        startActivity(intent);
    }
}
