package idm.tpf.sinai.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import idm.tpf.sinai.R;
import idm.tpf.sinai.async.QueryHandler;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Utils;


public class NewJob extends AbsDetail {

    protected QueryHandler queryHandler;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id== R.id.action_insert){

            // Acá podría incorporar la foto en el content provider. Con ese path, podría copiarlo al directorio de la app.

            queryHandler=new QueryHandler(getContentResolver());
            Utils.addPhoto(queryHandler, bundle.getString(Jobs.DATE), bundle.getDouble(Jobs.LATITUDE),
                    bundle.getDouble(Jobs.LONGITUDE), bundle.getString(Jobs.TITLE), bundle.getString(Jobs.COMMENT)
                    , bundle.getString(Jobs.PATH));
        }

        if (id == android.R.id.home ){
        }


        Intent mainIntent=new Intent(this,MainActivity.class);
//        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(mainIntent);
        finish();
        return true;
    }

    @Override
    protected void setMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_new_photo, menu);
    }

}
