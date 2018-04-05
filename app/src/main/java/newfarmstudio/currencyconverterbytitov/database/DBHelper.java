package newfarmstudio.currencyconverterbytitov.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "currency_converter_database";
    public static final String CURRENCY_ITEMS_TABLE = "currency_items_table";

    public static final String CURRENCY_ID = "currency_id";
    public static final String NUM_CODE = "num_code";
    public static final String CHAR_CODE = "char_code";
    public static final String NOMINAL = "nominal";
    public static final String NAME = "name";
    public static final String VALUE = "value";

    private DBUpdateManager updateManager;

    private static final String CURRENCY_ITEMS_TABLE_CREATE_SCRIPT = "CREATE TABLE "
            + CURRENCY_ITEMS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CURRENCY_ID + " TEXT NOT NULL, "
            + NUM_CODE + " INTEGER, "
            + CHAR_CODE + " TEXT NOT NULL, "
            + NOMINAL + " INTEGER, "
            + NAME + " TEXT NOT NULL, "
            + VALUE + " TEXT NOT NULL);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null  , DATABASE_VERSION);
        updateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CURRENCY_ITEMS_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + CURRENCY_ITEMS_TABLE);
        onCreate(db);
    }

    private void saveCurrency(Valute valute) {

        ContentValues newValues = new ContentValues();

        newValues.put(CURRENCY_ID, valute.getId());
        newValues.put(NUM_CODE, valute.getNumCode());
        newValues.put(CHAR_CODE, valute.getCharCode());
        newValues.put(NOMINAL, valute.getNominal());
        newValues.put(NAME, valute.getName());
        newValues.put(VALUE, valute.getValue());

        getWritableDatabase().insert(CURRENCY_ITEMS_TABLE, null, newValues);
    }

    public void saveCurrencies(List<Valute> currencies) {
        for (Valute valute : currencies) {
            saveCurrency(valute);
        }
    }

    public List<Valute> getCurrencies() {

        List<Valute> currencies = new ArrayList<>();

        Cursor c = getReadableDatabase().query(CURRENCY_ITEMS_TABLE, null,
                null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                String currencyId = c.getString(c.getColumnIndex(CURRENCY_ID));
                int numCode = c.getInt(c.getColumnIndex(NUM_CODE));
                String charCode = c.getString(c.getColumnIndex(CHAR_CODE));
                int nominal = c.getInt(c.getColumnIndex(NOMINAL));
                String name = c.getString(c.getColumnIndex(NAME));
                String value = c.getString(c.getColumnIndex(VALUE));

                Valute valute = new Valute(currencyId, numCode, charCode, nominal, name, value);
                currencies.add(valute);

            } while (c.moveToNext());
        }
        c.close();

        return currencies;
    }

    public boolean getDbIsEmpty() {

        Cursor c = getReadableDatabase().query(CURRENCY_ITEMS_TABLE, null,
                null, null, null, null, null);

        if (c.moveToFirst()) {
            return false;
        }
        else {
            return true;
        }
    }

    public DBUpdateManager getUpdateManager() {
        return updateManager;
    }
}
