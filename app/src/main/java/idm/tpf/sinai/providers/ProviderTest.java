package idm.tpf.sinai.providers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by Mar on 04/05/2016.
 */
public class ProviderTest {

    protected static String TAG;
    Context mCtx;
    ContentResolver cr;

    public ProviderTest(Context ctx){

        TAG=getClass().getName();
        mCtx=ctx;
        cr=mCtx.getContentResolver();

    }

    public void getAll(){

        String[] projection = new String[] {
                Jobs._ID,
                Jobs.DATE,
                Jobs.LATITUDE,
                Jobs.LONGITUDE,
                Jobs.TITLE,
                Jobs.COMMENT,
                Jobs.PATH
        };


        Cursor c =cr.query( JobsProvider.CONTENT_URI,projection,null,null,null);

        if (c.moveToFirst()) {

            String date = c.getString(c.getColumnIndex(Jobs.DATE));
            Double lat = c.getDouble(c.getColumnIndex(Jobs.LATITUDE));
            Double lng = c.getDouble(c.getColumnIndex(Jobs.LONGITUDE));
            String title = c.getString(c.getColumnIndex(Jobs.TITLE));
            String comment = c.getString(c.getColumnIndex(Jobs.COMMENT));
            String path = c.getString(c.getColumnIndex(Jobs.PATH));

            do {
                Log.v(TAG, " ------------------------------------------------------------");
                Log.v(TAG, date + "  " + lat + "  " + title + "  " + comment + "  " + path);

            }while (c.moveToNext());

        }
    }

    public void insert(){

        /*
        ContentValues values = new ContentValues();

        values.put(Jo.COL_NOMBRE, "ClienteN");
        values.put(Clientes.COL_TELEFONO, "999111222");
        values.put(Clientes.COL_EMAIL, "nuevo@email.com");

        ContentResolver cr = getContentResolver();

        cr.insert(ClientesProvider.CONTENT_URI, values);
        */
    }

}
