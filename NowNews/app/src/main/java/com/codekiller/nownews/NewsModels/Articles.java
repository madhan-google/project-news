package com.codekiller.nownews.NewsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Articles {


    public Articles() {
    }

    @SerializedName("source")
    @Expose
    Source source;

    @SerializedName("author")
    @Expose
    String author;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("urlToImage")
    @Expose
    String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    String publishedAt;

    @SerializedName("content")
    @Expose
    String content;


    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}
