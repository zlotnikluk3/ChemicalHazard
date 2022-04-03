package com.example.kojot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

public class stclass extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String lattitude, longitude;

    private Button btnDatePicker, btnTimePicker, geoButton, oblButton, inCoBtn;
    private EditText txtDate, txtTime, DlSt, DlMin, Dlsek, SzSt, SzMin, Szsek, wind;
    private int mYear, mMonth, mDay, mHour, mMinute, godz, min, dzien, mies, rok;
    private RadioGroup radioMWGroup;
    private RadioButton miasto;
    private Spinner spiDl, spiSz, spiOkt;
    private Algorithmes a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stclass);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        geoButton = (Button) findViewById(R.id.geoButton);
        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocation();
                }
            }
        });

        inCoBtn = (Button) findViewById(R.id.inCoButton);
        inCoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        oblButton = (Button) findViewById(R.id.stDbutton);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        wind = (EditText) findViewById(R.id.editWind);

        DlSt = (EditText) findViewById(R.id.editDlSt);
        DlMin = (EditText) findViewById(R.id.editDlMin);
        Dlsek = (EditText) findViewById(R.id.editDlSek);
        SzSt = (EditText) findViewById(R.id.editSzSt);
        SzMin = (EditText) findViewById(R.id.editSzMin);
        Szsek = (EditText) findViewById(R.id.editSzSek);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        oblButton.setOnClickListener(this);

        radioMWGroup = (RadioGroup) findViewById(R.id.mwRadioGroup);
        miasto = (RadioButton) findViewById(R.id.miasto);
        RadioButton wies = (RadioButton) findViewById(R.id.wies);
        miasto.setChecked(true);
        radioMWGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio geoButton is selected
                if (checkedId == R.id.miasto) {
                    Toast.makeText(getApplicationContext(), "choice: miasto",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "choice: wieś",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        spiDl = (Spinner) findViewById(R.id.spinnerDl);
        spiDl.setOnItemSelectedListener(this);
        spiSz = (Spinner) findViewById(R.id.spinnerSz);
        spiSz.setOnItemSelectedListener(this);
        spiOkt = (Spinner) findViewById(R.id.spinnerOkt);
        spiOkt.setOnItemSelectedListener(this);

        a = new Algorithmes();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        setFromMap(inMap.getP());
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

        if (v == btnDatePicker) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            dzien = dayOfMonth;
                            mies = monthOfYear;
                            rok = year;

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            min = minute;
                            godz = hourOfDay;

                            if (minute < 10) {
                                txtTime.setText(+hourOfDay + ":0" + minute);
                            } else {
                                txtTime.setText(+hourOfDay + ":" + minute);
                            }
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
        if (v == oblButton) {
            boolean f = false;
            if (txtDate.getText().length() > 0 && txtTime.getText().length() > 0 && DlSt.getText().length() > 0
                    && DlMin.getText().length() > 0 && Dlsek.getText().length() > 0 && SzSt.getText().length() > 0
                    && SzMin.getText().length() > 0 && Szsek.getText().length() > 0 && wind.getText().length() > 0) {
                f = true;
            } else {
                Toast.makeText(this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            }
            if (f) {
                try {
                    if ((Integer.parseInt(String.valueOf(SzSt.getText())) < 90 && Integer.parseInt(String.valueOf(SzMin.getText())) < 60
                            && Double.parseDouble(String.valueOf(Szsek.getText())) < 60 && Integer.parseInt(String.valueOf(DlSt.getText())) < 180
                            && Integer.parseInt(String.valueOf(DlMin.getText())) < 60 && Double.parseDouble(String.valueOf(Dlsek.getText())) < 60)
                            || ((Integer.parseInt(String.valueOf(SzSt.getText())) == 90 && Integer.parseInt(String.valueOf(SzMin.getText())) == 0
                            && Double.parseDouble(String.valueOf(Szsek.getText())) == 0) && (Integer.parseInt(String.valueOf(DlSt.getText())) == 180
                            && Integer.parseInt(String.valueOf(DlMin.getText())) == 0 && Double.parseDouble(String.valueOf(Dlsek.getText())) == 0))) {

                        Date data = new Date();
                        data.setDate(dzien);
                        data.setMonth(mies);
                        data.setYear(rok);
                        int k = 1, l = 1;

                        if (spiSz.getSelectedItemPosition() > 0)
                            k = -1;
                        if (spiDl.getSelectedItemPosition() > 0)
                            l = -1;
                        wyniki.lat = formatCoor(k * Integer.parseInt(String.valueOf(SzSt.getText())),
                                Integer.parseInt(String.valueOf(SzMin.getText())), Double.parseDouble(String.valueOf(Szsek.getText())));
                        wyniki.lng = formatCoor(l * Integer.parseInt(String.valueOf(DlSt.getText())),
                                Integer.parseInt(String.valueOf(DlMin.getText())), Double.parseDouble(String.valueOf(Dlsek.getText())));

                        double Ip = a.intensProm(data, godz, min, wyniki.lat, wyniki.lng, Integer.parseInt(spiOkt.getSelectedItem().toString()));
                        double L = a.wyznL(data, godz, min, Ip, Double.parseDouble(String.valueOf(wind.getText())), wyniki.lat, wyniki.lng,
                                Integer.parseInt(spiOkt.getSelectedItem().toString()));
                        wyniki.cl = a.LtoClass(L);
                        if (wyniki.f) {
                            wyniki.kg2 = wyniki.kg1 * Math.pow(Double.parseDouble(String.valueOf(wind.getText())), 0.78);
                            wyniki.Q = Math.round(100 * wyniki.Qb * wyniki.kg2) / 100.0;
                        }
                        wyniki.xg = a.GausX(wyniki.Q, Double.parseDouble(String.valueOf(wind.getText())), miasto.isChecked(),
                                wyniki.cl, 1.0);
                        openPacActivity();
                    }
                } catch (NumberFormatException e) {
                    wind.getText().clear();
                    Szsek.getText().clear();
                    Dlsek.getText().clear();
                    Toast.makeText(getApplicationContext(), "Znaleniono nieprawidłowy format liczby!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, " Nieprawidłowy zakres współrzędnych geograficznych", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(stclass.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (stclass.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(stclass.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                //Test
                //lattitude="-53:08:22,35436";
                //longitude="-53:08:22,35436";
                lattitude = Location.convert(location.getLatitude(), Location.FORMAT_SECONDS);
                longitude = Location.convert(location.getLongitude(), Location.FORMAT_SECONDS);

                if (devCo(lattitude)[0].charAt(0) == '-') {
                    this.SzSt.setText(devCo(lattitude)[0].substring(1));
                    this.spiSz.setSelection(1);
                } else {
                    this.SzSt.setText(devCo(lattitude)[0]);
                    this.spiSz.setSelection(0);
                }

                this.SzMin.setText(devCo(lattitude)[1]);
                this.Szsek.setText(devCo(lattitude)[2].replace(',', '.'));

                if (devCo(longitude)[0].charAt(0) == '-') {
                    this.DlSt.setText(devCo(longitude)[0].substring(1));
                    this.spiDl.setSelection(1);
                } else {
                    this.DlSt.setText(devCo(longitude)[0]);
                    this.spiDl.setSelection(0);
                }

                this.DlMin.setText(devCo(longitude)[1]);
                this.Dlsek.setText(devCo(longitude)[2].replace(',', '.'));

            } else if (location1 != null) {
                lattitude = Location.convert(location1.getLatitude(), Location.FORMAT_SECONDS);
                longitude = Location.convert(location1.getLongitude(), Location.FORMAT_SECONDS);
                if (devCo(lattitude)[0].charAt(0) == '-') {
                    this.SzSt.setText(devCo(lattitude)[0].substring(1));
                    this.spiSz.setSelection(1);
                } else {
                    this.SzSt.setText(devCo(lattitude)[0]);
                    this.spiSz.setSelection(0);
                }

                this.SzMin.setText(devCo(lattitude)[1]);
                this.Szsek.setText(devCo(lattitude)[2].replace(',', '.'));

                if (devCo(longitude)[0].charAt(0) == '-') {
                    this.DlSt.setText(devCo(longitude)[0].substring(1));
                    this.spiDl.setSelection(1);
                } else {
                    this.DlSt.setText(devCo(longitude)[0]);
                    this.spiDl.setSelection(0);
                }

                this.DlMin.setText(devCo(longitude)[1]);
                this.Dlsek.setText(devCo(longitude)[2].replace(',', '.'));
            } else if (location2 != null) {
                lattitude = Location.convert(location2.getLatitude(), Location.FORMAT_SECONDS);
                longitude = Location.convert(location2.getLongitude(), Location.FORMAT_SECONDS);
                if (devCo(lattitude)[0].charAt(0) == '-') {
                    this.SzSt.setText(devCo(lattitude)[0].substring(1));
                    this.spiSz.setSelection(1);
                } else {
                    this.SzSt.setText(devCo(lattitude)[0]);
                    this.spiSz.setSelection(0);
                }

                this.SzMin.setText(devCo(lattitude)[1]);
                this.Szsek.setText(devCo(lattitude)[2].replace(',', '.'));

                if (devCo(longitude)[0].charAt(0) == '-') {
                    this.DlSt.setText(devCo(longitude)[0].substring(1));
                    this.spiDl.setSelection(1);
                } else {
                    this.DlSt.setText(devCo(longitude)[0]);
                    this.spiDl.setSelection(0);
                }

                this.DlMin.setText(devCo(longitude)[1]);
                this.Dlsek.setText(devCo(longitude)[2].replace(',', '.'));

            } else {
                Toast.makeText(this, "Nie można ustalić lokalizacji", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Włącz połączenie GPS")
                .setCancelable(false)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void openPacActivity() {
        Intent intent = new Intent(this, pacActivity.class);
        startActivity(intent);
    }

    public void openMapActivity() {
        Intent intent = new Intent(this, inMap.class);
        startActivity(intent);
    }

    public String[] devCo(String coor) {
        String dc[] = new String[3];
        int i = 0;
        for (int j = 0; j < 3; j++) {
            dc[j] = "";
        }
        for (char c : coor.toCharArray()) {
            if (i < 1 && c != ':') {
                dc[0] += c;
            } else if (i < 1 && c == ':') {
                i++;
            } else if (i < 2 && c != ':') {
                dc[1] += c;
            } else if (i < 2 && c == ':') {
                i++;
            } else {
                dc[2] += c;
            }
        }
        return dc;
    }

    public double formatCoor(int st, int min, double sek) {
        double d = ((min * 60) + sek) / 3600.00;
        if (st > 0)
            return st + d;
        else
            return st - d;
    }

    public void setFromMap(LatLng point) {
        if (point != null) {
            lattitude = Location.convert(point.latitude, Location.FORMAT_SECONDS);
            longitude = Location.convert(point.longitude, Location.FORMAT_SECONDS);

            if (devCo(lattitude)[0].charAt(0) == '-') {
                this.SzSt.setText(devCo(lattitude)[0].substring(1));
                this.spiSz.setSelection(1);
            } else {
                this.SzSt.setText(devCo(lattitude)[0]);
                this.spiSz.setSelection(0);
            }

            this.SzMin.setText(devCo(lattitude)[1]);
            this.Szsek.setText(devCo(lattitude)[2].replace(',', '.'));

            if (devCo(longitude)[0].charAt(0) == '-') {
                this.DlSt.setText(devCo(longitude)[0].substring(1));
                this.spiDl.setSelection(1);
            } else {
                this.DlSt.setText(devCo(longitude)[0]);
                this.spiDl.setSelection(0);
            }

            this.DlMin.setText(devCo(longitude)[1]);
            this.Dlsek.setText(devCo(longitude)[2].replace(',', '.'));
        }
    }

}
