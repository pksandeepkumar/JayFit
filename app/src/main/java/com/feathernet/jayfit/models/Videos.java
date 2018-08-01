package com.feathernet.jayfit.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feathernet.jayfit.database.DatabasesHelper;
import com.feathernet.jayfit.rest.ServiceGenerator;
import com.feathernet.jayfit.rest.VincityAPI;
import com.feathernet.jayfit.rest.pojos.category.CategoryPOJO;
import com.feathernet.jayfit.rest.pojos.videosall.Video;

import java.util.ArrayList;

import retrofit2.Call;

/**
 *
 */

public class Videos extends BaseDataModel {

    public static final String TABLE_NAME = "TableVideos";

//    "id": "16",
//            "name": "film",
//            "category": "4",
//            "subcategory": "9",
//            "description": "",
//            "file": "20180606113328bdbd6db1d8cc09a957e86180fda9442a.pdf",
//            "date": "2018-06-14",
//            "image": "20180705173136thumbbdbd6db1d8cc09a957e86180fda9442a.jpg",
//            "video": "https://vimeo.com/243128189",
//            "type": "paid",
//            "status": "1",
//            "filepath": "http://www.atmunnar.com/jfconverter/uploads/pdf/20180606113328bdbd6db1d8cc09a957e86180fda9442a.pdf",
//            "imagepath": "http://www.atmunnar.com/jfconverter/uploads/images/20180705173136thumbbdbd6db1d8cc09a957e86180fda9442a.jpg"


    public static final String CATEGORY_ID = "CategoryID";
    public static final String SUB_CATEGORY_ID = "SubcategoryID";
    public static final String VIDEO_ID = "VideoID";
    public static final String NAME = "Name";
    public static final String VIDEO = "video";
    public static final String TYPE = "type";
    public static final String STATUS = "Status";
    public static final String PDF_BROCHURE = "PdfBrochure";
    public static final String IMAGE_PATH = "ImagePath";
    public static final String CURRENT_POSITION = "CurrentPosition";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE  " + TABLE_NAME
            + " ( " + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_ID + " VARCHAR(5) , "
            + SUB_CATEGORY_ID + " VARCHAR(5) , "
            + VIDEO_ID + " VARCHAR(5) , "
            + NAME + " VARCHAR(250) , "
            + VIDEO + " TEXT , "
            + CURRENT_POSITION + " INTEGER , "
            + TYPE + " VARCHAR(10) , "
            + PDF_BROCHURE + " TEXT , "
            + IMAGE_PATH + " TEXT , "
            + STATUS + " VARCHAR(5) )";


    private String categoryID;
    private String subcategoryID;
    private String videoID;
    private String name;
    private String video;
    private String type;
    private String status;
    private String pdfBrochure;
    private String imagePath;
    private int currentPostion = 0;

    public ArrayList<Videos> videos;


    public Videos() {
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPdfBrochure() {
        return pdfBrochure;
    }

    public void setPdfBrochure(String pdfBrochure) {
        this.pdfBrochure = pdfBrochure;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }






    public static ArrayList<Videos> getAllCategories(Context context) {
        DatabasesHelper helper = new DatabasesHelper( context);
        ArrayList<Videos> areas = getAllObjects(helper);
        helper.close();
        return areas;
    }

    public static Videos loadFromPOJO(com.feathernet.jayfit.rest.pojos.videosall.Video obj) {
        if(obj == null) return null;
        Videos videos = new Videos();
        videos.subcategoryID = obj.getSubcategory();
        videos.categoryID = obj.getCategory();
        videos.name = obj.getName();
        videos.imagePath = obj.getImagepath();
        videos.video = obj.getVideo();
        videos.videoID = obj.getId();
        videos.pdfBrochure = obj.getFilepath();
        videos.type = obj.getType();
        return videos;
    }



    //DB Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static void insertObject(DatabasesHelper db, Videos object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CATEGORY_ID, object.getCategoryID());
        cv.put(SUB_CATEGORY_ID, object.getSubcategoryID());
        cv.put(VIDEO_ID, object.getVideoID());
        cv.put(NAME, object.getName());
        cv.put(VIDEO, object.getVideo());
        cv.put(TYPE, object.getType());
        cv.put(STATUS, object.getStatus());
        cv.put(PDF_BROCHURE, object.getPdfBrochure());
        cv.put(IMAGE_PATH, object.getImagePath());
        cv.put(CURRENT_POSITION, object.currentPostion);

        long result = sqld.insert(TABLE_NAME, null,cv);
        object.id = result;
        sqld.close();
    }

    public static void insertOrUpdateWrtObjectID(DatabasesHelper db, Videos object) {
        if( object == null) return;
        Videos temp = getAnObjectWithObjectID(db, object.videoID);
        if( temp == null ) {
            insertObject(db, object);
        } else {
            updateObjectWithObjectID(db, object);
        }
    }

    public static void updateObjectWithObjectID(DatabasesHelper db, Videos object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_ID, object.getCategoryID());
        cv.put(SUB_CATEGORY_ID, object.getSubcategoryID());
        cv.put(VIDEO_ID, object.getVideoID());
        cv.put(NAME, object.getName());
        cv.put(VIDEO, object.getVideo());
        cv.put(TYPE, object.getType());
        cv.put(STATUS, object.getStatus());
        cv.put(PDF_BROCHURE, object.getPdfBrochure());
        cv.put(IMAGE_PATH, object.getImagePath());
        cv.put(CURRENT_POSITION, object.currentPostion);
        sqld.update(TABLE_NAME, cv, VIDEO_ID + "='" + object.getVideoID() + "'", null);
        sqld.close();
    }

    public static void updateCurrentPostion(DatabasesHelper db, Videos object) {
        if( object == null) return;
        SQLiteDatabase sqld = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_POSITION, object.currentPostion);
        sqld.update(TABLE_NAME, cv, VIDEO_ID + "='" + object.getVideoID() + "'", null);
        sqld.close();
    }

    public static void insertObjects(DatabasesHelper db, ArrayList<Videos> objects) {
        if( objects == null) return;
        for( Videos object : objects) {
            if( null == object ) continue;
            insertObject(db, object);
        }
    }

    public static Videos getAnObjectFromCursor(Cursor c ) {
        Videos instance = null;
        if( c != null) {
            instance = new Videos();
            instance.id = c.getInt(c.getColumnIndex(ID));
            instance.setCategoryID(c.getString(c.getColumnIndex(CATEGORY_ID)));
            instance.setSubcategoryID(c.getString(c.getColumnIndex(SUB_CATEGORY_ID)));
            instance.setVideoID(c.getString(c.getColumnIndex(VIDEO_ID)));
            instance.setName(c.getString(c.getColumnIndex(NAME)));
            instance.setVideo(c.getString(c.getColumnIndex(VIDEO)));
            instance.setType(c.getString(c.getColumnIndex(TYPE)));
            instance.setStatus(c.getString(c.getColumnIndex(STATUS)));
            instance.setPdfBrochure(c.getString(c.getColumnIndex(PDF_BROCHURE)));
            instance.setImagePath(c.getString(c.getColumnIndex(IMAGE_PATH)));
            instance.currentPostion = c.getInt(c.getColumnIndex(CURRENT_POSITION));

        } else {
        }
        return instance;
    }

    public static ArrayList<Videos> getAllObjects(DatabasesHelper db) {
        ArrayList<Videos> objects = new ArrayList<Videos>();
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

    public static ArrayList<Videos> getAllVideosUnderSubCategory(DatabasesHelper db, String subcategoryID) {
        ArrayList<Videos> objects = new ArrayList<Videos>();
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME  + " WHERE  "
                + SUB_CATEGORY_ID + " ='" + subcategoryID + "'";

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

    public static Videos getAnObjectWithObjectID(DatabasesHelper db, String objectID) {
        Videos object = null;
        SQLiteDatabase dbRead = db.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " WHERE "
                + VIDEO_ID + " = '" + objectID + "'";
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
