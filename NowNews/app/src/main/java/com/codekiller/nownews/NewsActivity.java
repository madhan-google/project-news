package com.codekiller.nownews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codekiller.nownews.NewsModels.Articles;
import com.codekiller.nownews.Utils.SharedPrefs;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static com.codekiller.nownews.Utils.GlobalStrings.TEST_ID;
import static maes.tech.intentanim.CustomIntent.customType;

public class NewsActivity extends AppCompatActivity {
    public static final String TAG = "NEWS ACTIVITY";
    ImageView imageView, backBtn, favBtn, shareBtn, readBtn;
    TextView sourceView, titleView, publishView, authorView, contentView, descriptionView;
    Button fullNews;
    String object;
    SharedPrefs sharedPrefs;
    AdView adView;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        imageView = findViewById(R.id.image_view);
        sourceView = findViewById(R.id.source_view);
        titleView = findViewById(R.id.title_view);
        publishView = findViewById(R.id.publish_view);
        authorView = findViewById(R.id.author_view);
        contentView = findViewById(R.id.content_view);
        descriptionView = findViewById(R.id.description_view);
        fullNews = findViewById(R.id.full_news_btn);
        backBtn = findViewById(R.id.back_btn);
        favBtn = findViewById(R.id.favorite_btn);
        shareBtn = findViewById(R.id.share_btn);
        readBtn = findViewById(R.id.read_news);
        adView = findViewById(R.id.news_adview);

        object = getIntent().getStringExtra("object");
        Articles articles = new Gson().fromJson(object,Articles.class);
        sharedPrefs = new SharedPrefs(this);

        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(TEST_ID))
                .build());*/
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        Log.d(TAG, "onCreate: adrequest - "+adRequest);

        Glide.with(this)
                .load(articles.getUrlToImage())
                .into(imageView);
        /*sourceView.setText("Source : "+articles.getSource().getName());*/
        if( articles.getSource().getName() != null ){
            sourceView.setText(articles.getSource().getName());
        }else{
            sourceView.setText("Now News");
        }
        titleView.setText("Title : "+articles.getTitle());
        publishView.setText("Published At : "+articles.getPublishedAt());
        authorView.setText("Author : "+articles.getAuthor());
        contentView.setText("Content :\n"+articles.getContent());
        descriptionView.setText("Description :\n"+articles.getDescription());
        Log.d(TAG, "onCreate: url - "+articles.getUrl());

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if( status == TextToSpeech.SUCCESS ){
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        fullNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articles.getUrl())));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RotateOutUpLeft).duration(800).playOn(favBtn);
                ArrayList<String> arrayList = sharedPrefs.getList();
                if( arrayList.contains(object) ) {
                    toast("Already Exist !!");
                }else{
                    arrayList.add(object);
                    sharedPrefs.putList(arrayList);
                    toast("Added");
                }
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextToSpeech.speak(articles.getTitle(),TextToSpeech.QUEUE_FLUSH,null);
                tts.speak(articles.getTitle()+articles.getContent(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).duration(800).playOn(shareBtn);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,articles.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,articles.getUrl());
                startActivity(Intent.createChooser(intent,"News Link"));
            }
        });

    }
    public void toast(String s){
        Toast.makeText(NewsActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        customType(this,"left-to-right");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }
}