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

import retrofit2.Call;

/**
 *
 */

public class SubCategory extends BaseDataModel {

    public static final String TABLE_NAME = "TableSubCategory";


    public static final String CATEGORY_ID = "CategoryID";
    public static final String SUB_CATEGORY_ID = "SubcategoryID";
    public static final String NAME = "Name";
    public static final String IMAGE = "Image";
    public static final String STATUS = "Status";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_ID + " VARCHAR(5) , "
            + SUB_CATEGORY_ID + " VARCHAR(5) , "
            + NAME + " VARCHAR(250) , "
            + IMAGE + " TEXT , "
            + STATUS + " VARCHAR(5) )";


    public String categoryID;
    public String subcategoryID;
    public String name;
    public String image;
    public String status;

    public SubCategory() {
    }

    public SubCategory(String categoryID, String id, String name, String image, String status ) {
        this.categoryID = categoryID;
        this.subcategoryID = id;
        this.name = name;
        this.image = image;
        this.status = status;
    }


    public static ArrayList<SubCategory> getAllCategories(Context context) {
        DatabasesHelper helper = new DatabasesHelper( context);
        ArrayList<SubCategory> areas = getAllObjects(helper);
        helper.close();
        return areas;
    }


    //DB Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static void insertObject(DatabasesHelper db, SubCategory object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_ID, object.categoryID);
        cv.put(SUB_CATEGORY_ID, object.subcategoryID);
        cv.put(NAME, object.name);
        cv.put(IMAGE, object.image);
        cv.put(STATUS, object.status);
        long result = sqld.insert(TABLE_NAME, null,cv);
        object.id = result;
        sqld.close();
    }

    public static void insertOrUpdateWrtObjectID(DatabasesHelper db, SubCategory object) {
        if( object == null) return;
        SubCategory temp = getAnObjectWithObjectID(db,object.subcategoryID);
        if( temp == null ) {
            insertObject(db, object);
        } else {
            updateObjectWithObjectID(db, object);
        }
    }

    public static void updateObjectWithObjectID(DatabasesHelper db, SubCategory object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_ID, object.categoryID);
        cv.put(SUB_CATEGORY_ID, object.subcategoryID);
        cv.put(NAME, object.name);
        cv.put(IMAGE, object.image);
        cv.put(STATUS, object.status);
        sqld.update(TABLE_NAME, cv, SUB_CATEGORY_ID + "='" +object.subcategoryID + "'", null);
        sqld.close();
    }

    public static void insertObjects(DatabasesHelper db, ArrayList<SubCategory> objects) {
        if( objects == null) return;
        for( SubCategory object : objects) {
            if( null == object ) continue;
            insertObject(db, object);
        }
    }

    public static SubCategory getAnObjectFromCursor(Cursor c ) {
        SubCategory instance = null;
        if( c != null) {
            instance = new SubCategory();
            instance.id = c.getInt(c.getColumnIndex(ID));
            instance.categoryID = c.getString(c.getColumnIndex(CATEGORY_ID));
            instance.subcategoryID = c.getString(c.getColumnIndex(SUB_CATEGORY_ID));
            instance.name = c.getString(c.getColumnIndex(NAME));
            instance.image = c.getString(c.getColumnIndex(IMAGE));
            instance.status = c.getString(c.getColumnIndex(STATUS));

        } else {
        }
        return instance;
    }

    public static ArrayList<SubCategory> getAllObjects(DatabasesHelper db) {
        ArrayList<SubCategory> objects = new ArrayList<SubCategory>();
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

    public static SubCategory getAnObjectWithObjectID(DatabasesHelper db, String objectID) {
        SubCategory object = null;
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE "
                + SUB_CATEGORY_ID + " = '" + objectID + "'";
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
