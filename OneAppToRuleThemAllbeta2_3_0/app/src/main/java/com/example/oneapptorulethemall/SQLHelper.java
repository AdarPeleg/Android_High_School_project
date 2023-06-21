package com.example.oneapptorulethemall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="lib.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME="users";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_EMAIL="email";
    public static final String COLUMN_AGE="age";
    public static final String COLUMN_PASSWORD="password";
    public static final String COLUMN_PERMISSION="permission";


    private final String CREATE_TABLE="CREATE TABLE " +TABLE_NAME+ " ( "
            + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_PERMISSION + " TEXT, "
            + COLUMN_AGE + " INTEGER );";


    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertData(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues(); //hash map
        values.put(COLUMN_NAME,user.getName());
        values.put(COLUMN_EMAIL,user.getEmail());
        values.put(COLUMN_AGE,user.getAge());
        values.put(COLUMN_PASSWORD,user.getPassword());
        values.put(COLUMN_PERMISSION,user.getPermission());
        sqLiteDatabase.insert(TABLE_NAME,null,values); //nullcolumnhack default value

    }

    public Cursor getCursor(){
        Cursor cursor = getWritableDatabase().query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID+ " DESC "
        );
        return cursor;
    }

    public boolean doesUsernameExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_NAME + " = '" + name + "';";
        Cursor userCursor = db.rawQuery(query , null); // applying the query
        boolean ans = userCursor.getCount() > 0;
        userCursor.close();
        return ans;
    }



    public void setProfile(User profile,String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + username + "';";
        Cursor userCursor = db.rawQuery(query , null);
        userCursor.moveToFirst();
        profile.setName(userCursor.getString(1));
        profile.setEmail(userCursor.getString(2));
        profile.setPassword(userCursor.getString(3));
        profile.setPermission(userCursor.getString(4));
        profile.setAge(userCursor.getInt(5));
        userCursor.close();
    }

    public boolean updatePer(String username, String permission){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + username + "';";
        Cursor userCursor = db.rawQuery(query , null);
        userCursor.moveToFirst();


        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, username);
        cv.put(COLUMN_EMAIL,userCursor.getString(2));
        cv.put(COLUMN_PASSWORD,userCursor.getString(3) );
        cv.put(COLUMN_PERMISSION, permission);
        cv.put(COLUMN_AGE, userCursor.getString(5));

        db.update(TABLE_NAME, cv, "name=?", new String[]{username});
        return true;
    }


}
