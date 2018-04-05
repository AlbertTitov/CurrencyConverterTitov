package newfarmstudio.currencyconverterbytitov.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;
import static newfarmstudio.currencyconverterbytitov.database.DBHelper.CURRENCY_ID;

public class DBUpdateManager {

    SQLiteDatabase database;

    DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    private void updateValue(String currencyId, String value) {
        update(DBHelper.VALUE, currencyId, value);
    }

    private void updateNominal(String currencyId, int value) {
        update(DBHelper.NOMINAL, currencyId, value);
    }

    private void updateName(String currencyId, String value) {
        update(DBHelper.NAME, currencyId, value);
    }

    private void updateNumCode(String currencyId, int value) {
        update(DBHelper.NUM_CODE, currencyId, value);
    }

    private void updateCharCode(String currencyId, String value) {
        update(DBHelper.NUM_CODE, currencyId, value);
    }

    private void updateCurrency(Valute valute) {

        /*
        * Теоретически может сложиться ситуация, когда меняется номинал - например, деноминация в стране,
        * или же введена другая национальная валюта - евро вместо румынского лея,
        * следовательно поля nominal, name, numCode, charCode необходимо будет обновить
        * */
        updateNominal(valute.getId(), valute.getNominal());
        updateName(valute.getId(), valute.getName());
        updateNumCode(valute.getId(), valute.getNumCode());
        updateCharCode(valute.getId(), valute.getCharCode());

        updateValue(valute.getId(), valute.getValue());
    }

    public  void updateCurrencies(List<Valute> currencies) {
        for (Valute valute : currencies) {
            updateCurrency(valute);
        }
    }

    private void update(String column, String currencyId, String value) {

        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CURRENCY_ITEMS_TABLE, cv, CURRENCY_ID + " = ?", new String[] {currencyId});
    }

    private void update(String column, String currencyId, int value) {

        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CURRENCY_ITEMS_TABLE, cv, CURRENCY_ID + " = ?", new String[] {currencyId});
    }
}
