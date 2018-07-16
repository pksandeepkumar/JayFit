package com.feathernet.jayfit.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feathernet.jayfit.database.DatabasesHelper;
import com.feathernet.jayfit.rest.ServiceGenerator;
import com.feathernet.jayfit.rest.VincityAPI;
import com.feathernet.jayfit.rest.pojos.category.CategoryPOJO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 *
 */

public class Category extends BaseDataModel {

    public static final String TABLE_NAME = "TableCategory";


    public static final String CATEGORY_ID = "CategoryID";
    public static final String NAME = "Name";
    public static final String STATUS = "Status";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_ID + " VARCHAR(5) , "
            + NAME + " VARCHAR(250) , "
            + STATUS + " VARCHAR(5) )";


    public String categoryID;
    public String name;
    public String status;

    public Category() {
    }

    public Category( String id, String name, String status ) {
        this.categoryID = id;
        this.name = name;
        this.status = status;
    }


    public static ArrayList<Category> getAllCategories(Context context) {
        DatabasesHelper helper = new DatabasesHelper( context);
        ArrayList<Category> areas = getAllObjects(helper);
        helper.close();
        return areas;
    }


    //DB Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static void insertObject(DatabasesHelper db, Category object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_ID, object.categoryID);
        cv.put(NAME, object.name);
        cv.put(STATUS, object.status);
        long result = sqld.insert(TABLE_NAME, null,cv);
        object.id = result;
        sqld.close();
    }

    public static void insertOrUpdateWrtObjectID(DatabasesHelper db, Category object) {
        if( object == null) return;
        Category temp = getAnObjectWithObjectID(db,object.categoryID);
        if( temp == null ) {
            insertObject(db, object);
        } else {
            updateObjectWithObjectID(db, object);
        }
    }

    public static void updateObjectWithObjectID(DatabasesHelper db, Category object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_ID, object.categoryID);
        cv.put(NAME, object.name);
        cv.put(STATUS, object.status);
        sqld.update(TABLE_NAME, cv, CATEGORY_ID + "='" +object.categoryID + "'", null);
        sqld.close();
    }

    public static void insertObjects(DatabasesHelper db, ArrayList<Category> objects) {
        if( objects == null) return;
        for( Category object : objects) {
            if( null == object ) continue;
            insertObject(db, object);
        }
    }

    public static Category getAnObjectFromCursor(Cursor c ) {
        Category instance = null;
        if( c != null) {
            instance = new Category();
            instance.id = c.getInt(c.getColumnIndex(ID));
            instance.categoryID = c.getString(c.getColumnIndex(CATEGORY_ID));
            instance.name = c.getString(c.getColumnIndex(NAME));
            instance.status = c.getString(c.getColumnIndex(STATUS));

        } else {
        }
        return instance;
    }

    public static ArrayList<Category> getAllObjects(DatabasesHelper db) {
        ArrayList<Category> objects = new ArrayList<Category>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME ;
        Cursor c = dbRead.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                objects.add(getAnObjectFromCursor(c));
            } while ( c.moveToNext()) ;
        }
        c.close();
        dbRead.close();
        return objects;
    }

    public static Category getAnObjectWithObjectID(DatabasesHelper db, String objectID) {
        Category object = null;
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE "
                + CATEGORY_ID + " = '" + objectID + "'";
        Cursor c = dbRead.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                object = getAnObjectFromCursor(c);
            } while ( c.moveToNext()) ;
        }
        c.close();
        dbRead.close();
        return object;
    }

    public static boolean deleteTable(DatabasesHelper db) {
        try {
            SQLiteDatabase sql = db.getWritableDatabase();
            String query = "DELETE from " + TABLE_NAME;
            sql.execSQL(query);
            sql.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteTableARow(DatabasesHelper db, int id) {
        try {
            SQLiteDatabase sql = db.getWritableDatabase();
            String query = "DELETE from " + TABLE_NAME + "WHERE " + ID + " = " + id;
            sql.execSQL(query);
            sql.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //API Calls>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public Call<CategoryPOJO> getCategory() {
        VincityAPI serviceApi =  ServiceGenerator.createService(VincityAPI.class);
        Call<CategoryPOJO> call = serviceApi.category();
        return call;
    }


}
