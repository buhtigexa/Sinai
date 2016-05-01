package idm.tpf.sinai.utils;

import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import idm.tpf.sinai.providers.Jobs;


/**
 * Created by Mar on 09/05/2016.
 */

/*
Pattern pattern = Pattern.compile("(<<titulo>)([^/>]+)");
        Matcher matcher = pattern.matcher(text);


        while(matcher.find()) {
        System.out.println("found:" +"  ---- "  + matcher.group(2));
        }
     */

public class ExifComments extends  Exif {

    private static  String TAG  ;
    protected String title;
    protected String comment;
    protected String latitude;
    protected String longitude;
    protected String userComment;

    public ExifComments(String path) {

        super(path);
        TAG = getClass().getCanonicalName();
        userComment = exifInterface.getAttribute("UserComment");

        if (userComment==null){
            userComment=new String();
        }
        this.title=getAttribute(Jobs.TITLE);
        this.comment=getAttribute(Jobs.COMMENT);
        this.latitude=getAttribute(Jobs.LATITUDE);
        this.longitude=getAttribute(Jobs.LONGITUDE);
    }


    protected String getAttribute(String attr){

        String match=new String();
        Pattern pattern= Pattern.compile("(<<"+attr+">)([^/>]+)");
        Matcher matcher=pattern.matcher(userComment);
        if (matcher.find()){
            match= matcher.group(2);
        }
        return match;
    }

    @Override
    public void setLatitude(Double latitude) {

        this.latitude=latitude.toString();
    }

    @Override
    public void setLongitude(Double longitude) {

        this.longitude=longitude.toString();
    }

    @Override
    public Double getLatitude() {

        Double latitude=0.0;

        try{
            latitude=Double.valueOf(this.latitude);
        }
        catch(NumberFormatException e){
            Log.v(TAG, " Se está convirtiendo un String no numérico");
        }
        return latitude;


    }

    @Override
    public Double getLongitude() {

        Double longitude=0.0;

        try{
            longitude=Double.valueOf(this.longitude);
        }
        catch(NumberFormatException e){
            Log.v(TAG, " Se está convirtiendo un String no numérico");
        }
        return longitude;
    }

    @Override
    public void setComment(String comment) {

        this.comment=comment;
    }

    @Override
    public String getComment() {

        return this.comment;
    }

    @Override
    public void setTitle(String title) {

        this.title=title;
    }

    @Override
    public String getTitle() {

        return this.title;
    }

    @Override
    public void close() {

        try {
            userComment="####sinai####"+"<<" + Jobs.TITLE+ ">"     + this.title + "/>" + "<<" + Jobs.COMMENT+ ">"+ this.comment + "/>" +
                        "<<" + Jobs.LATITUDE + ">"+ this.latitude + "/>" + "<<" + Jobs.LONGITUDE + ">"+ this.longitude + "/>";

            exifInterface.setAttribute("UserComment",userComment);
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
