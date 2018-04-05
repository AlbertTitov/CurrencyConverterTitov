package newfarmstudio.currencyconverterbytitov.mvp.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import newfarmstudio.currencyconverterbytitov.mvp.CurrencyConverterMVP;
import newfarmstudio.currencyconverterbytitov.mvp.model.CurrenciesModel;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

public class CurrenciesPresenter implements CurrencyConverterMVP.Presenter {

    private static CurrencyConverterMVP.Presenter presenter;
    private CurrencyConverterMVP.Model model;
    private CurrencyConverterMVP.View view;

    private Map<String, Valute> currencies;

    private CurrenciesPresenter() {
        model = new CurrenciesModel();
    }

    /*
     * Получаем instance presenter'a на случай пересоздания activity
     * */
    public static CurrencyConverterMVP.Presenter getInstance() {
        if (presenter == null) {
            presenter = new CurrenciesPresenter();
        }
        return presenter;
    }

    @Override
    public void setView(CurrencyConverterMVP.View timerView) {
        this.view = timerView;
    }

    public void setModel(CurrencyConverterMVP.Model model) {
        this.model = model;
    }

    @Override
    public void convert() {

        /*
        * Расчёт суммы исходной и финальной валют в рублях с учётом номинала,
        * сконвертированного значения
        * */
        int initCurrencyNominal = currencies.get(view.getInitialSelectedCurrency()).getNominal();
        double initCurrencyCurs = Double.parseDouble(currencies.get(view.getInitialSelectedCurrency()).getValue().replace(",", "."));

        double initCurrencyInBaseValute = initCurrencyCurs/initCurrencyNominal;

        int finalCurrencyNominal = currencies.get(view.getFinalSelectedCurrency()).getNominal();
        double finalCurrencyCurs = Double.parseDouble(currencies.get(view.getFinalSelectedCurrency()).getValue().replace(",", "."));

        double finalCurrencyInBaseValute = finalCurrencyCurs/finalCurrencyNominal;

        double convertedValue = view.getInitialCurrencyAmount() * initCurrencyInBaseValute/finalCurrencyInBaseValute;

        view.showConvertedValue(String.format(Locale.getDefault(), "%.2f", convertedValue));
    }

    @Override
    public List<String> getCurrencyNames() {

        if (currencies != null) {

            List<String> names = new ArrayList<>(currencies.keySet());
            Collections.sort(names);
            return names;

        } else {

            view.showDataIsNotLoaded();
            return null;
        }
    }

    @Override
    public void loadData() {
        currencies = model.getCurrencies();
    }
}
