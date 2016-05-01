package idm.tpf.sinai.activity;

import android.content.Intent;
import android.view.View;

public class EditNewJob extends Edit {


    @Override
    public void onCancel(View view) {

        this.back();
        this.finish();
    }

    @Override
    protected void back() {

        Intent mainIntent=new Intent(this,MainActivity.class);
        startActivity(mainIntent);

    }
}
