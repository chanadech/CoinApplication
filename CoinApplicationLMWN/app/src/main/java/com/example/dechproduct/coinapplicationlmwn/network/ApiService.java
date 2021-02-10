package com.example.dechproduct.coinapplicationlmwn.network;
import com.example.dechproduct.coinapplicationlmwn.models.CoinResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("v1/public/coins/")
    Call<CoinResponse> getCoins();
}
