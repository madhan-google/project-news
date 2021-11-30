package com.codekiller.nownews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codekiller.nownews.Adapters.ViewPageAdapter;
import com.codekiller.nownews.Fragements.BusinessFragement;
import com.codekiller.nownews.Fragements.CultureFragment;
import com.codekiller.nownews.Fragements.EnvironmentFragement;
import com.codekiller.nownews.Fragements.FashionFragement;
import com.codekiller.nownews.Fragements.ScienceFragement;
import com.codekiller.nownews.Fragements.SocietyFragement;
import com.codekiller.nownews.Fragements.SportsFragement;
import com.codekiller.nownews.Fragements.TopHeadlinesFragement;
import com.codekiller.nownews.Fragements.WorldFragement;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

import static com.codekiller.nownews.Utils.GlobalStrings.TEST_ID;
import static com.codekiller.nownews.Utils.GlobalStrings.business;
import static com.codekiller.nownews.Utils.GlobalStrings.culture;
import static com.codekiller.nownews.Utils.GlobalStrings.environment;
import static com.codekiller.nownews.Utils.GlobalStrings.fashion;
import static com.codekiller.nownews.Utils.GlobalStrings.headlines;
import static com.codekiller.nownews.Utils.GlobalStrings.science;
import static com.codekiller.nownews.Utils.GlobalStrings.society;
import static com.codekiller.nownews.Utils.GlobalStrings.sports;
import static com.codekiller.nownews.Utils.GlobalStrings.world;
import static maes.tech.intentanim.CustomIntent.customType;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "MAIN ACTIVITY";

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;
    AdView adView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        adView = findViewById(R.id.main_adview);

        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(TEST_ID))
                .build());*/
        AdRequest adRequest = new AdRequest.Builder().build();
        Log.d(TAG, "onCreate: adrequest - "+adRequest);
        adView.loadAd(adRequest);
        InterstitialAd.load(MainActivity.this, "ca-app-pub-5943796115602553/4138004994", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
            }
        });

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragements(
                new TopHeadlinesFragement(this),
                headlines
        );
        viewPageAdapter.addFragements(
                new ScienceFragement(this),
                science
        );
        viewPageAdapter.addFragements(
                new WorldFragement(this),
                world
        );
        viewPageAdapter.addFragements(
                new SportsFragement(this),
                sports
        );
        viewPageAdapter.addFragements(
                new EnvironmentFragement(this),
                environment
        );
        viewPageAdapter.addFragements(
                new SocietyFragement(this),
                society
        );
        viewPageAdapter.addFragements(
                new FashionFragement(this),
                fashion
        );
        viewPageAdapter.addFragements(
                new BusinessFragement(this),
                business
        );
        viewPageAdapter.addFragements(
                new CultureFragment(this),
                culture
        );
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.getItemId() == R.id.favorites ) {
            startActivity(new Intent(this, FavoritesActivity.class));
            customType(this,"up-to-bottom");
        }else if( item.getItemId() == R.id.about ){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://indexbound.blogspot.com/p/now-news-about.html")));
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if( mInterstitialAd != null ){
            mInterstitialAd.show(MainActivity.this);
        }
    }
}