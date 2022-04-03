package com.example.kojot.factor.liquid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kojot.factor.constant.ContactField;
import com.example.kojot.factor.model.Liquid;

import java.util.ArrayList;
import java.util.List;

import static com.example.kojot.factor.constant.ContactField.LIQUID_TABLE;

public class SQLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Gas.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + LIQUID_TABLE + " (" +
                    ContactField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_M + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CP + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC1 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC2 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC3 + TEXT_TYPE + " )";

    private static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE " + ContactField.GAS_TABLE + " (" +
                    ContactField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_M + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CP + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_CV + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC1 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC2 + TEXT_TYPE + COMMA_SEP +
                    ContactField.PAC3 + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LIQUID_TABLE;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Liquid liquid) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, liquid.getName());
        values.put(ContactField.COLUMN_M, liquid.getM());
        values.put(ContactField.COLUMN_CP, liquid.getCp());
        values.put(ContactField.PAC1, liquid.getPac1());
        values.put(ContactField.PAC2, liquid.getPac2());
        values.put(ContactField.PAC3, liquid.getPac3());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                LIQUID_TABLE,
                null,
                values);
    }

    public Cursor retrieve() {
        String selectQuery = "SELECT  * FROM " + LIQUID_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    public void update(Liquid liquid) {
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, liquid.getName());
        values.put(ContactField.COLUMN_M, liquid.getM());
        values.put(ContactField.COLUMN_CP, liquid.getCp());
        values.put(ContactField.PAC1, liquid.getPac1());
        values.put(ContactField.PAC2, liquid.getPac2());
        values.put(ContactField.PAC3, liquid.getPac3());

        // Which row to update, based on the ID
        String selection = ContactField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(liquid.getId())};

        int count = db.update(
                LIQUID_TABLE,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id) {
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = ContactField.COLUMN_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        db.delete(LIQUID_TABLE, selection, selectionArgs);
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + LIQUID_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1)+" "+cursor.getString(2)+" [g/mol]");
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}
