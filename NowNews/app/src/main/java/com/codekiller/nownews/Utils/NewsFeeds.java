package com.codekiller.nownews.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.codekiller.nownews.MainActivity;
import com.codekiller.nownews.NewsModels.Articles;
import com.codekiller.nownews.NewsModels.Everything;
import com.codekiller.nownews.NewsModels.NewsAPI;
import com.codekiller.nownews.NewsModels.TopHeadlines;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codekiller.nownews.Utils.GlobalStrings.API_KEY;
import static com.codekiller.nownews.Utils.GlobalStrings.BASE_URL;
import static com.codekiller.nownews.Utils.GlobalStrings.ERROR;
import static com.codekiller.nownews.Utils.GlobalStrings.business;
import static com.codekiller.nownews.Utils.GlobalStrings.culture;
import static com.codekiller.nownews.Utils.GlobalStrings.environment;
import static com.codekiller.nownews.Utils.GlobalStrings.fashion;
import static com.codekiller.nownews.Utils.GlobalStrings.headlines;
import static com.codekiller.nownews.Utils.GlobalStrings.science;
import static com.codekiller.nownews.Utils.GlobalStrings.society;
import static com.codekiller.nownews.Utils.GlobalStrings.sports;
import static com.codekiller.nownews.Utils.GlobalStrings.world;


public class NewsFeeds {
    Retrofit retrofit;
    NewsAPI newsAPI;
    public static String TAG = "NewsFeeds";
    Context context;
    //RxJavaCallAdapterFactory rxAdapter;

    public NewsFeeds(Context context) {
        //ma = new MainActivity();
        this.context = context;
        //rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(rxAdapter)
                .build();
        newsAPI = retrofit.create(NewsAPI.class);

    }

    public void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public NewsAPI getApi(){
        return newsAPI;
    }

    /*private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        *//*okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return isInternetAvailable();
            }

            @Override
            public void onInternetUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onInternetUnavailable();
                }
            }
        });*//*

        return okhttpClientBuilder.build();
    }*/

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
