package com.example.kojot.factor.gas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kojot.R;
import com.example.kojot.factor.gas.db.SQLiteDB;
import com.example.kojot.factor.model.Gas;

public class ActActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText factorName;
    private EditText M;
    private EditText cp;
    private EditText cv;
    private EditText pac1, pac2, pac3;

    private Button btnAdd, btnEdit, btnDelete;

    private SQLiteDB sqLiteDB;
    private Gas gas;

    public static void start(Context context) {
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Gas gas) {
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), gas);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_gas);

        factorName = (EditText) findViewById(R.id.factorText);
        M = (EditText) findViewById(R.id.mText);
        cp = (EditText) findViewById(R.id.cpText);
        cv = (EditText) findViewById(R.id.cvText);
        pac1 = (EditText) findViewById(R.id.pac1Text);
        pac2 = (EditText) findViewById(R.id.pac2Text);
        pac3 = (EditText) findViewById(R.id.pac3Text);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        gas = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());

        if (gas != null) {
            btnAdd.setVisibility(View.GONE);

            factorName.setText(gas.getName());
            M.setText(gas.getM());
            cp.setText(gas.getCp());
            cv.setText(gas.getCv());
            pac1.setText(gas.getPac1());
            pac2.setText(gas.getPac2());
            pac3.setText(gas.getPac3());
        } else {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        sqLiteDB = new SQLiteDB(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            if (factorName.getText().length() > 0 && M.getText().length() > 0 && (M.getText().charAt(0) != '.') && cp.getText().length() > 0
                    && (cp.getText().charAt(0) != '.') && cv.getText().length() > 0 && (cv.getText().charAt(0) != '.') && (pac1.getText().charAt(0) != '.')
                    && (pac2.getText().charAt(0) != '.') && (pac3.getText().charAt(0) != '.')) {
                if (Double.parseDouble(String.valueOf(cp.getText())) > Double.parseDouble(String.valueOf(cv.getText()))) {
                    gas = new Gas();
                    gas.setName(factorName.getText().toString());
                    gas.setM(M.getText().toString());
                    gas.setCp(cp.getText().toString());
                    gas.setCv(cv.getText().toString());
                    gas.setPac1(pac1.getText().toString());
                    gas.setPac2(pac2.getText().toString());
                    gas.setPac3(pac3.getText().toString());
                    sqLiteDB.create(gas);
                    Toast.makeText(this, "Dodano!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Cp musi być większe od Cv", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Uzupełnij wymagane pola", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnEdit) {
            if (factorName.getText().length() > 0 && M.getText().length() > 0 && (M.getText().charAt(0) != '.') && cp.getText().length() > 0
                    && (cp.getText().charAt(0) != '.') && cv.getText().length() > 0 && (cv.getText().charAt(0) != '.') && (pac1.getText().charAt(0) != '.')
                    && (pac2.getText().charAt(0) != '.') && (pac3.getText().charAt(0) != '.')) {
                if (Double.parseDouble(String.valueOf(cp.getText())) > Double.parseDouble(String.valueOf(cv.getText()))) {
                    gas.setName(factorName.getText().toString());
                    gas.setM(M.getText().toString());
                    gas.setCp(cp.getText().toString());
                    gas.setCv(cv.getText().toString());
                    gas.setPac1(pac1.getText().toString());
                    gas.setPac2(pac2.getText().toString());
                    gas.setPac3(pac3.getText().toString());
                    sqLiteDB.update(gas);
                    Toast.makeText(this, "Zedytowano!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Cp musi być większe od Cv", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Uzupełnij wymagane pola", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnDelete) {
            sqLiteDB.delete(gas.getId());

            Toast.makeText(this, "Usunięto!", Toast.LENGTH_SHORT).show();
            finish();
        }
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
