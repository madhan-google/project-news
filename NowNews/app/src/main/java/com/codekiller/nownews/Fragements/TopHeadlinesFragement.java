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
import com.codekiller.nownews.NewsModels.NewsAPI;
import com.codekiller.nownews.NewsModels.TopHeadlines;
import com.codekiller.nownews.R;
import com.codekiller.nownews.Utils.NewsFeeds;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codekiller.nownews.Utils.GlobalStrings.API_KEY;
import static com.codekiller.nownews.Utils.GlobalStrings.BASE_URL;


public class TopHeadlinesFragement extends Fragment {


    RecyclerView topHeadlineRecycler;
    ProgressBar progressBar;
    ArrayList<Articles> arrayList;
    ArticleRecycler articleRecycler;
    static Context context;


    public TopHeadlinesFragement(Context context) {
        // Required empty public constructor
        //newsFeeds = new NewsFeeds(getContext());

        arrayList = new ArrayList<>();
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_top_headlines_fragement, container, false);
        topHeadlineRecycler = v.findViewById(R.id.top_headline_recycler);
        progressBar = v.findViewById(R.id.progress_bar);
        topHeadlineRecycler.setLayoutManager(new LinearLayoutManager(context));
        topHeadlineRecycler.setHasFixedSize(true);
        Call<TopHeadlines> call = new NewsFeeds(context).getApi().getHeadlines(
                Locale.getDefault().getCountry(),
                API_KEY
        );
        call.enqueue(new Callback<TopHeadlines>() {
            @Override
            public void onResponse(Call<TopHeadlines> call, Response<TopHeadlines> response) {
                progressBar.setVisibility(View.GONE);
                arrayList = response.body().getArticles();
                articleRecycler = new ArticleRecycler(getContext(),arrayList);
                topHeadlineRecycler.setAdapter(articleRecycler);
            }

            @Override
            public void onFailure(Call<TopHeadlines> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                new NewsFeeds(context).toast("something went wrong\ncheck your internet connection");
            }
        });
        return v;
    }
}