package idm.tpf.sinai.activity;

import android.view.View;

public class EditCurrentJob extends Edit {



    @Override
    public void onCancel(View view) {

        finish();

    }

    @Override
    protected void back() {

        finish();
    }

}
