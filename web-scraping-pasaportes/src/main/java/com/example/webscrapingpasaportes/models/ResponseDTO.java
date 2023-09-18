package com.example.webscrapingpasaportes.models;

public class ResponseDTO {

    String title;
    String url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
