package develop.sanstorik.com.genetic_coursework.database;

import android.provider.BaseColumns;

class IndividualReaderContract{
    private IndividualReaderContract(){}

    static abstract class IndividualEntry implements BaseColumns{
        static final String TABLE_NAME = "individuals";
        static final String COLUMN_NAME_GENES_BITS = "genesBits";
    }
}
