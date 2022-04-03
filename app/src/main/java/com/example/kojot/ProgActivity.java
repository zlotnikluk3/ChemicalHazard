package com.example.kojot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kojot.factor.gas.GasChoActivity;
import com.example.kojot.factor.liquid.LiqChoActivity;

public class ProgActivity extends AppCompatActivity {

    private Button gcButton, lpButton, lbButton;

    private static int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog);
        flag = 0;
        wyniki.p1 = 0;
        wyniki.p2 = 0;
        wyniki.p3 = 0;
        wyniki.m = 0;

        gcButton = (Button) findViewById(R.id.gcButton);
        gcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wyniki.f = false;
                openGasChoActivity();
            }
        });

        lpButton = (Button) findViewById(R.id.lpButton);
        lpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wyniki.f = true;
                flag = 1;
                openLiqPoolActivity();
            }
        });

        lbButton = (Button) findViewById(R.id.lbButton);
        lbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wyniki.f = false;
                flag = 2;
                openLiqBoilActivity();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openGasChoActivity() {
        Intent intent = new Intent(this, GasChoActivity.class);
        startActivity(intent);
    }

    public void openLiqPoolActivity() {
        Intent intent = new Intent(this, LiqChoActivity.class);
        startActivity(intent);
    }

    public void openLiqBoilActivity() {
        Intent intent = new Intent(this, LiqChoActivity.class);
        startActivity(intent);
    }

    public static int getFlag() {
        return flag;
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
