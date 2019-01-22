package ru.eyelog.currencycalculator3.util_net;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("XML_daily.asp")
    Observable<ValCurs> getCurrency();
}