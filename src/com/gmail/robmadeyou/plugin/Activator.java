package com.gmail.robmadeyou.plugin;

/**
 * Created by rob on 07/05/2014.
 */
public abstract class Activator extends Base {

    private String[] activators;
    public Activator(String... activators){
        this.activators = activators;
    }

    public String[] getActivators(){
        return activators;
    }

    /**
     * @param possibilities possible activations
     * @return If check was valid, returns true, else false.
     */
    public boolean check(String... possibilities){
        for(String s : activators){
            for(String ss : possibilities){
                if(s.equalsIgnoreCase(ss)) {
                    onActivation();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * When input is right and activates. This method is called
     */
    public abstract void onActivation();
}
