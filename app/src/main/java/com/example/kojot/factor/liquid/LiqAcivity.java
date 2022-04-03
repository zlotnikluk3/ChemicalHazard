package com.example.kojot.factor.liquid;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kojot.R;
import com.example.kojot.factor.liquid.adapter.ContactListAdapter;
import com.example.kojot.factor.liquid.db.SQLiteDB;
import com.example.kojot.factor.listener.RecyclerItemClickListener;
import com.example.kojot.factor.model.Liquid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LiqAcivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvContact;
    private FloatingActionButton btnAdd;

    private ContactListAdapter contactListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liq);

        lvContact = (RecyclerView) findViewById(R.id.lvContact);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);

        linearLayoutManager = new LinearLayoutManager(this);
        contactListAdapter = new ContactListAdapter(this);
        contactListAdapter.setOnItemClickListener(this);

        lvContact.setLayoutManager(linearLayoutManager);
        lvContact.setAdapter(contactListAdapter);

        loadData();
        if (sqLiteDB.getAllLabels().isEmpty()) {
            addONstart();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(LiqAcivity.this);
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData() {
        sqLiteDB = new SQLiteDB(this);

        List<Liquid> liquidList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Liquid liquid;

        if (cursor.moveToFirst()) {
            do {

                liquid = new Liquid();

                liquid.setId(cursor.getInt(0));
                liquid.setName(cursor.getString(1));
                liquid.setM(cursor.getString(2));
                liquid.setCp(cursor.getString(3));
                liquid.setPac1(cursor.getString(4));
                liquid.setPac2(cursor.getString(5));
                liquid.setPac3(cursor.getString(6));

                liquidList.add(liquid);
            } while (cursor.moveToNext());
        }

        contactListAdapter.clear();
        contactListAdapter.addAll(liquidList);
    }

    public void addONstart() {
        Liquid liq1 = new Liquid();
        liq1.setName("N2H4");
        liq1.setM(Double.toString(31.05));
        liq1.setCp(Double.toString(56.53));
        liq1.setPac1(Double.toString(15));
        liq1.setPac2(Double.toString(64));
        liq1.setPac3(Double.toString(350));
        Liquid liq2 = new Liquid();
        liq2.setName("NH3");
        liq2.setM(Double.toString(17.03));
        liq2.setCp(Double.toString(36.84));
        liq2.setPac1(Double.toString(30.0));
        liq2.setPac2(Double.toString(160.0));
        liq2.setPac3(Double.toString(1100.0));
        sqLiteDB.create(liq1);
        sqLiteDB.create(liq2);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(this, contactListAdapter.getItem(position));
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

