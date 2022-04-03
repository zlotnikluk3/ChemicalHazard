package com.example.kojot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kojot.factor.gas.GasActivity;
import com.example.kojot.factor.liquid.LiqAcivity;

public class MainActivity extends AppCompatActivity {

    private Button GasButton, LiqButton, insButton, pButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pButton = (Button) findViewById(R.id.prog);
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgActivity();
            }
        });
        GasButton = (Button) findViewById(R.id.gasAct);
        GasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGasActivity();
            }
        });
        LiqButton = (Button) findViewById(R.id.liqAct);
        LiqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLiqActivity();
            }
        });

        insButton = (Button) findViewById(R.id.insButton);
        insButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsActivity();
            }
        });
    }

    public void openProgActivity() {
        Intent intent = new Intent(this, ProgActivity.class);
        startActivity(intent);
    }

    public void openGasActivity() {
        Intent intent = new Intent(this, GasActivity.class);
        startActivity(intent);
    }

    public void openLiqActivity() {
        Intent intent = new Intent(this, LiqAcivity.class);
        startActivity(intent);
    }

    public void openInsActivity() {
        Intent intent = new Intent(this, Instrukcja.class);
        startActivity(intent);
    }
}