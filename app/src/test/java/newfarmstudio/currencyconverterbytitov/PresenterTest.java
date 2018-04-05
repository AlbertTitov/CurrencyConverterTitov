package newfarmstudio.currencyconverterbytitov;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import newfarmstudio.currencyconverterbytitov.mvp.CurrencyConverterMVP;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;
import newfarmstudio.currencyconverterbytitov.mvp.presenter.CurrenciesPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterTest {


    CurrencyConverterMVP.View mockView;
    CurrencyConverterMVP.Presenter presenter;
    CurrencyConverterMVP.Model mockModel;

    Map<String, Valute> currencies;

    @Before
    public void setup() {

        mockView = mock(CurrencyConverterMVP.View.class);
        mockModel = mock(CurrencyConverterMVP.Model.class);

        presenter = CurrenciesPresenter.getInstance();

        presenter.setView(mockView);
        presenter.setModel(mockModel);

        Valute euro = new Valute("R01239", 978, "EUR", 1, "Евро", "70,6038");
        Valute swissFrank = new Valute("R01775", 756, "CHF", 1, "Швейцарский франк", "60,0850");

        currencies = new HashMap<>();
        currencies.put(euro.getName(), euro);
        currencies.put(swissFrank.getName(), swissFrank);

    }

    @Test
    public void convertionIsCorrect() {

        when(mockModel.getCurrencies()).thenReturn(currencies);

        when(mockView.getInitialCurrencyAmount()).thenReturn(542d);
        when(mockView.getInitialSelectedCurrency()).thenReturn("Евро");
        when(mockView.getFinalSelectedCurrency()).thenReturn("Швейцарский франк");

        presenter.loadData();
        presenter.convert();

        verify(mockView, times(1)).showConvertedValue("636,89");
    }

}
