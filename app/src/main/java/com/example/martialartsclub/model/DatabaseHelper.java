package com.example.martialartsclub.model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "martialArtsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MartialArts";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String PRICE_KEY = "price";
    private static final String COLOR_KEY = "color";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDatabaseSQL = "create table " + TABLE_NAME +
                " (" + ID_KEY + " integer primary key autoincrement,"
                + NAME_KEY +" text," + PRICE_KEY + " real," + COLOR_KEY + " text);";
        sqLiteDatabase.execSQL(createDatabaseSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

//    Method to add objects to the database
    public void addMartialArt(MartialArt martialArtObject){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Command to add the data
        String addMartialArtObjectCommand = "insert into " + TABLE_NAME + " values(null, '"
                +martialArtObject.getMartialArtName() + "', '" + martialArtObject.getMartialArtPrice()
                + "', '" + martialArtObject.getMartialArtColor() + "');";

        sqLiteDatabase.execSQL(addMartialArtObjectCommand);
        sqLiteDatabase.close();
    }

//    Method to delete data from the database using the ID
    public void deleteMartialArtObjectFromDatabaseById(int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Command to delete the object
        String deleteMartialArtObjectCommand = "delete from " + TABLE_NAME + " where " + ID_KEY
                + " = " + id + ";";
        sqLiteDatabase.execSQL(deleteMartialArtObjectCommand);
        sqLiteDatabase.close();
    }

//    Method to update the data in the database using the ID
    public void modifyMartialArtObject(int martialArtId, String martialArtName, double martialArtPrice, String martialArtColor){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Command to update the data in the database
        String modifyMartialArtObjectCommand = "update " + TABLE_NAME + " set " + NAME_KEY + " = '"
                + martialArtName + "', " + PRICE_KEY + " = '" + martialArtPrice + "', "
                + COLOR_KEY + " = '" + martialArtColor + "' where " + ID_KEY + " = " + martialArtId + ";";

        sqLiteDatabase.execSQL(modifyMartialArtObjectCommand);
        sqLiteDatabase.close();
    }

//    Method to return all the objects present in the database
    public ArrayList<MartialArt> returnAllMartialArtObjects(){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Command which returns all the data
        String sqlQueryCommand = "select * from " + TABLE_NAME + ";";

        // Cursor is created in order to process and store the data which is returned after executing the command
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQueryCommand, null);
        ArrayList<MartialArt> martialArts = new ArrayList<>();

        while(cursor.moveToNext()){
            MartialArt currentMartialArt = new MartialArt(cursor.getInt(0),
                    cursor.getString(1), cursor.getDouble(2), cursor.getString(3));

            martialArts.add(currentMartialArt);
        }
        sqLiteDatabase.close();
        return martialArts;
    }

//    Method to return the object by ID
    public MartialArt returnMartialArtObjectById(int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Command which returns data using a specific ID
        String getMartialArtObjectByIdCommand = "select * from " + TABLE_NAME + " where " + ID_KEY
                + " = " + id + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(getMartialArtObjectByIdCommand, null);
        MartialArt martialArt = null;

        if(cursor.moveToFirst()){
            martialArt = new MartialArt(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2),
                    cursor.getString(3));
        }
        return martialArt;
    }
}
