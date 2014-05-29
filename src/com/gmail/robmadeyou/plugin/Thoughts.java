package com.gmail.robmadeyou.plugin;

/**
 * Created by rob on 07/05/2014.
 */
public abstract class Thoughts extends Base {

    private long interval;
    private String[] activations;

    /**
     *
     * @param interval if interval is 0, thoughts will only be invoked by activations.
     *                 else, interval is the delay between each tick in milliseconds.
     *                 Ideal interval is anything over ~20+ seconds. It is good to
     *                 make sure that you manage your plugins well with thoughts and stuff.
     * @param activations
     */
    public Thoughts(long interval, String... activations){
        this.interval = interval;
        this.activations = activations;
        if(interval > 0){
            new Thread(() ->{
                try{
                    while(true) {
                        onClick();
                        Thread.sleep(interval);
                    }
                }catch (Exception e){e.printStackTrace();}
            }).start();
        }
    }

    /**
     * Returns the interval that a thread might be activated.
     * If value is 0, it is only activated by activations.
     * @return
     */
    public long getIntervals(){
        return this.interval;
    }

    /**
     * Returns the list of activations that could possibly activate the
     * thought
     * @return
     */
    public String[] getActivations(){
        return activations;
    }

    /**
     * Checks if string is able to activate the thought
     * and activates the thread
     * O(2n)
     * @return if string is an activator
     */
    public boolean check(String... activator){
        for (int i = 0; i < activator.length; i++) {
            for (int j = 0; j < this.activations.length; j++) {
                if(activator[i].equalsIgnoreCase(activations[j])){
                    onClick();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *  The lenght of every click is defined
     *  when the constructor is called
     */
    public abstract void onClick();
}
