package com.example.kojot.factor.gas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.kojot.R;
import com.example.kojot.factor.gas.adapter.ContactListAdapter;
import com.example.kojot.factor.gas.db.SQLiteDB;
import com.example.kojot.factor.listener.RecyclerItemClickListener;
import com.example.kojot.factor.model.Gas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GasActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvContact;
    private FloatingActionButton btnAdd;

    private ContactListAdapter contactListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);

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
                ActActivity.start(GasActivity.this);
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

        List<Gas> gasList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Gas gas;

        if (cursor.moveToFirst()) {
            do {

                gas = new Gas();

                gas.setId(cursor.getInt(0));
                gas.setName(cursor.getString(1));
                gas.setM(cursor.getString(2));
                gas.setCp(cursor.getString(3));
                gas.setCv(cursor.getString(4));
                gas.setPac1(cursor.getString(5));
                gas.setPac2(cursor.getString(6));
                gas.setPac3(cursor.getString(7));

                gasList.add(gas);
            } while (cursor.moveToNext());
        }

        contactListAdapter.clear();
        contactListAdapter.addAll(gasList);
    }

    public void addONstart() {
        Gas gas = new Gas();
        gas.setName("NH3");
        gas.setM(Double.toString(17.03));
        gas.setCp(Double.toString(36.84));
        gas.setCv(Double.toString(27.84));
        gas.setPac1(Double.toString(30.0));
        gas.setPac2(Double.toString(160.0));
        gas.setPac3(Double.toString(1100.0));
        sqLiteDB.create(gas);
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

