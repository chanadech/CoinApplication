package com.example.dechproduct.coinapplicationlmwn.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CoinResponse {

    @SerializedName("data")
    @Expose
    private CoinItemCollectionDao data;

    public CoinItemCollectionDao getData() {
        return data;
    }

    public void setData(CoinItemCollectionDao data) {
        this.data = data;
    }
}
