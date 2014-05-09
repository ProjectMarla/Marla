package com.gmail.robmadeyou.plugin;


/**
 * Created by rob on 02/05/2014.
 */
public abstract class Input extends Base{

    public Input(){

    }

    public final void text(String in){
        getMarla().input(in);
    }
}
