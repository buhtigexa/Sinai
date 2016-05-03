
package idm.tpf.sinai.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import idm.tpf.sinai.R;
import idm.tpf.sinai.async.JobsAsyncUI;
import idm.tpf.sinai.async.ViewAsyncHolder;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Utils;


/**
 * Created by Mar on 03/05/2016.
 */
public class JobsAdapter extends CursorAdapter {

    //public JobsAsyncUI worker;
    public static String TAG;
    public JobsAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);

        TAG=getClass().getCanonicalName();
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_item,parent,  false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        /*worker=new JobsAsyncUI(view,cursor);
        worker.execute(cursor);
        Log.v(TAG, "JOBS WORKER ASYNC UI" );
        */
        String title,comment,path;
        Bitmap bmp;
        this.mCursor=cursor;
        title=mCursor.getString(mCursor.getColumnIndex(Jobs.TITLE));
        comment=mCursor.getString(mCursor.getColumnIndex(Jobs.COMMENT));
        path=mCursor.getString(mCursor.getColumnIndex(Jobs.PATH));

        bmp= Utils.getThumbnail(path);
        //bmp=Utils.getCorrectlyThumbnail(path,20,20);
        ViewAsyncHolder viewHolder=new ViewAsyncHolder(title,comment,bmp);

        Log.v(TAG, " doInBackground complete");
        TextView tvDescription=(TextView)view.findViewById(R.id.tvDescription);
        TextView tvTitle=(TextView)view.findViewById(R.id.tvTitle);
        ImageView ivImage=(ImageView)view.findViewById(R.id.ivIcon);

        if (bmp==null){
            Log.v(TAG, "El thumbnail para esta imagen es NULL " + path);
        }
        ivImage.setImageBitmap(bmp);
        tvDescription.setText(comment);
        tvTitle.setText(title);
        Log.v(TAG, " doInBackground complete");


    }



}
