package idm.tpf.sinai.utils;

/**
 * Created by Mar on 01/05/2016.
 */

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import idm.tpf.sinai.async.QueryHandler;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.providers.JobsProvider;


public class Utils {


    final static String TAG = "Utils";

    public Utils() {

    }

    static public void addPhoto(QueryHandler queryHandler,String dateTime,Double lat,Double lng,String title,String comment,String path){

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

    static public ArrayList<String> getListFiles(String path) {


        ArrayList<String> list = new ArrayList<String>();
        Log.d(TAG, "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d(TAG, "Size: " + file.length);
        for (int i = 0; i < file.length; i++) {
            list.add(file[i].getAbsolutePath());
            Log.d(TAG, "FileName:" + file[i].getName() + " Size: " + file[i].length());
        }
        Log.d("", "");
        return list;
    }

    static public void listFiles(ArrayList<String> files) {

        for (String s : files) {
            Log.v("listFiles", s);

        }
    }

    // esta es otra forma de obtener un Thumbnail....


    static public Bitmap getThumbnail_(Context ctx,String path){

        //Uri uri= Uri.fromFile(new File(path));
        //Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(ctx.getContentResolver(), uri,MediaStore.Images.Thumbnails.MINI_KIND,
          //
          //                                                    (BitmapFactory.Options) null );
        return null;
    }

    static public Bitmap getThumbnail(String path) {

        Bitmap thumbnail = null;

        ExifInterface exif;

        try {
            exif = new ExifInterface(path);
            byte[] imageData = exif.getThumbnail();
            int length=imageData.length;
            if (imageData != null) {
                thumbnail = BitmapFactory.decodeByteArray(imageData, 0, length);
            }


        } catch(Exception e){

            e.printStackTrace();
            Log.v("Utils-getThumbnail", "La imagen no se encuentra - Path erróneo. " + path);

        }

        if (thumbnail==null){
            Log.v(TAG, " Thumbnail is null from : "  + path);
            thumbnail=ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 30, 30);
        }
        return thumbnail;
    }

    static public void createFile(Bitmap bmp, String pathDestiny) {

        Log.v("Utils-createFile ", " Creando thumbnail en " + pathDestiny);
        if (bmp == null) {
            Log.e("Utils-createFile", "El bmp es null");
            return;
        }

        try {
            File file = new File(pathDestiny);
            if (!file.exists()) {
                Log.e("Utils", "File thumbnail NO creado");
            }
            FileOutputStream fOut = new FileOutputStream(pathDestiny);
            bmp.compress(Bitmap.CompressFormat.PNG, 68, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.e(null, "Error al crear thumbnail.");
            e.printStackTrace();
            Log.e(null, "Error al crear thumbnail.");
        }
    }

    public static Location getLastKnownLocation(Context mCtx) {


        Toast.makeText(mCtx, "Solicitando location", Toast.LENGTH_SHORT).show();
        boolean gps_enabled = false;
        boolean network_enabled = false;

        LocationManager lm = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);

        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location net_loc = null, gps_loc = null, finalLoc = null;

        if (gps_enabled)
            if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
        gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.v("GPS", "I'm up!");
        if (network_enabled)
            net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.v("WIFI", "I'm up!");
        if (gps_loc != null && net_loc != null) {

            if (gps_loc.getAccuracy() >= net_loc.getAccuracy())
                finalLoc = gps_loc;
            else
                finalLoc = net_loc;

            // I used this just to get an idea (if both avail, its upto you which you want to take as I taken location with more accuracy)

        } else {

            if (gps_loc != null) {
                Toast.makeText(mCtx, "PROVISTA POR GPS", Toast.LENGTH_SHORT).show();
                finalLoc = gps_loc;
            } else if (net_loc != null) {
                Toast.makeText(mCtx, "PROVISTA POR WIFI", Toast.LENGTH_SHORT).show();
                finalLoc = net_loc;
            }
        }


        return finalLoc;

    }



    static public void DisplayRow(Cursor c){
    // solo quiero mostrar una fila de una tabla de la base de datos.
        Log.v( " DB :"  ,c.getString(0) + "  "  +  c.getString(1) + "  " + c.getString(2) + "  " +c.getString(3) + "  " +  c.getString(4) +
                "   " +  c.getString(5) + "   " + c.getString(6));

    }



    public static boolean isSinaiFile(String path) {

        boolean is=false;
        String userComment=new String();
        try {
            ExifInterface exif = new ExifInterface(path);
            userComment= exif.getAttribute("UserComment");
            if (userComment == null) {
                return false;
            }
            Pattern pat = Pattern.compile(".*#sinai#.*");
            Matcher mat = pat.matcher(userComment);
            is=mat.matches() ? true : false;
        }
            catch (IOException e) {
                e.printStackTrace();
        }

        return is;

    }




    public static String stripExtension(final String s){

        return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
        }

    public static Bitmap getImageCorrectly(String path){

        Bitmap myBitmap=null;
        try
        {
            myBitmap=BitmapFactory.decodeFile(path);
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG," Error al rotar imagen o archivo no encontrado");
        }

        return myBitmap;
    }


    public static Bitmap getCorrectlyThumbnail(String path,int width,int height){

        Bitmap bmp=getImageCorrectly(path);
        Bitmap thumbnail=null;

        if (bmp!=null){

            thumbnail=ThumbnailUtils.extractThumbnail(bmp,width,height);
        }

        return thumbnail;
    }
}




