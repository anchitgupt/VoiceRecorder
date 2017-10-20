package voicerecorder.ateam.com.voicerecorder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;

import java.util.Date;

/**
 * Created by apple on 20/10/17.
 */

public class SqlLite extends SQLiteOpenHelper{

    // TODO: 20/10/17 to optimize and coorect the static variables used in variables 
    public static final String DATABASE_NAME = "voice.db";
    private static final String TABLE_NAME = "AUDIO";
    private static final String ID ="ID AUTO INCREMENT PRIMARY KEY";
    private static final String NAME ="NAME TEXT";
    private static final String LENGTH ="LENGTH INTEGER";
    private static final String LOCATION = "LOC TEXT";
    private static final String DATES = "DATES DATE ";
    public SqlLite(Context context) {
        super(context,DATABASE_NAME,null,1);
    }


    public SqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //// TODO: 20/10/17 Correct import of static variables 
        sqLiteDatabase.execSQL("CREATE TABLE"+
                "IF NOT EXIST"+
                TABLE_NAME+
                "("+
                ID+","+
                NAME+","+
                LENGTH+","+
                LOCATION+","+
                DATES+")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name, long len, String loc, Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //// TODO: 20/10/17 insert the values in cv variable of function arguments 
        return true;
    }
}
