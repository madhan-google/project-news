package com.codekiller.nownews.NewsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Everything {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("totalResults")
    @Expose
    int total_results;

    @SerializedName("articles")
    @Expose
    ArrayList<Articles> articles;

    public String getStatus() {
        return status;
    }

    public int getTotal_results() {
        return total_results;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }
}
