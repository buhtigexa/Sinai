package idm.tpf.sinai.strategies;

import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;

import java.io.IOException;

import idm.tpf.sinai.activity.NewJob;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Exif;
import idm.tpf.sinai.utils.ExifComments;

/**
 * Created by Mar on 02/05/2016.
 */
public class AddJobAction implements IAction {



    protected Context mCtx;

    public AddJobAction(Context ctx){


        this.mCtx=ctx;
    }

    @Override
    public void excecute(Object object) {

        ExifComments exif=(ExifComments)object;

        Bundle bundle=new Bundle();

        Double lat=exif.getLatitude();
        Double lng=exif.getLongitude();
        String date=exif.getDate();
        String title=exif.getTitle();
        String comment=exif.getComment();

        bundle.putDouble(Jobs.LATITUDE,lat);
        bundle.putDouble(Jobs.LONGITUDE, lng);
        bundle.putString(Jobs.DATE, date);
        bundle.putString(Jobs.TITLE, title);
        bundle.putString(Jobs.COMMENT, comment);
        bundle.putString(Jobs.PATH, exif.path);

        Intent newJob = new Intent(mCtx, NewJob.class);
        newJob.putExtra("RECENT_IMAGE_FILE",bundle.getString(Jobs.PATH));
        newJob.putExtras(bundle);
        mCtx.startActivity(newJob);


    }
}
