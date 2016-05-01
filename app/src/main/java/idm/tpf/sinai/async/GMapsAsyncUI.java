package idm.tpf.sinai.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import idm.tpf.sinai.providers.Jobs;


/**
 * Created by Mar on 05/05/2016.
 */
public class GMapsAsyncUI extends AsyncTask<Cursor,MarkerOptions,GoogleMap> {

    protected GoogleMap mMap;
    protected static String TAG;
    protected Context mCtx;

    public GMapsAsyncUI(GoogleMap map,Context ctx){

        this.TAG=getClass().getCanonicalName();
        this.mMap=map;
        this.mCtx=ctx;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        //Toast.makeText(mCtx," Limpiando el mapa . ",Toast.LENGTH_SHORT).show();
        this.mMap.clear();


    }

    @Override
    protected void onProgressUpdate(MarkerOptions... values) {
        //super.onProgressUpdate(values);
        this.mMap.addMarker(values[0]);
    }

    @Override
    protected GoogleMap doInBackground(Cursor... params) {
        if (this.mMap != null) {
            Cursor cursor = params[0];
            Log.v(TAG," EL cursor pondrá: "  +  cursor.getCount()  + " Marcadores .");
            if (cursor.moveToFirst()) {
                do {
                    if (! this.isCancelled()) {

                        Double lat = cursor.getDouble(cursor.getColumnIndex(Jobs.LATITUDE));
                        Double lng = cursor.getDouble(cursor.getColumnIndex(Jobs.LONGITUDE));
                        String title = cursor.getString(cursor.getColumnIndex(Jobs.TITLE));
                        String comment = cursor.getString(cursor.getColumnIndex(Jobs.COMMENT));
                        MarkerOptions markOps = new MarkerOptions();
                        markOps.position(new LatLng(lat /*+ ThreadLocalRandom.current().nextInt(0, 10 + 1)*/, lng /*+ ThreadLocalRandom.current().nextInt(10, 20 + 1*/));
                        markOps.title(title);
                        markOps.snippet(comment);
                        //this.mMap.addMarker(markOps);
                        publishProgress(markOps);
                    }
                    Log.v(TAG, " Añadiendo Marker !!!");
                    //Toast.makeText(this.mCtx, " Añadiendo Marcador!!", Toast.LENGTH_SHORT).show();
                }
                while (cursor.moveToNext());
            }
        }
    return this.mMap;
    }
}
