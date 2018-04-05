package newfarmstudio.currencyconverterbytitov;

import java.util.List;

import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.ValCurs;
import newfarmstudio.currencyconverterbytitov.mvp.model.apimodel.Valute;

/**
 * Created by Альберт on 25.03.2018.
 */

public interface Repository {

    ValCurs getResultsFromNetwork();
    List<Valute> getResultsFromMemory();
    List<Valute> getResultData();
}
