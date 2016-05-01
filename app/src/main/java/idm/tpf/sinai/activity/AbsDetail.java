package idm.tpf.sinai.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import idm.tpf.sinai.R;
import idm.tpf.sinai.providers.Jobs;
import idm.tpf.sinai.utils.Utils;

public abstract class AbsDetail extends AppCompatActivity {

    protected Bundle bundle;
    protected ActionBar actionBar;
    public static String TAG;

   @Override
    protected void onCreate(Bundle savedInstanceState) {

        TAG = getClass().getCanonicalName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.show();

        bundle = getIntent().getExtras();

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) findViewById(R.id.ivSnapshot);

        // esto anda muy bien pero me carga la imagen rotada.
        //imageView.setImageBitmap(BitmapFactory.decodeFile(bundle.getString(Jobs.PATH, "")));
        imageView.setImageBitmap(Utils.getImageCorrectly(bundle.getString(Jobs.PATH, "")));

        tvDescription.setText(bundle.getString(Jobs.COMMENT, ""));

    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        setMenu(menu);
        actionBar.setTitle(bundle.getString(Jobs.TITLE, " Sin título"));
        actionBar.setSubtitle(bundle.getString(Jobs.DATE, ""));
        return true;
    }

    protected abstract void setMenu(Menu menu);
    //getMenuInflater().inflate(mMenu, menu);

}
