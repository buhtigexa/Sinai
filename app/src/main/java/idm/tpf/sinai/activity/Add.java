package idm.tpf.sinai.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Exif;
import idm.tpf.sinai.utils.ExifComments;
import idm.tpf.sinai.utils.Utils;


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
        boolean isSinaiFile = Utils.isSinaiFile(photoPath);
        TAG = getClass().getCanonicalName();
        Exif exif=new ExifComments(photoPath);
        Bundle bundle=new Bundle();


        if (isSinaiFile) {

            // si es arhivo de Sinai, lo voy a poder ver en Details.Activity ( sin eliminar ni compartir, sino Sólo Agregar ): Si agrega, lo copio al directorio
            // propio-
            Double lat=exif.getLatitude();
            Double lng=exif.getLongitude();
            String date=exif.getDate();
            String title=exif.getTitle();
            String comment=exif.getComment();

            bundle.putDouble(Jobs.LATITUDE,lat);
            bundle.putDouble(Jobs.LONGITUDE,lng);
            bundle.putString(Jobs.DATE, date);
            bundle.putString(Jobs.TITLE, title);
            bundle.putString(Jobs.COMMENT, comment);
            bundle.putString(Jobs.PATH,photoPath);

            Intent newJob = new Intent(this, NewJob.class);
            newJob.putExtra("RECENT_IMAGE_FILE", photoPath);
            newJob.putExtras(bundle);

            startActivity(newJob);

        }
        else {
            // Si no es archivo de Sina, voy a usar Edit . Si agrega, lo copio al directorio propio.

            Double lat=exif.getLatitude();
            Double lng=exif.getLongitude();
            String date=exif.getDate();
            String title=exif.getTitle();
            String comment=exif.getComment();

            Intent editIntent = new Intent(this, EditNewJob.class);
            editIntent.putExtra("RECENT_IMAGE_FILE", photoPath);
            startActivity(editIntent);
        }
    }



    protected String getNameFromUri(Uri uri) {

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        //int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

        if (nameIndex >= 0) {
            return cursor.getString(nameIndex);
        } else {
            return null;
        }
    }
/*
    private void copyInputStreamToFile(InputStream in, String path) {

        try {


            OutputStream os = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while ((bytesRead = in.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            in.close();

            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


*/
}
