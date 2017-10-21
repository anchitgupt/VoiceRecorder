package voicerecorder.ateam.com.voicerecorder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;
import android.widget.Toast;

import voicerecorder.ateam.com.voicerecorder.Database.VoiceContract.VoiceEntry;
import java.util.Date;

/**
 * Created by apple on 20/10/17.
 */

public class SqlLite extends SQLiteOpenHelper{

    // TODO: 20/10/17 to optimize and coorect the static variables used in variables 
    public static final String DATABASE_NAME = "voice.db";

    public static final int DATABSE_VERSION = 1;
    Context cxt;
    public SqlLite(Context context) {
        super(context,DATABASE_NAME,null,DATABSE_VERSION);
        this.cxt =context;
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
        sqLiteDatabase.execSQL("CREATE TABLE " + VoiceEntry.TABLE_NAME + "("
                +VoiceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                VoiceEntry.NAME + " TEXT NOT NULL, "+
                VoiceEntry.LENGTH + " INTEGER NOT NULL, "+
                VoiceEntry.LOCATION + " TEXT, "+
                VoiceEntry.DATES + " Text);"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name, long len, String loc, Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // TODO: 20/10/17 insert the values in cv variable of function arguments
        cv.put(VoiceEntry.NAME, name);
        cv.put(VoiceEntry.LENGTH, len);
        cv.put(VoiceEntry.LOCATION, loc);
        cv.put(VoiceEntry.DATES, String.valueOf(date));

        long l = db.insert(VoiceEntry.TABLE_NAME,null,cv);
        if(l == -1)
            Toast.makeText(cxt, "DataBase NOT Updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(cxt, "Database Updated", Toast.LENGTH_SHORT).show();
        return true;
    }



}
