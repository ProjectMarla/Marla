package com.gmail.robmadeyou.plugin;

/**
 * Created by rob on 07/05/2014.
 */
public abstract class Activator extends Base {

    //Waits 100 milliseconds before checking if a new string has been entered
    private int TIME_TO_WAIT_FOR_INPUT = 100;

    private String NAME;

    private String[] activators;
    private String currentInput = "";
    public Activator(String... activators){
        this.activators = activators;
    }

    /**
     * @return Returns possible keywords that are able to activate this activator.
     */
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
                    new Thread(() -> {
                        onActivation();
                    }).start();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Locks all current input to go through just this activator.
     * This makes sure that Marla understands where the input must
     * go and what to do with it. This allows for continuous input.
     */
    public final void lock(){
        if(getMarla().getLockedActivator() == null)
            getMarla().lockToActivator(this);
        else try{
            throw new IllegalLock("Lock is already held by another plugin");
            }catch (Exception e){
                out("I wasn't able to throw an exception so I threw another exception for that exception. What is happening?");
            };
    }

    /**
     * Unlocks the activator from Marla's main input, and no longer
     * passes all the Strings to the plugin directly. This is vital
     * to be called after calling lock;
     */
    public final void unlock(){
        getMarla().lockToActivator(null);
    }

    /**
     * Simplest way to print something to the user, will simply
     * notify them via a chat message. This will call Marla's output
     * method, so be careful on what plugins you are using to handle
     * the output, as not everyone will handle things the same way.
     * @param message Message(s) you would like to send out
     */
    public final void out(String... message){
        for(String s : message)
            getMarla().output(s);
    }

    /**
     *
     * @param in Activates specific input
     */
    public final void in(String in){
        this.currentInput = in;
    }

    /**
     * Constantly makes the thread wait till input is entered,
     * as soon as the input is entered, a value is returned and thread runs as normal.
     * This makes entering text, for questions and such really easy with Marla as it won't constantly
     * call the onActivation message, instead it will just call the in method and
     * the thread will handle the input if needed.
     *
     * This allows for linear questions, with no weird coding needed.
     * @return input;
     */
    public final String getInput(){
        while (currentInput == ""){
            try {
                Thread.sleep(TIME_TO_WAIT_FOR_INPUT);
            }catch (Exception e){out("Thread wasn't able to sleep in: " + this);}
        }
        String out = currentInput;
        currentInput = "";
        return out;
    }

    /**
     * Returns the name of the activator. This is really just
     * only for a nicer looking thing when everything is
     * done in the console.
     * @return name of activator
     */
    public final String getName(){
        return NAME;
    }

    /**
     * When input is right and activates. This method is called
     */
    public abstract void onActivation();
}
