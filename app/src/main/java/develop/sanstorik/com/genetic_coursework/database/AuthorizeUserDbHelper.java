package develop.sanstorik.com.genetic_coursework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class AuthorizeUserDbHelper extends SQLiteOpenHelper {
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + IndividualReaderContract.IndividualEntry.TABLE_NAME;
    private final static String CREATE_TABLE =
            "CREATE TABLE " + IndividualReaderContract.AuthorizeEntry.TABLE_NAME
                    + " (" + IndividualReaderContract.AuthorizeEntry._ID + " INTEGER PRIMARY KEY,"
                    + IndividualReaderContract.AuthorizeEntry.COLUMN_NAME_USERNAME + " TEXT,"
                    + IndividualReaderContract.AuthorizeEntry.COLUMN_NAME_PASSWORD + " TEXT )";

    AuthorizeUserDbHelper(Context context){
        super(context, IndividualReaderContract.AuthorizeEntry.TABLE_NAME, null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
