package com.codekiller.nownews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.codekiller.nownews.Adapters.FavoriteRecyclerAdapter;
import com.codekiller.nownews.Utils.NewsFeeds;
import com.codekiller.nownews.Utils.SharedPrefs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.codekiller.nownews.Utils.GlobalStrings.TEST_ID;
import static maes.tech.intentanim.CustomIntent.customType;

public class FavoritesActivity extends AppCompatActivity {
    public static final String TAG = "FAVORITE ACTIVITY";

    RecyclerView recyclerView;
    ImageView imageView;
    ArrayList<String> arrayList;
    Toolbar toolbar;
    SharedPrefs sharedPrefs;
    FavoriteRecyclerAdapter favoriteRecyclerAdapter;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        recyclerView = findViewById(R.id.recycler_view);
        imageView = findViewById(R.id.back_btn);
        adView = findViewById(R.id.fav_adview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        sharedPrefs = new SharedPrefs(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrayList = sharedPrefs.getList();
        if( arrayList != null ){
            favoriteRecyclerAdapter = new FavoriteRecyclerAdapter(this,arrayList);
        }
        recyclerView.setAdapter(favoriteRecyclerAdapter);

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                arrayList.remove(viewHolder.getAdapterPosition());
                favoriteRecyclerAdapter.notifyDataSetChanged();
                sharedPrefs.putList(arrayList);
                new NewsFeeds(FavoritesActivity.this).toast("removed");
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void finish() {
        super.finish();
        customType(this,"bottom-to-up");
    }
}