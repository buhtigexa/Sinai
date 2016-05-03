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

        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }
}
