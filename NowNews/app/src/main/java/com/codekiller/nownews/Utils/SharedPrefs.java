package com.codekiller.nownews.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;

public class SharedPrefs {
    SharedPreferences sharedPreferences;
    static Context context;

    public SharedPrefs() {

    }

    public SharedPrefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.codekiller.nownews",Context.MODE_PRIVATE);
    }

    public boolean putList(ArrayList<String> list){
        sharedPreferences.edit().putStringSet("saved",new HashSet<>(list)).apply();
        return true;
    }

    public ArrayList<String> getList(){
        return new ArrayList<>(sharedPreferences.getStringSet("saved",new HashSet<>()));
    }
}
