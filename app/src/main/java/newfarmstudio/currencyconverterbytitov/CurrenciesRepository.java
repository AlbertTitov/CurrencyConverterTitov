package newfarmstudio.currencyconverterbytitov;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import newfarmstudio.currencyconverterbytitov.database.DBHelper;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.ValCurs;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

/**
 * Created by Альберт on 25.03.2018.
 */

public class CurrenciesRepository implements Repository {

    private DBHelper dbHelper;
    private ValCurs valCurs;

    private static Repository repository;

    private CurrenciesRepository() {
        dbHelper = new DBHelper(MyApplication.getAppContext());
    }

    @Override
    public ValCurs getResultsFromNetwork() {

        Thread getNetworkResult = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(ApiConstants.BASE_URL);
                    InputStream input = url.openStream();

                    Serializer ser = new Persister();
                    valCurs = ser.read(ValCurs.class, input);

                    if (valCurs != null) {

                        Thread saveResultToDb = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (dbHelper.getDbIsEmpty()) {
                                    dbHelper.saveCurrencies(valCurs.getList());
                                }
                                else {
                                    dbHelper.getUpdateManager().updateCurrencies(valCurs.getList());
                                }
                            }
                        });

                        saveResultToDb.start();
                    }

                    input.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getNetworkResult.start();
        try {
            getNetworkResult.join(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return valCurs;
    }

    @Override
    public List<Valute> getResultsFromMemory() {
        return dbHelper.getCurrencies();
    }

    @Override
    public List<Valute> getResultData() {

        getResultsFromNetwork();

        if (valCurs == null) {

            return getResultsFromMemory();
        }
        else {

            return valCurs.getList();
        }
    }

    public static Repository getInstance() {

        if (repository == null) {
            repository = new CurrenciesRepository();
        }
        return repository;
    }
}
