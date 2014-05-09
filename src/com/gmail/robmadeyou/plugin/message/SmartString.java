package com.gmail.robmadeyou.plugin.message;

/**
 * Created by rob on 07/05/2014.
 */
public class SmartString {
    private String s;
    public SmartString(String s){
        this.s = s;
    }

    public boolean isQuestion(){
        float likelyness = 0f;
        float magic = 0.6f;



        return likelyness > magic;
    }

    public String getS(){
        return s;
    }
}
