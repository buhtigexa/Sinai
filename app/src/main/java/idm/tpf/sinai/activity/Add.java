package idm.tpf.sinai.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import idm.tpf.sinai.strategies.SinaiFileContext;

public class Add extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public static String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        String photoPath = getNameFromUri(uri);
        TAG = getClass().getCanonicalName();


        SinaiFileContext sinaiFileContext=new SinaiFileContext(this,photoPath);
        sinaiFileContext.doAction();
        finish();


    }



    protected String getNameFromUri(Uri uri) {

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        int nameIndex=0;
        if (cursor!=null) {
            cursor.moveToFirst();
            nameIndex= cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        }



        if (nameIndex >= 0) {
            return cursor.getString(nameIndex);
        } else {
            return new String();
        }
    }

}
