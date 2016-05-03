package idm.tpf.sinai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import idm.tpf.sinai.R;
import idm.tpf.sinai.async.QueryHandler;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Utils;


public class NewJob extends AbsDetail {

    protected QueryHandler queryHandler;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_insert:

               // acá tendría que abstraer un poco, y también puedo encapsular la operación que se haga según un SI/NO


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Sinai - Añadir nuevo trabajo..");
                alertDialog.setMessage("¿ Deseás incorporar este trabajo ?");
                alertDialog.setIcon(R.drawable.ic_done_white_36dp);

                alertDialog.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        queryHandler = new QueryHandler(getContentResolver());
                        Utils.addPhoto(queryHandler, bundle.getString(Jobs.DATE), bundle.getDouble(Jobs.LATITUDE),
                                bundle.getDouble(Jobs.LONGITUDE), bundle.getString(Jobs.TITLE), bundle.getString(Jobs.COMMENT)
                                , bundle.getString(Jobs.PATH));

                        Toast.makeText(getApplicationContext(), "¡ Listo  !", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();


                break;

            case android.R.id.home:

                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();

            default:
                break;
        }
        return true;
    }

    @Override
    protected void setMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_new_photo, menu);
    }

}
