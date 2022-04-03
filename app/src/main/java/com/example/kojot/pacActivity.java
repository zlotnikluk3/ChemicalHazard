package com.example.kojot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pacActivity extends AppCompatActivity {

    private Button oblButton;
    private EditText stgText1, stgText2, stgText3, stgTextP1, stgTextP2, stgTextP3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac);

        stgText1 = (EditText) findViewById(R.id.editSTG1);
        stgText2 = (EditText) findViewById(R.id.editSTG2);
        stgText3 = (EditText) findViewById(R.id.editSTG3);

        stgTextP1 = (EditText) findViewById(R.id.editSTGp1);
        stgTextP2 = (EditText) findViewById(R.id.editSTGp2);
        stgTextP3 = (EditText) findViewById(R.id.editSTGp3);

        stgTextP1.setText(Double.toString(wyniki.p1));
        stgTextP2.setText(Double.toString(wyniki.p2));
        stgTextP3.setText(Double.toString(wyniki.p3));

        stgText1.setEnabled(false);
        stgText2.setEnabled(false);
        stgText3.setEnabled(false);

        setEdiOnLoad();

        stgText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgText1.isEnabled()) {
                    stgTextP1.setEnabled(false);
                } else {
                    stgTextP1.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgText1.isEnabled()) {
                    if (stgText1.getText().length() > 0)
                        try {
                            stgTextP1.setText(Double.toString(Math.round(10000 * 24.45 * Double.parseDouble(stgText1.getText().toString()) / wyniki.m) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgText1.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgTextP1.getText().clear();
                        stgTextP1.setEnabled(true);
                    }
                }
            }
        });

        stgTextP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgTextP1.isEnabled()) {
                    stgText1.setEnabled(false);
                } else {
                    stgText1.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgTextP1.isEnabled()) {
                    if (stgTextP1.getText().length() > 0)
                        try {
                            stgText1.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP1.getText().toString()) / 24.45) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgTextP1.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgText1.getText().clear();
                        stgText1.setEnabled(true);
                    }
                }
            }
        });

        stgText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgText2.isEnabled()) {
                    stgTextP2.setEnabled(false);
                } else {
                    stgTextP2.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgText2.isEnabled()) {
                    if (stgText2.getText().length() > 0)
                        try {
                            stgTextP2.setText(Double.toString(Math.round(10000 * 24.45 * Double.parseDouble(stgText2.getText().toString()) / wyniki.m) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgText2.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgTextP2.getText().clear();
                        stgTextP2.setEnabled(true);
                    }
                }
            }
        });

        stgTextP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgTextP2.isEnabled()) {
                    stgText2.setEnabled(false);
                } else {
                    stgText2.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgTextP2.isEnabled()) {
                    if (stgTextP2.getText().length() > 0)
                        try {
                            stgText2.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP2.getText().toString()) / 24.45) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgTextP2.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgText2.getText().clear();
                        stgText2.setEnabled(true);
                    }
                }
            }
        });

        stgText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgText3.isEnabled()) {
                    stgTextP3.setEnabled(false);
                } else {
                    stgTextP3.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgText3.isEnabled()) {
                    if (stgText3.getText().length() > 0)
                        try {
                            stgTextP3.setText(Double.toString(Math.round(10000 * 24.45 * Double.parseDouble(stgText3.getText().toString()) / wyniki.m) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgText3.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgTextP3.getText().clear();
                        stgTextP3.setEnabled(true);
                    }
                }
            }
        });

        stgTextP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stgTextP3.isEnabled()) {
                    stgText3.setEnabled(false);
                } else {
                    stgText3.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (stgTextP3.isEnabled()) {
                    if (stgTextP3.getText().length() > 0)
                        try {
                            stgText3.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP3.getText().toString()) / 24.45) / 10000.0));
                        } catch (NumberFormatException e) {
                            stgTextP3.getText().clear();
                            Toast.makeText(getApplicationContext(), "Nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                        }
                    else {
                        stgText3.getText().clear();
                        stgText3.setEnabled(true);
                    }
                }
            }
        });


        oblButton = (Button) findViewById(R.id.oblButton);
        oblButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stgText1.getText().length() > 0 && stgText2.getText().length() > 0 && stgText3.getText().length() > 0) {
                    wyniki.xg1 = Math.round(100 * wyniki.xg / Math.sqrt(Double.parseDouble(stgText1.getText().toString()))) / 100.0;
                    wyniki.xg2 = Math.round(100 * wyniki.xg / Math.sqrt(Double.parseDouble(stgText2.getText().toString()))) / 100.0;
                    wyniki.xg3 = Math.round(100 * wyniki.xg / Math.sqrt(Double.parseDouble(stgText3.getText().toString()))) / 100.0;
                    if (Double.parseDouble(stgText1.getText().toString()) == 0.0)
                        wyniki.xg1 = 0;
                    if (Double.parseDouble(stgText2.getText().toString()) == 0.0)
                        wyniki.xg2 = 0;
                    if (Double.parseDouble(stgText3.getText().toString()) == 0.0)
                        wyniki.xg3 = 0;
                    if (Double.parseDouble(stgText1.getText().toString()) < Double.parseDouble(stgText2.getText().toString()) &&
                            Double.parseDouble(stgText2.getText().toString()) < Double.parseDouble(stgText3.getText().toString()))
                        openFinalActivity();
                    else
                        Toast.makeText(getApplicationContext(), "Spełnij warunek PAC1<PAC2<PAC3", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openFinalActivity() {
        Intent intent = new Intent(this, Final.class);
        startActivity(intent);
    }

    public void setEdiOnLoad() {
        stgText1.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP1.getText().toString()) / 24.45) / 10000.0));
        stgText2.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP2.getText().toString()) / 24.45) / 10000.0));
        stgText3.setText(Double.toString(Math.round(10000 * wyniki.m * Double.parseDouble(stgTextP3.getText().toString()) / 24.45) / 10000.0));
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
