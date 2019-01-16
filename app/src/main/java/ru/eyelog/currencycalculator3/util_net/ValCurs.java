package ru.eyelog.currencycalculator3.util_net;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ValCurs {

    @Attribute
    private String Date;

    @Attribute
    private String name;

    @ElementList(entry="Valute", inline=true)
    private List<ValuteDTO> valuteDTOList;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ValuteDTO> getValuteDTOList() {
        return valuteDTOList;
    }

    public void setValuteDTOList(List<ValuteDTO> valuteDTOList) {
        this.valuteDTOList = valuteDTOList;
    }
}
