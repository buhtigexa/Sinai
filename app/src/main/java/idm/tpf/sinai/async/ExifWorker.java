package idm.tpf.sinai.async;

import android.content.Context;

import idm.tpf.sinai.utils.Exif;
import idm.tpf.sinai.utils.ExifComments;


/**
 * Created by Mar on 06/05/2016.
 */
public class ExifWorker implements  Runnable{

    public String dateTime,title,comment,path;
    public Double latitude,longitude;
    public Context mCxt;
    private String TAG;

    public ExifWorker(Context ctx, String dateTime, Double latitude, Double longitude, String title, String comment, String path){

        TAG=getClass().getCanonicalName();

        this.dateTime=dateTime;
        this.latitude=latitude;
        this.longitude=longitude;
        this.title=title;
        this.comment=comment;
        this.path=path;
        this.mCxt=ctx;

    }
    @Override
    public void run() {

        Exif exif=new ExifComments(path);
        exif.setDate(dateTime);
        exif.setTitle(title);
        exif.setComment(comment);
        exif.setLatitude(latitude);
        exif.setLongitude(longitude);
        exif.close();


    }
}
