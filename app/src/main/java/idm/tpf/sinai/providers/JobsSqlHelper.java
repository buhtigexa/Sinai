package idm.tpf.sinai.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mar on 04/05/2016.
 */

public class JobsSqlHelper extends SQLiteOpenHelper {

    static final String KEY_ROWID = "_id";
    static final String DATE="date";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    static final String TITLE = "title";
    static final String COMMENT = "comment";
    static final String PATH= "path";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "JOBS_DATABASE";
    static final String DATABASE_TABLE = "jobs";
    static final int DATABASE_VERSION = 1;

    static public final String DATABASE_CREATE = "create table  if not exists " + DATABASE_TABLE  +
            " ( _id integer primary key autoincrement, " +
            DATE + "  text not null ,"  +
            LATITUDE  + " double not null , " +
            LONGITUDE + " double not null , " +
            TITLE + " text not null , " +
            COMMENT + " text not null , " +
            PATH +  " text not null ) ";

    public JobsSqlHelper(Context contexto, String nombre,
                                SQLiteDatabase.CursorFactory factory, int version) {

        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(DATABASE_CREATE);

        //Insertamos 15 clientes de ejemplo

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS  " + DATABASE_TABLE);

        //Se crea la nueva versión de la tabla
        db.execSQL(DATABASE_CREATE);
    }
}