package idm.tpf.sinai.utils;

import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by Mar on 09/05/2016.
 */
public abstract class Exif {

    protected ExifInterface exifInterface;


    public Exif(String path) {

        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract void setLatitude(Double latitude);

    public abstract void setLongitude(Double longitude);

    public abstract Double getLatitude();

    public abstract Double getLongitude();

    public abstract void setComment(String comment);

    //protected abstract void setAttr(int pos, String value);

    public abstract String getComment();

    public abstract void setTitle(String title);

    public abstract String getTitle();


    public void setDate(String date){
        exifInterface.setAttribute(ExifInterface.TAG_DATETIME,date);
    }

    public  String getDate(){
        return exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
    }

    public  abstract  void close();




}
