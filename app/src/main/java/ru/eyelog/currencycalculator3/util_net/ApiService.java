package ru.eyelog.currencycalculator3.util_net;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("XML_daily.asp")
    Call<ValCurs> getCurrency();
}