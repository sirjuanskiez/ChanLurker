package com.bellotech.chanlurker.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan on 7/18/2014.
 */
public class Board {
    @SerializedName("board")
    private String name;
    private String title;
    @SerializedName("ws_board")
    private int workSafe;
    @SerializedName("per_page")
    private int threadsPerPage;
    @SerializedName("pages")
    private int maxPages;

    public String getName() {
        return "/" + name + "/";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWorkSafe() {
        return workSafe;
    }

    public void setWorkSafe(int workSafe) {
        this.workSafe = workSafe;
    }

    public int getThreadsPerPage() {
        return threadsPerPage;
    }

    public void setThreadsPerPage(int threadsPerPage) {
        this.threadsPerPage = threadsPerPage;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }
}
