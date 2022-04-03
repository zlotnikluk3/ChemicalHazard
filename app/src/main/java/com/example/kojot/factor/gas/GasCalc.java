package com.example.kojot.factor.gas;

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
import com.example.kojot.factor.gas.db.SQLiteDB;
import com.example.kojot.stclass;
import com.example.kojot.wyniki;

public class GasCalc extends AppCompatActivity implements View.OnClickListener {

    private EditText temp, Ro, ad, ah, pz, pa;
    private Button stcButton;
    private Algorithmes a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_calc);

        stcButton = (Button) findViewById(R.id.dalej);
        stcButton.setOnClickListener(this);

        temp = (EditText) findViewById(R.id.editTemp);
        Ro = (EditText) findViewById(R.id.editRo);
        ad = (EditText) findViewById(R.id.editAd);
        ah = (EditText) findViewById(R.id.editAh);
        pz = (EditText) findViewById(R.id.editPz);
        pa = (EditText) findViewById(R.id.editPa);
        pa.setText(Integer.toString(101325));

        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    Ro.setEnabled(false);
                } else {
                    Ro.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        Ro.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    temp.setEnabled(false);
                } else {
                    temp.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

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
                    ;
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
            if ((temp.getText().length() > 0 || Ro.getText().length() > 0) && ah.getText().length() > 0 && pz.getText().length() > 0
                    && pa.getText().length() > 0) {
                double T = 0.0, gest = 0.0, y = Double.parseDouble(getGas()[1]) / Double.parseDouble(getGas()[2]);
                if (temp.getText().length() > 0) {
                    try {
                        T = Double.parseDouble(String.valueOf(temp.getText()));
                    } catch (NumberFormatException e) {
                        temp.getText().clear();
                        Toast.makeText(getApplicationContext(), "Znaleziono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        gest = Double.parseDouble(String.valueOf(Ro.getText()));
                    } catch (NumberFormatException e) {
                        Ro.getText().clear();
                        Toast.makeText(getApplicationContext(), "Znaleziono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                    }
                }
                try {
                    if (a.dlaw(Double.parseDouble(String.valueOf(pz.getText())), Double.parseDouble(String.valueOf(pa.getText())),
                            Double.parseDouble(getGas()[1]), Double.parseDouble(getGas()[2]))) {
                        wyniki.Q = Math.round(100 * a.wypDlaw(Double.parseDouble(String.valueOf(ah.getText())), Double.parseDouble(getGas()[1]), Double.parseDouble(getGas()[2]),
                                T, gest, Double.parseDouble(String.valueOf(pz.getText())), Double.parseDouble(getGas()[0]))) / 100.0;
                    } else {
                        wyniki.Q = Math.round(100 * a.wypNdlaw(Double.parseDouble(String.valueOf(ah.getText())), Double.parseDouble(getGas()[1]), Double.parseDouble(getGas()[2]),
                                T, gest, Double.parseDouble(String.valueOf(pz.getText())), Double.parseDouble(String.valueOf(pa.getText())), Double.parseDouble(getGas()[0]))) / 100.0;
                    }
                    openStClassActivity();
                } catch (NumberFormatException e) {
                    pz.getText().clear();
                    pa.setText(Integer.toString(101325));
                    Toast.makeText(getApplicationContext(), "Znaleniono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Uzupełnij brakujące pola", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String[] getGas() {
        String[] tab = new String[3];
        SQLiteDB db = new SQLiteDB(getApplicationContext());
        Cursor curs = db.retrieve();
        curs.moveToPosition(GasChoActivity.getPos());
        tab[0] = curs.getString(2);
        tab[1] = curs.getString(3);
        tab[2] = curs.getString(4);
        curs.close();
        db.close();
        return tab;
    }

    public void openStClassActivity() {
        Intent intent = new Intent(this, stclass.class);
        startActivity(intent);
    }
}
