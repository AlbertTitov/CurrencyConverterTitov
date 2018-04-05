package newfarmstudio.currencyconverterbytitov.mvp;

import java.util.List;
import java.util.Map;

import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

/**
 * Created by Альберт on 25.03.2018.
 */

public interface CurrencyConverterMVP {

    interface View {

        void showConvertedValue(String value);

        void showDataIsNotLoaded();

        double getInitialCurrencyAmount();

        String getInitialSelectedCurrency();

        String getFinalSelectedCurrency();
    }

    interface Presenter {

        void setView(CurrencyConverterMVP.View timerView);

        void convert();

        List<String> getCurrencyNames();

        void loadData();

        void setModel(CurrencyConverterMVP.Model model);
    }

    interface Model {

        Map<String, Valute> getCurrencies();
    }
}
