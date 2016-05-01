package idm.tpf.sinai.async;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Mar on 09/05/2016.
 */

public class QueryHandler extends AsyncQueryHandler {

    protected String TAG;

    public QueryHandler(ContentResolver cr) {
        super(cr);
        TAG=getClass().getCanonicalName();
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        Log.v("My Query handler", " Insercción completa ");
    }
    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        Log.v(TAG," Borrado completo del registro ");
    }
}