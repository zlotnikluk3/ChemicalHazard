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

public class LiqPoolActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText temp, editDyf, editKin, editSch, editPpar, ad, ah;
    private Button stcButton;
    private Algorithmes a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liq_pool);

        temp = (EditText) findViewById(R.id.editTemp);
        ad = (EditText) findViewById(R.id.editSr);
        ah = (EditText) findViewById(R.id.editAh);
        editDyf = (EditText) findViewById(R.id.editDyf);
        editKin = (EditText) findViewById(R.id.editKin);
        temp = (EditText) findViewById(R.id.editTemp);
        editPpar = (EditText) findViewById(R.id.editPpar);
        editSch = (EditText) findViewById(R.id.editSch);

        editSch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    editDyf.setEnabled(false);
                    editKin.setEnabled(false);
                } else {
                    editDyf.setEnabled(true);
                    editKin.setEnabled(true);
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

        editKin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    editSch.setEnabled(false);
                } else {
                    if (editDyf.length() == 0) {
                        editSch.setEnabled(true);
                    }
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

        editDyf.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    editSch.setEnabled(false);
                } else {
                    if (editKin.length() == 0) {
                        editSch.setEnabled(true);
                    }
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

        stcButton = (Button) findViewById(R.id.dalej);
        stcButton.setOnClickListener(this);

        a = new Algorithmes();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v == stcButton) {
            if (temp.getText().length() > 0 && editPpar.getText().length() > 0 && ad.getText().length() > 0
                    && (editSch.getText().length() > 0 || (editDyf.getText().length() > 0 && editKin.getText().length() > 0))) {
                try {
                    wyniki.kg1 = a.kG(Nsc(), 1.0, Double.parseDouble(String.valueOf(ad.getText())));
                    wyniki.Qb = a.qPrePar(1.0, Double.parseDouble(String.valueOf(ad.getText())), Double.parseDouble(String.valueOf(editPpar.getText())), getMLiq(), Double.parseDouble(String.valueOf(temp.getText())));
                    openStClassActivity();
                } catch (NumberFormatException e) {
                    temp.getText().clear();
                    editDyf.getText().clear();
                    editKin.getText().clear();
                    editSch.getText().clear();
                    editPpar.getText().clear();
                    Toast.makeText(getApplicationContext(), "Znaleniono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Uzupełnij brakujące pola", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Double getMLiq() {
        String cp;
        SQLiteDB db = new SQLiteDB(getApplicationContext());
        Cursor curs = db.retrieve();
        curs.moveToPosition(LiqChoActivity.getPos());
        cp = curs.getString(2);
        curs.close();
        db.close();
        return Double.parseDouble(cp);
    }

    public Double Nsc() {
        if (editSch.length() > 0)
            return Double.parseDouble(String.valueOf(editSch.getText()));
        else
            return Double.parseDouble(String.valueOf(editKin.getText())) / Double.parseDouble(String.valueOf(editDyf.getText()));
    }


    public void openStClassActivity() {
        Intent intent = new Intent(this, stclass.class);
        startActivity(intent);
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
