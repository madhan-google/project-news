package com.codekiller.nownews.NewsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

    public Source() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
