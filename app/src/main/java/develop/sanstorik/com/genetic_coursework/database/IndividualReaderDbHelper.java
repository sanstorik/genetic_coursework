package develop.sanstorik.com.genetic_coursework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class IndividualReaderDbHelper extends SQLiteOpenHelper {
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + IndividualReaderContract.IndividualEntry.TABLE_NAME;
    private final static String CREATE_TABLE =
            "CREATE TABLE " + IndividualReaderContract.IndividualEntry.TABLE_NAME
                    + " (" + IndividualReaderContract.IndividualEntry._ID + " INTEGER PRIMARY KEY,"
            + IndividualReaderContract.IndividualEntry.COLUMN_NAME_GENES_BITS + " TEXT )";

    IndividualReaderDbHelper(Context context) {
        super(context, IndividualReaderContract.IndividualEntry.TABLE_NAME, null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
