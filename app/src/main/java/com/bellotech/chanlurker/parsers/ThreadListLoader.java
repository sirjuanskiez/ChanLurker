package com.bellotech.chanlurker.parsers;

import com.bellotech.chanlurker.data.Thread;
import com.bellotech.chanlurker.data.Threads;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class ThreadListLoader {
    private final String TRHEADS_FOR_BOARD_URL = "http://a.4cdn.org/";
    ThreadListLoader(){};

    public static List<Thread> LoadThreadList(String... params){
        try{
            return new ThreadListLoader().buildThreadList(params);
        }catch (MalformedURLException me){

        } catch (IOException e){

        }
        return null;
    }

    private List<Thread> buildThreadList(String... params) throws MalformedURLException, IOException{
        String json = IOUtils.toString(new URL(TRHEADS_FOR_BOARD_URL + params[0] + "/" + params[1] + ".json"));
        JsonReader reader = new JsonReader(new StringReader(json));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Threads threads = gson.fromJson(reader, Threads.class);
        return threads.getThreads();
    }
}
