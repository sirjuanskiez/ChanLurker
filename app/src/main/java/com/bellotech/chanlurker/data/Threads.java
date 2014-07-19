package com.bellotech.chanlurker.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class Threads {
    @SerializedName("threads")
    private List<Thread> threads = new ArrayList<Thread>();

    public List<Thread> getThreads(){
        return threads;
    }

    public void setThreads(List<Thread> threads){
        this.threads = threads;
    }
}
