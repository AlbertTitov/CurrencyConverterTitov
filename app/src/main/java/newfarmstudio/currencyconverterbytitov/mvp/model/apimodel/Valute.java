package newfarmstudio.currencyconverterbytitov.mvp.model.apimodel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Альберт on 24.03.2018.
 */

@Root(name = "Valute")
public class Valute {

    @Attribute(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private int nominal;

    @Element(name = "Name")
    private String name;

    @Element(name = "Value")
    private String value;

    public Valute(String id, int numCode, String charCode, int nominal, String name, String value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public Valute() {
    }

    public String getId() {
        return id;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
