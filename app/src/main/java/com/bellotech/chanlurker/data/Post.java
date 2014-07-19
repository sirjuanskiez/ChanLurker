package com.bellotech.chanlurker.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan on 7/18/2014.
 */
public class Post {
    private String opNo;
    @SerializedName("no")
    private long postNo;
    @SerializedName("now")
    private String date;
    private String name;
    @SerializedName("com")
    private String comment;
    private String tim;
    private String ext;

    public String getOpNo(){
        return opNo;
    }

    public void setOpNo(String opNo){
        this.opNo = opNo;
    }

    public Long getPostNo(){
        return postNo;
    }

    public String getDate(){
        return date;
    }

    public String getName(){
        return name;
    }

    public String getComment(){
        return comment != null ? comment : "";
    }

    public String getTim(){
        return tim;
    }

    public String getExt(){
        return ext;
    }
}
