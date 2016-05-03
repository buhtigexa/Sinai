package idm.tpf.sinai.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import idm.tpf.sinai.adapter.DBAdapter;


/**
 * Created by Mar on 04/05/2016.
 */
public class JobsProvider extends ContentProvider {

    private static final String uri = "content://sinai.idm.tpf.providers/jobs";
    //UriMatcher
    private static final int JOBS = 1;
    private static final int JOBS_ID = 2;
    private static final UriMatcher uriMatcher;
    public static final Uri CONTENT_URI = Uri.parse(uri);



    public static DBAdapter db;

    static final String DATABASE_NAME = "JOBS_DATABASE";
    static final String DATABASE_TABLE = "jobs";
    static final int DATABASE_VERSION = 1;

    protected JobsSqlHelper jobsHelper;


    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("sinai.idm.tpf.providers", "jobs", JOBS);
        uriMatcher.addURI("sinai.idm.tpf.providers", "jobs/#", JOBS_ID);
    }





    @Override
    public boolean onCreate() {

        jobsHelper = new JobsSqlHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.reset();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == JOBS_ID){
            where = " _id = " + uri.getLastPathSegment();
        }

        SQLiteDatabase db = jobsHelper.getWritableDatabase();

        Cursor c =db.query(DATABASE_TABLE,projection,where,selectionArgs,null,null,sortOrder);
        return c;


    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case JOBS:
                return "vnd.android.cursor.dir/vnd.sgoliver.cliente";
            case JOBS_ID:
                return "vnd.android.cursor.item/vnd.sgoliver.cliente";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long regId = 1;
        SQLiteDatabase db = jobsHelper.getWritableDatabase();
        regId = db.insert(DATABASE_TABLE, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        Log.v("PROVIDER : ",  " Selection "   + selection +  " Selection ARgs: "  + selectionArgs );
        if(uriMatcher.match(uri) == JOBS_ID){
            where = " _id = " + uri.getLastPathSegment();
            Log.v("PROVICER", "Clausula Where :" + where );
        }

        SQLiteDatabase db = jobsHelper.getWritableDatabase();

        cont = db.delete(DATABASE_TABLE, where, selectionArgs);

        return cont;
    }

    public void close(){

        SQLiteDatabase db = jobsHelper.getWritableDatabase();
        db.close();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == JOBS_ID){
            where = " _id = " + uri.getLastPathSegment();
        }

        SQLiteDatabase db = jobsHelper.getWritableDatabase();

        cont = db.update(DATABASE_TABLE, values, where, selectionArgs);

        return cont;
    }

    public void reset(){

        SQLiteDatabase db = jobsHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        //Se crea la nueva versión de la tabla
        db.execSQL(JobsSqlHelper.DATABASE_CREATE);
    }
}
