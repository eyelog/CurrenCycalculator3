package ru.eyelog.currencycalculator3.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.eyelog.currencycalculator3.util_net.ValuteTO;

public interface ViewStateNet extends MvpView {
    void setCurses(List<ValuteTO> valuteTOS);
}
