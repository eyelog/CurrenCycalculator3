package ru.eyelog.currencycalculator3.util_net;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class ValuteDTO {

    @Attribute
    private String ID;

    @Element
    private String NumCode;
    @Element
    private String CharCode;
    @Element
    private String Nominal;
    @Element
    private String Name;
    @Element
    private String Value;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumCode() {
        return NumCode;
    }

    public void setNumCode(String numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setNominal(String nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public ValuteTO getListTO(){
        return new ValuteTO(
                getID(),
                getNumCode(),
                getCharCode(),
                getNominal(),
                getName(),
                getValue());
    }
}
