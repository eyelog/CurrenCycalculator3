package ru.eyelog.currencycalculator3;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.eyelog.currencycalculator3.util_net.ValuteTO;

public interface ViewState extends MvpView {
    void setCurses(List<ValuteTO> valuteTOS);
}
