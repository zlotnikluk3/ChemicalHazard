package com.example.kojot.factor.gas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kojot.factor.constant.ContactField;
import com.example.kojot.factor.model.Gas;

import java.util.ArrayList;
import java.util.List;

import static com.example.kojot.factor.constant.ContactField.GAS_TABLE;

public class SQLiteDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Gas.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE " + GAS_TABLE + " (" +
                    ContactField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_M + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CP + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CV + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC1 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC2 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC3 + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + ContactField.LIQUID_TABLE + " (" +
                    ContactField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_M + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CP + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC1 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC2 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC3 + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GAS_TABLE;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES1);
        db.execSQL(SQL_CREATE_ENTRIES2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Gas gas) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, gas.getName());
        values.put(ContactField.COLUMN_M, gas.getM());
        values.put(ContactField.COLUMN_CP, gas.getCp());
        values.put(ContactField.COLUMN_CV, gas.getCv());
        values.put(ContactField.PAC1, gas.getPac1());
        values.put(ContactField.PAC2, gas.getPac2());
        values.put(ContactField.PAC3, gas.getPac3());

        long newRowId;
        newRowId = db.insert(
                GAS_TABLE,
                null,
                values);
    }

    public Cursor retrieve() {
        String selectQuery = "SELECT  * FROM " + GAS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    public void update(Gas gas) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, gas.getName());
        values.put(ContactField.COLUMN_M, gas.getM());
        values.put(ContactField.COLUMN_CP, gas.getCp());
        values.put(ContactField.COLUMN_CV, gas.getCv());
        values.put(ContactField.PAC1, gas.getPac1());
        values.put(ContactField.PAC2, gas.getPac2());
        values.put(ContactField.PAC3, gas.getPac3());

        String selection = ContactField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(gas.getId())};

        int count = db.update(
                GAS_TABLE,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = ContactField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(GAS_TABLE, selection, selectionArgs);
    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + GAS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1) + " " + cursor.getString(2) + " [g/mol]");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }
}
