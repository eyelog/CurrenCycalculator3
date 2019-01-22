package ru.eyelog.currencycalculator3.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.eyelog.currencycalculator3.util_net.ApiService;
import ru.eyelog.currencycalculator3.util_net.AppNetConnector;
import ru.eyelog.currencycalculator3.util_net.ValCurs;
import ru.eyelog.currencycalculator3.util_net.ValuteTO;
import ru.eyelog.currencycalculator3.views.ViewStateNet;

@InjectViewState
public class PresenterNet extends MvpPresenter<ViewStateNet> {

    ApiService apiService;

    List<ValuteTO> data;

    public PresenterNet() {
        apiService = AppNetConnector.getApiService();
    }

    @SuppressLint("CheckResult")
    public void getCurrencyList() {

        // Using RxAndroid
        Observable<ValCurs> getValCurs = apiService.getCurrency();
        getValCurs.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valCurs -> {
                    data = new ArrayList<>();
                    for (int i = 0; i < valCurs.getValuteDTOList().size(); i++) {
                        data.add(valCurs.getValuteDTOList().get(i).getListTO());
                    }
                    getViewState().setCurses(data);
                });

//        Call<ValCurs> getValCurs = apiService.getCurrency();
//        getValCurs.enqueue(new Callback<ValCurs>() {
//            @Override
//            public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {
//                if(response.isSuccessful()){
//                    System.out.println("Logcat: " + "Successful");
//                    if(response.body()!=null){
//                        System.out.println("Logcat: " + "response.body()!=null");
//                        ValCurs valCurs = response.body();
//                        data = new ArrayList<>();
//                        for (int i = 0; i < valCurs.getValuteDTOList().size(); i++) {
//                            data.add(valCurs.getValuteDTOList().get(i).getListTO());
//                        }
//
//                        getViewState().setCurses(data);
//
//                    }else {
//                        System.out.println("Logcat: " + "response.body()==null");
//                    }
//                }else {
//                    System.out.println("Logcat: " + "UnSuccessful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ValCurs> call, Throwable t) {
//                System.out.println("Logcat: " + "Failure");
//                System.out.println("Logcat: " + "Throwable " + t);
//            }
//        });
    }
}
