package idm.tpf.sinai.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import idm.tpf.sinai.R;
import idm.tpf.sinai.async.ExifWorker;
import idm.tpf.sinai.async.QueryHandler;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.providers.JobsProvider;
import idm.tpf.sinai.utils.Utils;


public abstract class Edit extends Activity {

	String path;
	int count ;
    String dateTime;
	static  String TAG;
	SimpleDateFormat sdf;
    QueryHandler queryHandler;
    EditText etTitle;
    EditText description;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

        TAG=getClass().getCanonicalName();
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
        path=getIntent().getStringExtra("RECENT_IMAGE_FILE"); // tambien puedo usar la URI: MediaStore.EXTRA_OUTPUT, outputFileUri
        ImageView snapShot=(ImageView)findViewById(R.id.ivSnapshot);


        // levanto la foto que saqué recién. Esto estaba muy bien! pero la imagen es rotada.
        //snapShot.setImageBitmap(BitmapFactory.decodeFile(path));

        // con esto acomodo la imagen en posicion correcta.
        snapShot.setImageBitmap(Utils.getImageCorrectly(path));

        etTitle =(EditText)findViewById(R.id.etTitle);
        description =(EditText)findViewById(R.id.etDescription);
		queryHandler=new QueryHandler(getContentResolver());


        Log.v(TAG, "EDIT ACTIVITY ON CREATE :" + path);

    }


	public void onSave(View view){


        Log.v(TAG, "ON SAVE ....");

        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateTime = sdf.format(new Date());


        Double lat=0.0;
        Double lng=0.0;

        Location loc=MainActivity.currentLocation;

        // RARO ! , pero si no hubiere ninguna usamos la ultima conocida.

        if (loc==null){

            Toast.makeText(this, " No encuentro ubicación ! ", Toast.LENGTH_LONG).show();
            loc= Utils.getLastKnownLocation(this);

        }

        Log.v(TAG, " Time de localización : " + loc.getTime());

        try
        {
            lat=loc.getLatitude();
            lng=loc.getLongitude();
        }

        catch(NullPointerException e){

            lat=0.0;
            lng=0.0;
            Log.e(TAG, "LOCATION IS NULL ----------------------------------------------------------------");
        }


        String title=etTitle.getText().toString();
        String comment=description.getText().toString();

        if (comment.isEmpty()) {

            comment=" Sin     descripción para esta foto.......  ";
        }

        if (title.isEmpty()) {
            title="  Sin   titulo.  ";
        }

        // escritura asincrónica en el content provider y en los atributos de la imagen.
        ExifWorker exifWorker= new ExifWorker(this,dateTime,lat,lng,title,comment,path);
        exifWorker.run();


        //Utils.addPhoto(queryHandler, dateTime, lat, lng, title, comment, path);
        addPhoto(dateTime, lat, lng, title, comment, path);
       back();


    }


    protected void addPhoto(String dateTime,Double lat,Double lng,String title,String comment,String path){

        Log.v("ADD PHOTO:"," PATH " + path);
        ContentValues cv=new ContentValues();
        cv.put(Jobs.DATE, dateTime);
        cv.put(Jobs.LATITUDE, lat);
        cv.put(Jobs.LONGITUDE,lng);
        cv.put(Jobs.TITLE, title);
        cv.put(Jobs.COMMENT, comment);
        cv.put(Jobs.PATH, path);
        queryHandler.startInsert(2, null, JobsProvider.CONTENT_URI, cv);

    }

	public abstract void onCancel(View   view);




    protected abstract void back();
        // TAl vez el caso de que sea una nueva foto, tengo que acomodarlo con herencia. Si la actividad es lanzada al agregar una foto,// disparo el intent
        // sino: finish().

      //  Intent mainIntent=new Intent(this,Main.class);
      //  startActivity(mainIntent);



    }
