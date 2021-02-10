package com.example.dechproduct.coinapplicationlmwn.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.dechproduct.coinapplicationlmwn.activities.CoinDetailActivity;
import com.example.dechproduct.coinapplicationlmwn.R;
import com.example.dechproduct.coinapplicationlmwn.models.CoinItem;
import com.bumptech.glide.GenericRequestBuilder;


import java.io.InputStream;
import java.util.List;


public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {
    private Context context;
    private List<CoinItem> items;

    public CoinAdapter(Context context, List items){
        this.context = context;
        this.items =items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(items.get(position));

        CoinItem item = items.get(position);
        holder.coinNameTv.setText(item.getName());
        setIconImageView(item,holder);
        holder.descriptionTv.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView coinNameTv;
        public ImageView iconImageView;
        public TextView descriptionTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coinNameTv = itemView.findViewById(R.id.coinNames);
            iconImageView = itemView.findViewById(R.id.coinIcons);
            descriptionTv = itemView.findViewById(R.id.coinDescriptions);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        //optional
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CoinDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", items.get(getAdapterPosition()));
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public void setIconImageView(CoinItem item, ViewHolder holder){
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>
                requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(item.getIconUrl());
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(holder.iconImageView);
    }
}
