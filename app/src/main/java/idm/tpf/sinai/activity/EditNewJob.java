package idm.tpf.sinai.activity;

import android.content.Intent;
import android.view.View;

public class EditNewJob extends Edit {


    @Override
    public void onCancel(View view) {

        this.back();

    }

    @Override
    protected void back() {

        this.finish();
        Intent mainIntent=new Intent(this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);

    }
}
