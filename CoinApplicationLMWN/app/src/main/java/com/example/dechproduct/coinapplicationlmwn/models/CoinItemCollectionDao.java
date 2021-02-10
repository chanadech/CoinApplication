package com.example.dechproduct.coinapplicationlmwn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoinItemCollectionDao {
    @SerializedName("coins")
    @Expose
    private List<CoinItemDao> coins = null;

    public List<CoinItemDao> getCoins() {
        return coins;
    }
}
