package ru.eyelog.currencycalculator3.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.eyelog.currencycalculator3.util_net.ValuteTO;
import ru.eyelog.currencycalculator3.views.ViewStateCount;

@InjectViewState
public class PresenterCount extends MvpPresenter<ViewStateCount> {

    private double curFrom, curTo;
    private String stOut;

    public PresenterCount() {
    }

    public void getOutLine(ValuteTO valuteFrom, ValuteTO valuteTo, String value){

        curFrom = getDouble(valuteFrom.getNominal()) *
                getDouble(value);

        curTo = getDouble(valuteTo.getNominal()) *
                getDouble(valuteTo.getValue());

        // TODO badcode todo it good!
        stOut = value + " " + valuteFrom.getName() + " = " + curFrom / curTo + " " + valuteTo.getName();

        getViewState().setOutLine(stOut);
    }

    private double getDouble(String gotSt) {
        String stParsed = "";

        for (int i = 0; i < gotSt.length(); i++) {
            if (!gotSt.substring(i, i + 1).equals("-")) {
                stParsed += (gotSt.substring(i, i + 1).equals(",") ? "." : gotSt.substring(i, i + 1));
            }
        }

        return Double.parseDouble(stParsed);
    }
}
