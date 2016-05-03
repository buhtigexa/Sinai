package idm.tpf.sinai.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import idm.tpf.sinai.activity.Details;
import idm.tpf.sinai.adapter.JobsAdapter;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.providers.JobsProvider;

import idm.tpf.sinai.R;
/**
 * Created by Mar on 03/05/2016.
 */

public class JobsFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    android.support.v4.content.CursorLoader cursor;
    JobsAdapter adapter;
    static String TAG;
    static final int ID_Fragment=0;
    LoaderManager loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TAG=getClass().getName();
        Log.v(TAG, " ON CREATE");

        adapter = new JobsAdapter(getActivity(), null, 0);
        setListAdapter(adapter);
        loader = getLoaderManager();
        loader.initLoader(ID_Fragment,null,this);


        }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_jobs, menu);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Cursor cursor = adapter.getCursor();
        Bundle bundle=new Bundle();
        bundle.putString(Jobs.TITLE, cursor.getString(cursor.getColumnIndexOrThrow(Jobs.TITLE)));
        bundle.putString(Jobs.COMMENT,cursor.getString(cursor.getColumnIndexOrThrow(Jobs.COMMENT)));
        bundle.putString(Jobs.PATH, cursor.getString(cursor.getColumnIndexOrThrow(Jobs.PATH)));
        bundle.putString(Jobs.DATE, cursor.getString(cursor.getColumnIndexOrThrow(Jobs.DATE)));
        bundle.putDouble(Jobs.LATITUDE, cursor.getDouble(cursor.getColumnIndexOrThrow(Jobs.LATITUDE)));
        bundle.putDouble(Jobs.LONGITUDE, cursor.getDouble(cursor.getColumnIndexOrThrow(Jobs.LONGITUDE)));
        bundle.putLong(Jobs._ID,cursor.getLong(cursor.getColumnIndexOrThrow(Jobs._ID)));
        Intent photoActivity=new Intent(getActivity(), Details.class);
        photoActivity.putExtras(bundle);
        startActivity(photoActivity);

    }


    @Override
    public void onResume() {

        super.onResume();
        if (isAdded() && getActivity()!=null) {
            if (loader!=null){
                loader.restartLoader(ID_Fragment, null, this);
            }
        }
        adapter = new JobsAdapter(getActivity(), null, 0);
        setListAdapter(adapter);
        Log.v(TAG, " ON RESUME");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        cursor=new android.support.v4.content.CursorLoader(getActivity(), JobsProvider.CONTENT_URI,null,null,null,null);
        return cursor;

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        if (adapter !=null && data !=null) {
            adapter.swapCursor(data);
        }
        else {
                Log.v(TAG, "El adaptador es NULL ");
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        if (adapter!=null) {
            adapter.swapCursor(null);
        }
        Log.v(TAG, " ON LOAD RESET");
    }
}
