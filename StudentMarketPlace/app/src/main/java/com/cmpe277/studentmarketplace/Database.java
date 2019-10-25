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
    public void RemoveUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Name, KEY_EMAIL+" = ?",new String[]{email});
        db.close();
    }

    // Get All HomePosts
    public ArrayList<Post> GetAllPosts(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> postsList = new ArrayList<>();
        /*
        String query = "SELECT * FROM "+ TABLE_Name+";";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){

            ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex(KEY_PIC));
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            Post p = new Post(cursor.getString(cursor.getColumnIndex(KEY_NAME)),cursor.getString(cursor.getColumnIndex(KEY_DESC)),theImage));
            postsList.add(p);
        }*/
        Post p = new Post("Post1","This is post desc1","User1@sjsu.com",null);
        postsList.add(p);
        p = new Post("Post2","This is post desc2","User2@sjsu.com",null);
        postsList.add(p);
        return  postsList;
    }

    public ArrayList<Post> GetPostedPosts(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> postsList = new ArrayList<>();
        Post p = new Post("Post3","This is post desc3","User3@sjsu.com",null);
        postsList.add(p);
        p = new Post("Post4","This is post desc4","User4@sjsu.com",null);
        postsList.add(p);
        return  postsList;
    }

    public ArrayList<Post> GetPurchasedPosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> postsList = new ArrayList<>();
        Post p = new Post("Post5","This is post desc5","User5@sjsu.com",null);
        postsList.add(p);
        p = new Post("Post6","This is post desc6","User6@sjsu.com",null);
        postsList.add(p);
        p = new Post("Post7","This is post desc7","User7@sjsu.com",null);
        postsList.add(p);
        p = new Post("Post8","This is post desc8","User8@sjsu.com",null);
        postsList.add(p);
        return  postsList;
    }
}