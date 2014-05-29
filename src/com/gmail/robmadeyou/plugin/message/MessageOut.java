package com.gmail.robmadeyou.plugin.message;

import java.util.ArrayList;

/**
 * Created by rob on 25/05/14.
 */
public class MessageOut {

    private ArrayList<String> list = new ArrayList<>();
    public MessageOut(String... list){
        for (String s : list) {
            this.list.add(s);
        }
    }

    @Override
    public String toString() {
        StringBuilder buildr = new StringBuilder();
        for(String s : list){
            for(MessageCondiments l : MessageCondiments.list){
                if(s.contains(l.toString())) buildr.append(l);
            }
        }
        return buildr.toString();
    }
}
