
package idm.tpf.sinai.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import idm.tpf.sinai.R;
import idm.tpf.sinai.async.JobsAsyncUI;


/**
 * Created by Mar on 03/05/2016.
 */
public class JobsAdapter extends CursorAdapter {

    public JobsAsyncUI worker;
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

        worker=new JobsAsyncUI(view,cursor);
        worker.execute(cursor);
        Log.v(TAG, "JOBS WORKER ASYNC UI" );

    }
}
