package com.gmail.robmadeyou.plugin;

import com.gmail.robmadeyou.brain.Marla;

/**
 * Created by rob on 02/05/2014.
 */
public abstract class Base {

    Marla marla;
    boolean initialized = false;
    public Base(){}

    /**
     * Method that is run before a plugin is disabled. Or turned off.
     */
    public abstract void end();

    /**
     * Is called when plugin is ready to be initialized
     */
    public abstract void init();

    /**
     * @return active Marla instance
     */
    public Marla getMarla(){
        return marla;
    }

    /**
     * Sets Marla, and starts a new thread with
     * the Init. A new thread is started in case
     * coder wants to make a while loop to register all the text.
     * This way the main thread doesn't hang.
     *
     * How ever if the coder doesn't create a while loop, or any
     * other kind of loop, it will mean that the input will only
     * work once.
     */
    public Base setMarla(Marla marla) {
        if (!initialized){
            this.marla = marla;
            initialized = true;
            new Thread(() -> {
                init();
            }).start();
        }
        return this;
    }
}
