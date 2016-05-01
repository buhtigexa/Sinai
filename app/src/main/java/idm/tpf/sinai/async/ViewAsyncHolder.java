package idm.tpf.sinai.async;

import android.graphics.Bitmap;

/**
 * Created by Mar on 05/05/2016.
 */
public class ViewAsyncHolder {

    public String title;
    public String comment;
    public Bitmap bmp;

    public ViewAsyncHolder(String title,String comment,Bitmap bmp){

        this.title=title;
        this.comment=comment;

        this.bmp=bmp;
    }
}