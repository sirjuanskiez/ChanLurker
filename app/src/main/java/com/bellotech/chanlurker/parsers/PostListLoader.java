package com.bellotech.chanlurker.parsers;

import com.bellotech.chanlurker.data.Post;
import com.bellotech.chanlurker.data.Thread;
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
public class PostListLoader {
    private final String POSTS_FOR_THREAD_URL = "http://a.4cdn.org/";
    PostListLoader(){};

    public static List<Post> LoadPostList(String... params){
        try{
            return new PostListLoader().buildPostList(params);
        }catch (MalformedURLException me){

        } catch (IOException e){

        }
        return null;
    }

    private List<Post> buildPostList(String... params) throws MalformedURLException, IOException{
        String json = IOUtils.toString(new URL(POSTS_FOR_THREAD_URL + params[0] + "/thread/" + params[1] + ".json"));
        JsonReader reader = new JsonReader(new StringReader(json));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Thread thread = gson.fromJson(reader, Thread.class);
        return thread.getPosts();
    }
}
