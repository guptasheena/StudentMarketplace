package com.cmpe277.studentmarketplace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "market_db";
    private static final String TABLE_Name = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL= "Email";
    private static final String KEY_PWD= "Password";
    public Database(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Name + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT,"
                + KEY_PWD+ " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Name);
        onCreate(db);
    }

    // Adding new User Details
    public DbResult insertNewUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+KEY_PWD+" FROM "+ TABLE_Name+" where "+KEY_EMAIL+" = '"+email+"';";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() == 0) {
            //Get the Data Repository in write mode
            db = this.getWritableDatabase();
            //Create a new map of values, where column names are the keys
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_EMAIL, email);
            cValues.put(KEY_PWD, password);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(TABLE_Name, null, cValues);
            return new DbResult("SignUp Success",true);
        }
        else{
            return new DbResult("User already exists",false);
        }
    }

    //Verify user
    public DbResult validUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+KEY_PWD+" FROM "+ TABLE_Name+" where "+KEY_EMAIL+" = '"+email+"';";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            if(password.equals(cursor.getString(cursor.getColumnIndex(KEY_PWD)))){
                return new DbResult("Login success",true);
            }
            else{
                return new DbResult("Authentication Failed.Incorrect Password",false);
            }
        }
        else{
            return new DbResult("Authentication Failed.User not found",false);
        }
    }
    // Delete a user
    public void RemoveUser(int recipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Name, KEY_ID+" = ?",new String[]{String.valueOf(recipe_id)});
        db.close();
    }
}