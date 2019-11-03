package com.cmpe277.studentmarketplace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String TAG = "Database";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "market_db";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser = "CREATE TABLE User(id INTEGER PRIMARY KEY AUTOINCREMENT, email  TEXT, password TEXT, first_name TEXT, last_name TEXT, phone TEXT, address TEXT);";
        String createItem = "CREATE TABLE Item(id INTEGER PRIMARY KEY AUTOINCREMENT, name  TEXT, category TEXT, description TEXT, price REAL, posted_date DATETIME DEFAULT CURRENT_TIMESTAMP, posted_by TEXT, FOREIGN KEY (posted_by) REFERENCES User (email))";
        String itemImages = "CREATE TABLE ItemImages(id INTEGER PRIMARY KEY AUTOINCREMENT, itemId INTEGER, image BLOB, FOREIGN KEY (itemId) REFERENCES Item (id))";
        String purchaseInfo = "CREATE TABLE PurchaseInfo(id INTEGER PRIMARY KEY AUTOINCREMENT, itemId INTEGER, purchased_by TEXT, purchased_date DATETIME DEFAULT CURRENT_TIMESTAMP , FOREIGN KEY (purchased_by) REFERENCES User (email))";
        db.execSQL(createUser);
        db.execSQL(createItem);
        db.execSQL(itemImages);
        db.execSQL(purchaseInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Item");
        db.execSQL("DROP TABLE IF EXISTS ItemImages");
        db.execSQL("DROP TABLE IF EXISTS PurchaseInfo");
        onCreate(db);
    }

    // Adding new User Details
    public DbResult insertNewUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT password FROM User where email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            //Get the Data Repository in write mode
            db = this.getWritableDatabase();
            //Create a new map of values, where column names are the keys
            ContentValues cValues = new ContentValues();
            cValues.put("email", email);
            cValues.put("password", password);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert("User", null, cValues);
            return new DbResult("SignUp Success", true);
        } else {
            return new DbResult("User already exists", false);
        }
    }

    //Verify user
    public DbResult validUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT password FROM User where email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            if (password.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                return new DbResult("Login success", true);
            } else {
                return new DbResult("Authentication Failed.Incorrect Password", false);
            }
        } else {
            return new DbResult("Authentication Failed.User not found", false);
        }
    }

    // Delete a user
    public void RemoveUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", "email = ?", new String[]{email});
        db.close();
    }

    // Get All HomePosts
    public ArrayList<Post> GetAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Item where id not in(SELECT itemId from PurchaseInfo);";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Post> postsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String desc = cursor.getString(cursor.getColumnIndex("description"));
            String owner = cursor.getString(cursor.getColumnIndex("posted_by"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));

            Post p = new Post(itemId, name, desc, owner, category, price, getAllImagesByItemId(itemId));
            postsList.add(p);
        }
        return postsList;
    }

    // Get Posts by Name
    public ArrayList<Post> GetPostsByName(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Item where id not in(SELECT itemId from PurchaseInfo) and name = \"" + itemName + "\"";
        Log.d(TAG, "GetPostsByName: " + query);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Post> postsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String desc = cursor.getString(cursor.getColumnIndex("description"));
            String owner = cursor.getString(cursor.getColumnIndex("posted_by"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));

            Post p = new Post(itemId, name, desc, owner, category, price, getAllImagesByItemId(itemId));
            postsList.add(p);
        }
        return postsList;
    }

    public ArrayList<Bitmap> getAllImagesByItemId(int itemId) {
        ArrayList<Bitmap> b = new ArrayList<Bitmap>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ItemImages where itemId = '" + itemId + "';";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            b.add(ImageConversions.getBitmapFromBytes(cursor.getBlob(cursor.getColumnIndex("image"))));
        }
        return b;
    }

    public ArrayList<Post> GetPostedPosts() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> postsList = new ArrayList<>();
        Post p = new Post(3, "Post3", "This is post desc3", "User3@sjsu.com", "vehicle", 0.0, null);
        postsList.add(p);
        p = new Post(4, "Post4", "This is post desc4", "User4@sjsu.com", "vehicle", 0.0, null);
        postsList.add(p);
        return postsList;
    }

    public ArrayList<Post> GetPurchasedPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> postsList = new ArrayList<>();
        Post p = new Post(5, "Post5", "This is post desc5", "User5@sjsu.edu", "furniture", 0.0, null);
        postsList.add(p);
        p = new Post(6, "Post6", "This is post desc6", "User6@sjsu.com", "furniture", 0.0, null);
        postsList.add(p);
        p = new Post(7, "Post7", "This is post desc7", "User7@sjsu.com", "furniture", 0.0, null);
        postsList.add(p);
        p = new Post(8, "Post8", "This is post desc8", "User8@sjsu.com", "book", 0.0, null);
        postsList.add(p);
        return postsList;
    }

    public DbResult createPurchaseRecord(int item_id, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT password FROM User where email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            cValues.put("itemId", item_id);
            cValues.put("purchased_by", email);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert("PurchaseInfo", null, cValues);
            return new DbResult("Item Marked as Sold", false);
        } else {
            return new DbResult("Email doesn't exist", false);
        }
    }

    public DbResult createNewPost(Post p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("name", p.getName());
        cValues.put("category", p.getCategory());
        cValues.put("description", p.getDescription());
        cValues.put("posted_by", p.getOwnerEmail());
        cValues.put("price", p.getPrice());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("Item", null, cValues);
        //insert images in any
        ArrayList<Bitmap> allImages = p.getAllImages();
        if (allImages != null) {
            for (int i = 0; i < allImages.size(); i++) {
                ContentValues cValues1 = new ContentValues();
                cValues1.put("itemId", newRowId);
                cValues1.put("image", ImageConversions.getBytesFromBitmap(allImages.get(i)));
                db.insert("ItemImages", null, cValues1);
            }
        }

        return new DbResult("New post added", false);
    }
}