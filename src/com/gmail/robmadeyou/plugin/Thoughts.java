package com.gmail.robmadeyou.plugin;

public abstract class Thoughts extends Base {

    private long interval;
    private String[] activations;

    /**
     *
     * @param interval if interval is 0, thoughts will only be invoked by activations.
     *                 else, interval is the delay between each tick in milliseconds.
     *                 Ideal interval is anything over ~20+ seconds. It is good to
     *                 make sure that you manage your plugins well with thoughts and stuff.
     * @param activations strings that can activate the thought
     */
    public Thoughts(long interval, String... activations){
        this.interval = interval;
        this.activations = activations;
        if(interval > 0){
            new Thread(() ->{
                while(true) {
                    onClick();
                    try{
                        Thread.sleep(interval);
                    }catch (Exception e){e.printStackTrace();}
                }
            }).start();
        }
    }

    /**
     * Returns the interval that a thread might be activated.
     * If value is 0, it is only activated by activations.
     * @return the interval that the thread will sleep in milliseconds
     */
    public long getIntervals(){
        return this.interval;
    }

    /**
     * Returns the list of activations that could possibly activate the
     * thought
     * @return activators that can activate the thought
     */
    public String[] getActivations(){
        return activations;
    }

    /**
     * Checks if string is able to activate the thought
     * and activates the thread
     * O(2n)
     * @return if string is able to activate the thought
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
     *  The length of every click is defined
     *  when the constructor is called
     */
    public abstract void onClick();
}
