package newfarmstudio.currencyconverterbytitov.mvp.model;


import android.support.v4.util.ArrayMap;
import java.util.Map;
import newfarmstudio.currencyconverterbytitov.CurrenciesRepository;
import newfarmstudio.currencyconverterbytitov.mvp.CurrencyConverterMVP;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

public class CurrenciesModel implements CurrencyConverterMVP.Model {

    public CurrenciesModel() {
    }

    @Override
    public Map<String, Valute> getCurrencies() {

        Map<String, Valute> currencyMap = new ArrayMap<>();

        for (Valute valute : CurrenciesRepository.getInstance().getResultData()) {
            currencyMap.put(valute.getName(), valute);
        }

        return currencyMap;
    }
}
