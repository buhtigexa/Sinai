package idm.tpf.sinai.strategies;

import android.content.Context;


import idm.tpf.sinai.utils.Exif;
import idm.tpf.sinai.utils.ExifComments;
import idm.tpf.sinai.utils.Utils;

/**
 * Created by Mar on 02/05/2016.
 */
public class SinaiFileContext {


    public boolean isSinaiFile;
    Exif exif;
    protected Context mCtx;
    protected String path;

    public SinaiFileContext(Context ctx,String path) {

        isSinaiFile = Utils.isSinaiFile(path);
        exif=new ExifComments(path);
        mCtx=ctx;
        this.path=path;
    }

    public  void doAction() {

        IAction action=null;
        if (isSinaiFile) {
            action=new AddJobAction(mCtx);
            action.excecute(exif);
        } else {
            action=new EditJobAction(mCtx);
            action.excecute(new String(path));
        }
    }

}
