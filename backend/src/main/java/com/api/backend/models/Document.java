package com.api.backend.models;

import java.io.Serializable;

public class Document implements Serializable {
    private String title;
    private String url;
    private String content;
    private String dt_creation;
    private int reading_time;

    public Document() {

    }

    public Document(String title, String url, String content, String dt_creation, int reading_time) {
        this.title = title;
        this.url = url;
        this.content = content;
        this.dt_creation = dt_creation;
        this.reading_time = reading_time;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDt_creation() {
        return dt_creation;
    }

    public void setDt_creation(String dt_creation) {
        this.dt_creation = dt_creation;
    }

    public int getReading_time() {
        return reading_time;
    }

    public void setReading_time(int reading_time) {
        this.reading_time = reading_time;
    }
}
