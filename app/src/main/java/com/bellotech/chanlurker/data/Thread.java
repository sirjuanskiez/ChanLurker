package com.bellotech.chanlurker.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class Thread {
    @SerializedName("posts")
    private List<Post> posts = new ArrayList<Post>();

    public List<Post> getPosts(){
        return posts;
    }

    public void setPosts(List<Post> posts){
        this.posts = posts;
    }
}
