package develop.sanstorik.com.genetic_coursework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import develop.sanstorik.com.genetic_coursework.genetic.Individual;

import static develop.sanstorik.com.genetic_coursework.database.IndividualReaderContract.IndividualEntry;

public class IndividualDatabase {
    public enum SQLmode{
        WRITE, READ
    }

    private IndividualReaderDbHelper readerContract;
    private SQLiteDatabase database;
    private SQLmode mode;

    private IndividualDatabase(Context context, SQLmode mode){
        this.readerContract = new IndividualReaderDbHelper(context);
        this.mode = mode;

        if (mode == SQLmode.WRITE)
            database = readerContract.getWritableDatabase();
        else
            database = readerContract.getReadableDatabase();
    }

    public void insertIndividual(Individual individual){
        if(mode == SQLmode.READ)
            throw new IllegalStateException("wrong DB type");

        ContentValues values = new ContentValues();
        values.put(IndividualEntry.COLUMN_NAME_GENES_BITS, individual.getBits());
        database.insert(IndividualEntry.TABLE_NAME, "null", values);

        Log.i("tag", "inserted");
    }

    public void readIndividuals(){
        if(mode == SQLmode.WRITE)
            throw new IllegalStateException("wrong DB type");

        String[] columns = {
                IndividualEntry._ID,
                IndividualEntry.COLUMN_NAME_GENES_BITS
        };

        Cursor cursor = database.query(IndividualEntry.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();

        String genesVal = cursor.getString(
                cursor.getColumnIndexOrThrow(IndividualEntry.COLUMN_NAME_GENES_BITS)
        );
        Log.i("tag", genesVal);

        cursor.close();
    }

    public static IndividualDatabase connection(Context context, SQLmode mode){
        return new IndividualDatabase(context, mode);
    }
}
