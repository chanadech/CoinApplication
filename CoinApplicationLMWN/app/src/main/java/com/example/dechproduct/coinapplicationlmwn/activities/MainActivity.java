package com.example.dechproduct.coinapplicationlmwn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dechproduct.coinapplicationlmwn.R;
import com.example.dechproduct.coinapplicationlmwn.adapters.CoinAdapter;
import com.example.dechproduct.coinapplicationlmwn.models.CoinResponse;
import com.example.dechproduct.coinapplicationlmwn.models.CoinItem;
import com.example.dechproduct.coinapplicationlmwn.network.ApiManager;
import com.example.dechproduct.coinapplicationlmwn.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class MainActivity extends AppCompatActivity {


    ApiService apiService;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<CoinItem> list = new ArrayList<CoinItem>();

    private boolean appChecked;
    private static final String TAG = "COINAPI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiManager.getClient().create(ApiService.class);
        recyclerView = findViewById(R.id.recyclerView);
        Call<CoinResponse> call= apiService.getCoins();

        call.enqueue(new Callback<CoinResponse>() {
            @Override
            public void onResponse(Call<CoinResponse> call, Response<CoinResponse> response) {
                appChecked = true;

                if (response.isSuccessful()) {
                CoinResponse coinList = response.body();
                for (int i = 0; i < coinList.getData().getCoins().size(); i++) {
                    CoinItem item = new CoinItem();
                    item.setId(coinList.getData().getCoins().get(i).getId());
                    item.setSymbol(coinList.getData().getCoins().get(i).getSymbol());
                    item.setName(coinList.getData().getCoins().get(i).getName());
                    item.setDescription(coinList.getData().getCoins().get(i).getDescription());
                    item.setIconUrl(coinList.getData().getCoins().get(i).getIconUrl());
                    item.setPrice(coinList.getData().getCoins().get(i).getPrice());
                    item.setColor(coinList.getData().getCoins().get(i).getColor());
                    item.setWebUrl(coinList.getData().getCoins().get(i).getWebsiteUrl());

                    list.add(item);
                }
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new CoinAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }else {
                    Log.e(TAG,"onResponse:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CoinResponse> call, Throwable t) { }
        });
    }
}