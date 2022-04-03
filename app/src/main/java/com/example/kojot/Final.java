package com.example.kojot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Final extends AppCompatActivity {

    private TextView tv, tvh, p1, p2, p3;
    private Button backBtn, mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        tvh = (TextView) findViewById(R.id.textViewHead);
        tvh.setText("Wyniki:");

        tv = (TextView) findViewById(R.id.textView);
        tv.setText("Wydajność żródła wypływu: " + wyniki.Q + "[kg/s]\nKlasa stabilności atmosfery: " +
                wyniki.cl + "\nZasięgi strefy niebezpiecznej:");
        p1 = (TextView) findViewById(R.id.textP1);
        p1.setTextColor(Color.YELLOW);
        p2 = (TextView) findViewById(R.id.textP2);
        p2.setTextColor(Color.CYAN);
        p3 = (TextView) findViewById(R.id.textP3);
        p3.setTextColor(Color.RED);
        p1.setText("PAC1: " + wyniki.xg1 + " [m]");
        p2.setText("PAC2: " + wyniki.xg2 + " [m]");
        p3.setText("PAC3: " + wyniki.xg3 + " [m]");

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMainActivity();
            }
        });

        mapBtn = (Button) findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMapActivity();
            }
        });

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

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
