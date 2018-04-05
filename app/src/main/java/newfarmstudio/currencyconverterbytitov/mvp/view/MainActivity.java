package newfarmstudio.currencyconverterbytitov.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import newfarmstudio.currencyconverterbytitov.R;
import newfarmstudio.currencyconverterbytitov.mvp.CurrencyConverterMVP;
import newfarmstudio.currencyconverterbytitov.mvp.presenter.CurrenciesPresenter;

public class MainActivity extends AppCompatActivity implements CurrencyConverterMVP.View {

    EditText currencyAmount;
    Spinner initialCurrencySp;
    Spinner finalCurrencySp;
    Button convertBtn;
    TextView convertedValue;

    private List<String> currencyNames;

    private CurrencyConverterMVP.Presenter presenter;

    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = CurrenciesPresenter.getInstance();
        presenter.setView(this);
        presenter.loadData();
        currencyNames = presenter.getCurrencyNames();

        setUI();
    }

    @Override
    public void showConvertedValue(String value) {
        convertedValue.setText(value);
    }

    @Override
    public void showDataIsNotLoaded() {
        Toast.makeText(this, "Данные не были загружены",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public double getInitialCurrencyAmount() {
        return Double.parseDouble(currencyAmount.getText().toString());
    }

    @Override
    public String getInitialSelectedCurrency() {
        return (String) initialCurrencySp.getSelectedItem();
    }

    @Override
    public String getFinalSelectedCurrency() {
        return (String) finalCurrencySp.getSelectedItem();
    }

    private void setUI() {

        currencyAmount = findViewById(R.id.currency_amount);
        initialCurrencySp = findViewById(R.id.sp_initial_currency);
        finalCurrencySp = findViewById(R.id.sp_final_currency);
        convertBtn = findViewById(R.id.convert);
        convertedValue = findViewById(R.id.converted_value);

        initialCurrencySp.setPrompt("Исходная валюта");
        finalCurrencySp.setPrompt("Конечная валюта");

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        initialCurrencySp.setAdapter(spinnerAdapter);
        finalCurrencySp.setAdapter(spinnerAdapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.convert();
            }
        });
    }
}
