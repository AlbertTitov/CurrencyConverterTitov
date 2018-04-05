package newfarmstudio.currencyconverterbytitov.mvp.model.apimodel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Альберт on 24.03.2018.
 */

@Root(name = "ValCurs")
public class ValCurs {

    @ElementList(required=true, inline=true)
    private List<Valute> list;

    @Attribute(name = "Date")
    private String date;

    @Attribute(name = "name")
    private String name;

    public ValCurs() {
        list = new ArrayList<>();
    }

    public List<Valute>getList()
    {
        return this.list;
    }
}
