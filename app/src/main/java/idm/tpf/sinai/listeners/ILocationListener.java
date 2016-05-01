package idm.tpf.sinai.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import idm.tpf.sinai.activity.MainActivity;



/**
 * Created by Mar on 30/04/2016.
 */
public class ILocationListener implements LocationListener{

    Context mCtx;

    final String TAG = getClass().getCanonicalName();

    public  ILocationListener(Context ctx) {
        // TODO Auto-generated constructor stub
        mCtx=ctx;
    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        if (location!=null){
            MainActivity.currentLocation=location;
            //Log.v(TAG, location.getProvider() + "----------------------------");
            //Log.v(TAG, "Latitud :" +  location.getLatitude()  );
            //Log.v(TAG, "Longitud :" +  location.getLongitude() );

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        //Toast.makeText(mCtx, provider + "disabled", Toast.LENGTH_SHORT).show();
        //Log.v(TAG, "DISABLED : " +  provider + "disabled" );
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        //Toast.makeText(mCtx,provider + "Enabled",Toast.LENGTH_SHORT).show();
        //Log.v(TAG, "ENABLED : " +  provider + "Enabled" );

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}
