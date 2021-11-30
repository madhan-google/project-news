package com.codekiller.nownews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codekiller.nownews.NewsActivity;
import com.codekiller.nownews.NewsModels.Articles;
import com.codekiller.nownews.R;
import com.codekiller.nownews.Utils.NewsFeeds;
import com.codekiller.nownews.Utils.SharedPrefs;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class ArticleRecycler extends RecyclerView.Adapter<ArticleRecycler.ViewHolder> {
    Context context;
    ArrayList<Articles> articles;
    SharedPrefs sharedPrefs;
    ArrayList<String> favObjs;
    public static String TAG = "ARTICLE RECYCLER";

    public ArticleRecycler() {
    }

    public ArticleRecycler(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
        sharedPrefs = new SharedPrefs(context);
        favObjs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.news_item,
                        parent,
                        false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles a = articles.get(position);
        String obj = new Gson().toJson(a);
        Log.d(TAG, "onBindViewHolder: "+a.getAuthor()+" - "+a.getUrlToImage());
        holder.titleView.setText(a.getTitle());
        Glide.with(context)
                .load(a.getUrlToImage())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutUp).duration(800).playOn(holder.favoriteBtn);
                favObjs = sharedPrefs.getList();
                if( favObjs.contains(obj) ){
                    new NewsFeeds(context).toast("Already Exist !!");
                }else{
                    favObjs.add(obj);
                    sharedPrefs.putList(favObjs);
                    new NewsFeeds(context).toast("Added");
                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //YoYo.with(Techniques.FlipOutY).duration(800).playOn(holder.imageView);
                context.startActivity(new Intent(context, NewsActivity.class)
                        .putExtra("object",obj));
                customType(context,"right-to-left");
            }
        });
        holder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        CircleImageView favoriteBtn;
        TextView titleView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_view);
            progressBar = itemView.findViewById(R.id.progress_bar);
            favoriteBtn = itemView.findViewById(R.id.favorite_btn);
        }
    }
}
