package idm.tpf.sinai.strategies;

import android.content.Context;
import android.content.Intent;

import idm.tpf.sinai.activity.EditNewJob;
import idm.tpf.sinai.utils.ExifComments;

/**
 * Created by Mar on 02/05/2016.
 */
public class EditJobAction implements IAction {

    protected Context mCtx;

    public EditJobAction(Context ctx){
        this.mCtx=ctx;
    }

    @Override
    public void excecute(Object object) {

        String path=(String)object;
        Intent editIntent = new Intent(mCtx, EditNewJob.class);
        editIntent.putExtra("RECENT_IMAGE_FILE", path);
        this.mCtx.startActivity(editIntent);
    }
}
