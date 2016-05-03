package idm.tpf.sinai.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Date;
import idm.tpf.sinai.R;
import idm.tpf.sinai.adapter.ViewPagerAdapter;
import idm.tpf.sinai.fragments.GMapFragment;
import idm.tpf.sinai.fragments.HomeFragment;
import idm.tpf.sinai.fragments.JobsFragment;
import idm.tpf.sinai.listeners.ILocationListener;
import idm.tpf.sinai.listeners.PageListener;


public class MainActivity extends AppCompatActivity {


    public static SharedPreferences pref;
    public static String dbName="sinai";
    public static String home = "/sinai/";
    public static String photos = "photos/";
    public static String thumbnails = "thumbnails/";
    public static String recentImageFileName;
    public static File recentImageFile;
    public static double latitude;
    public static double longitude;


    public static final int CAMERA_PIC_REQUEST = 1337;
    public static int minTimeGps = 500;
    public static int minDistNet = 0;
    public static int minDistGps = 1;
    public static int minTimeNet = 100;
    public static final int ID_JobsFragment=1;

    final String TAG = getClass().getCanonicalName();


    public static Location currentLocation;
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    public ViewPager viewPager;
    public LocationManager lm;
    public LocationListener locationListener;

    public static ContentResolver contentResolver;
    public static PageListener pageListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        this.pageListener=new PageListener(this,viewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new ILocationListener(this);
        setUpDirectory();
        contentResolver = getContentResolver();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    protected void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new JobsFragment(), "My Jobs");
        adapter.addFragment(new GMapFragment(), "View on GMaps");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeGps, minDistGps, locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeNet, minDistNet, locationListener);


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.removeUpdates(locationListener);
    }

    protected void setUpDirectory(){


        latitude=0;
        longitude=0;

        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE );
        Log.d("MainActiviry-setUp", "Directorio: " + Environment.getExternalStorageDirectory().toString());
        home=Environment.getExternalStorageDirectory().toString()+"/sinai/";
        photos=Environment.getExternalStorageDirectory().toString()+"/sinai/photos/";
        thumbnails= Environment.getExternalStorageDirectory().toString()+"/sinai/thumbnails/"; // este lo tengo que sacar despues

        Log.i("Main-setUp", "home : "  +  home);
        Log.i("Main-setUp", "photos : "  +  photos);
        Log.i("Main-setUp", "Thumbnails : "  +  thumbnails);

        SharedPreferences.Editor editor=pref.edit();
        if (pref.getString("dbName", null)==null){
            editor.putString("dbName",dbName);
            editor.commit();
        }
        if (pref.getString("home", null)==null){
            editor.putString("home",home);
            editor.commit();
        }
        if (pref.getString("photos",null)==null){
            editor.putString("photos", photos);
            editor.commit();
        }
        if (pref.getString("thumbnails",null)==null){
            editor.putString("thumbnails", thumbnails);
            editor.commit();
        }

        boolean success=editor.commit();
        boolean dirCreateHome=true;
        boolean dirCreatePhotos=true;
        boolean dirCreateThumbnails=true;
        File dirHome = new File(home);
        if (!dirHome.exists()){
            dirCreateHome=dirHome.mkdir();

        }
        File dirPhotos = new File(photos);
        if (!dirPhotos.exists()){
            dirCreatePhotos=dirPhotos.mkdir();
        }
        File dirThumbnails = new File(thumbnails);
        if (!dirThumbnails.exists()){
            dirCreateThumbnails=dirThumbnails.mkdir();
        }

        Log.i("Main-setUp", "Pref Home  en: " + pref.getString("home", null));
        Log.i("Main-setUp", "Pref Photos en: " + pref.getString("photos", null));
        Log.i("Main-setUp", "Pref Thumbnails en: " + pref.getString("thumbnails", null));
        if (!success){
            Log.e("Main-SetUp", "Error en shared preferences");
        }

        Log.i("Main-setUp", "Home  en: " + dirHome.getAbsolutePath());
        Log.i("Main-setUp", "Photos en: " + dirPhotos.getAbsolutePath());
        Log.i("Main-setUp", "Thumbnails en: " + dirThumbnails.getAbsolutePath());

        if (!dirCreateHome || !dirCreatePhotos || !dirCreateThumbnails)	{
            Log.e("Main-setUp", "Fallo al crear directorios");
        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Si la foto se capturó bien, editarla.

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Intent editIntent = new Intent(this,EditCurrentJob.class);
            Uri outputFileUri = Uri.fromFile(recentImageFile);
            editIntent.putExtra("RECENT_IMAGE_FILE", outputFileUri.getPath());
            startActivity(editIntent); // editar foto.
            }
       }

    public void getPhoto(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        recentImageFileName= sdf.format(new Date())+".jpg";
        recentImageFile=new File(photos+recentImageFileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri outputFileUri = Uri.fromFile(recentImageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }
}
