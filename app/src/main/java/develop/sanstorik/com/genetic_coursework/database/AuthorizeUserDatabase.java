package develop.sanstorik.com.genetic_coursework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static develop.sanstorik.com.genetic_coursework.database.IndividualReaderContract.AuthorizeEntry;

public class AuthorizeUserDatabase {
    private final AuthorizeUserDbHelper readerContract;
    private final SQLiteDatabase database;
    private final IndividualDatabase.SQLmode mode;


    private AuthorizeUserDatabase(Context context, IndividualDatabase.SQLmode mode){
        readerContract = new AuthorizeUserDbHelper(context);
        this.mode = mode;

        if(mode == IndividualDatabase.SQLmode.READ)
            database = readerContract.getReadableDatabase();
        else
            database = readerContract.getWritableDatabase();
    }

    public void registerUser(String userName, String password){
        if(mode == IndividualDatabase.SQLmode.READ)
            throw new IllegalStateException("wrong DB type");

        ContentValues contentValues = new ContentValues();
        contentValues.put(AuthorizeEntry.COLUMN_NAME_USERNAME, userName);
        contentValues.put(AuthorizeEntry.COLUMN_NAME_PASSWORD, password);

        database.insert(AuthorizeEntry.TABLE_NAME,
                null, contentValues);
    }

    public boolean userIsValid(String userName, String password){
        String[] columns = {AuthorizeEntry.COLUMN_NAME_USERNAME, AuthorizeEntry.COLUMN_NAME_PASSWORD};
        String whereClause = AuthorizeEntry.COLUMN_NAME_USERNAME + "='" + userName + "' AND " +
                AuthorizeEntry.COLUMN_NAME_PASSWORD + "='" + password + "'";

        Cursor cursor = database.query(AuthorizeEntry.TABLE_NAME, columns, whereClause, null, null, null, null);

        boolean isValid = cursor.moveToFirst();
        cursor.close();

        return isValid;
    }


    public static AuthorizeUserDatabase connection(Context context, IndividualDatabase.SQLmode mode){
        return new AuthorizeUserDatabase(context, mode);
    }
}
