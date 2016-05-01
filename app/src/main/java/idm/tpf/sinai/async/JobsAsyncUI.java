package idm.tpf.sinai.async;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import idm.tpf.sinai.R;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Utils;


/**
 * Created by Mar on 05/05/2016.
 */
public class JobsAsyncUI extends AsyncTask<Cursor,View,ViewAsyncHolder> {

    protected Cursor mCursor;
    protected View mView;
    protected String title,comment,path;
    protected Bitmap bmp;
    protected static String TAG;

    public JobsAsyncUI(View view,Cursor cursor){

        TAG=getClass().getCanonicalName();
        this.mCursor=cursor;
        this.mView=view;
    }


    @Override
    protected ViewAsyncHolder doInBackground(Cursor... params) {

        this.mCursor=params[0];
        title=mCursor.getString(mCursor.getColumnIndex(Jobs.TITLE));
        comment=mCursor.getString(mCursor.getColumnIndex(Jobs.COMMENT));
        path=mCursor.getString(mCursor.getColumnIndex(Jobs.PATH));

        bmp= Utils.getThumbnail(path);
        //bmp=Utils.getCorrectlyThumbnail(path,20,20);
        ViewAsyncHolder viewHolder=new ViewAsyncHolder(title,comment,bmp);

        Log.v(TAG, " doInBackground complete");

        return viewHolder;
    }


    @Override
    protected void onPostExecute(ViewAsyncHolder viewAsyncHolder) {

        TextView tvDescription=(TextView)mView.findViewById(R.id.tvDescription);
        TextView tvTitle=(TextView)mView.findViewById(R.id.tvTitle);
        ImageView ivImage=(ImageView)mView.findViewById(R.id.ivIcon);

        if (viewAsyncHolder.bmp==null){
            Log.v(TAG, "El thumbnail para esta imagen es NULL " + path);
        }
        ivImage.setImageBitmap(viewAsyncHolder.bmp);
        tvDescription.setText(viewAsyncHolder.comment);
        tvTitle.setText(viewAsyncHolder.title);
        Log.v(TAG, " doInBackground complete");

    }
}
