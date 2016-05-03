
package idm.tpf.sinai.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mar on 04/05/2016.
 */

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String DATE="date";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    static final String TITLE = "title";
    static final String COMMENT = "comment";
    static final String PATH= "path";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "JOBS_DATABASE";
    static final String DATABASE_TABLE = "jobs";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE = "create table " + DATABASE_TABLE  +
            "( _id integer primary key autoincrement, " +
                DATE + "  text not null ,"  +
                LATITUDE  + "double not null , " +
                LONGITUDE + " double not null , " +
                TITLE + " text not null , " +
                COMMENT + " text not null , " +
                PATH +  " text not null )";

    public final Context context;
    public DatabaseHelper DBHelper;
    public SQLiteDatabase db;

    public DBAdapter(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }


    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();

        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertRow(String date,Double lat,Double lng, String title, String comment, String path){

        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, date);
        initialValues.put(LATITUDE, lat);
        initialValues.put(LONGITUDE, lng);
        initialValues.put(TITLE, title);
        initialValues.put(COMMENT, comment);
        initialValues.put(PATH, path);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }


    public boolean deleteRow(long rowId){

        return db.delete(DATABASE_TABLE, KEY_ROWID + " = " + rowId, null) > 0;
    }

    public Cursor getAllRows() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, DATE,LONGITUDE,LATITUDE,TITLE,COMMENT,PATH}, null, null, null, null, null);
    }


    public Cursor getRow(long rowId) throws SQLException{

        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, DATE,LONGITUDE,LATITUDE,TITLE,COMMENT,PATH},
                KEY_ROWID + " = " + rowId, null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public boolean updateRow(long rowId,String date,Double lat,Double lng, String title, String comment, String path){

        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, date);
        initialValues.put(LATITUDE, lat);
        initialValues.put(LONGITUDE, lng);
        initialValues.put(TITLE, title);
        initialValues.put(COMMENT, comment);
        initialValues.put(PATH, path);
        return db.update(DATABASE_TABLE, initialValues, KEY_ROWID + " = " + rowId, null) > 0;
        }




}