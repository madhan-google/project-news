package com.codekiller.nownews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codekiller.nownews.NewsActivity;
import com.codekiller.nownews.NewsModels.Articles;
import com.codekiller.nownews.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;

    public FavoriteRecyclerAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.favorite_recycer,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles a = new Gson().fromJson(arrayList.get(position),Articles.class);
        holder.textView.setText(a.getTitle());
        Glide.with(context)
                .load(a.getUrlToImage())
                .centerCrop()
                .into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, NewsActivity.class)
                .putExtra("object",arrayList.get(position)));
                customType(context,"right-to-left");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
