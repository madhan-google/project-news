package com.codekiller.nownews.Fragements;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codekiller.nownews.Adapters.ArticleRecycler;
import com.codekiller.nownews.NewsModels.Articles;
import com.codekiller.nownews.NewsModels.Everything;
import com.codekiller.nownews.R;
import com.codekiller.nownews.Utils.NewsFeeds;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codekiller.nownews.Utils.GlobalStrings.API_KEY;
import static com.codekiller.nownews.Utils.GlobalStrings.science;


public class ScienceFragement extends Fragment {

    static Context context;
    ArrayList<Articles> articles;
    RecyclerView recyclerView;
    ArticleRecycler articleRecycler;
    ProgressBar progressBar;

    public ScienceFragement(Context context) {
        // Required empty public constructor
        this.context = context;
        articles = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_science_fragement, container, false);
        progressBar = v.findViewById(R.id.progress_bar);
        recyclerView = v.findViewById(R.id.science_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        Call<Everything> call = new NewsFeeds(context).getApi().getEverything(
                science,
                API_KEY
        );
        call.enqueue(new Callback<Everything>() {
            @Override
            public void onResponse(Call<Everything> call, Response<Everything> response) {
                progressBar.setVisibility(View.GONE);
                articles = response.body().getArticles();
                articleRecycler = new ArticleRecycler(context,articles);
                recyclerView.setAdapter(articleRecycler);
            }

            @Override
            public void onFailure(Call<Everything> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                new NewsFeeds(context).toast("something went wrong\ncheck your internet connection");
            }
        });
        return v;
    }
}