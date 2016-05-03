package idm.tpf.sinai.fragments;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import idm.tpf.sinai.R;
import idm.tpf.sinai.activity.MainActivity;
import idm.tpf.sinai.async.GMapsAsyncUI;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.providers.JobsProvider;


/**
 * Created by Mar on 02/05/2016.
 */
public class    GMapFragment extends Fragment {

    static public MapView mapView;
    static public GoogleMap map;
    static final int gmapRequest=1;
    static  String TAG;
    protected GMapsAsyncUI worker;

    String [] projection={Jobs.DATE,Jobs.LATITUDE,Jobs.LONGITUDE,Jobs.TITLE, Jobs.COMMENT,Jobs.PATH};
    MyQueryHandler queryHandler;

    public class MyQueryHandler extends AsyncQueryHandler {

        public MyQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            worker=new GMapsAsyncUI(map,getActivity());
            worker.execute(cursor);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        TAG=getClass().getCanonicalName();
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gmaps_menu,menu);

    }

    @Override
    public void onResume() {

        super.onResume();
        try {
            if (mapView != null) {
                mapView.onResume();
                // el fragmento tiene que ser visible,...
                if (map != null) {
                    queryHandler = new MyQueryHandler(MainActivity.contentResolver);
                    queryHandler.startQuery(gmapRequest, null, JobsProvider.CONTENT_URI, projection, null, null, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "on Resume: Un problema al recargar el mapa.");
        }
    }
    @Override
    public void onDestroy() {
        try{
            if (mapView!=null) {
                mapView.onDestroy();

            }
            // frenar el worker que pone los marcadores.
        }
        catch(NullPointerException e){
            e.printStackTrace();
            Log.v(TAG,"NULL POINTER EXCEPTION!!");
        }
        super.onDestroy();

    }

    @Override
    public void onStop() {
        Log.v(TAG," ON STOP ");
        super.onStop();
        if (worker!=null){
            Log.v(TAG, " Cancelando el Worker ...");
            worker.cancel(true);
        }

    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}