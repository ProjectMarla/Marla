package com.gmail.robmadeyou.brain;

import com.gmail.robmadeyou.plugin.Activator;
import com.gmail.robmadeyou.plugin.Base;
import com.gmail.robmadeyou.plugin.Output;
import com.gmail.robmadeyou.plugin.Thoughts;
import com.sun.istack.internal.Nullable;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by rob on 02/05/2014.
 */
public class Marla {

    public static String VERSION = "0.0.1.4";
    public static String NAME = "Marla";
    public static String SUFFIX = " > ";

    private ArrayList<String> positiveInputs = new ArrayList<>();
    private ArrayList<String> negativeInputs = new ArrayList<>();
    private ArrayList<String> unclearInputs = new ArrayList<>();

    private ArrayList<Output> outputs = new ArrayList<>();
    private ArrayList<Activator> activators = new ArrayList<>();
    private Activator lockedActivator;

    /**
     * Default constructor for the almighty Marla AI??
     * It looks in the directory brains/* and searches for any Jar
     * files that have classes inside with the name Main. This will
     * be later changed so that the developer can name the class to what
     * ever they want, but as long as they reference it somewhere(most likely
     * a json or an xml or something along the lines).
     */
    public Marla(){

        searchDirectoryForPlugins("brains/");

    }

    /**
     * Searches a directory relative to the current working directory
     * and looks for all the possible plugins to load
     * @param dir directory to work in. Is relative to current working directory.
     */
    public void searchDirectoryForPlugins(String dir){
        try {
            Files.walk(Paths.get(dir)).forEach(filePath -> {
                String file = filePath+"";
                if(Files.isRegularFile(filePath) && file.endsWith(".jar")) {
                    try {
                        JarFile jar = new JarFile(filePath+"");
                        Enumeration e = jar.entries();
                        Class c = null;
                        URL[] urls = {new URL("jar:file:"+filePath+"!/")};
                        java.lang.ClassLoader cl = URLClassLoader.newInstance(urls);
                        while (e.hasMoreElements()) {
                            JarEntry je = (JarEntry) e.nextElement();
                            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                                continue;
                            }
                            String className = je.getName().substring(0, je.getName().length() - 6);
                            className = className.replace('/', '.');
                            c = cl.loadClass(className);
                            if (c.getName().contains("Main")) {
                                Object i = c.newInstance();
                                ((Base) i).setMarla(this);
                                if (i instanceof Output) {
                                    outputs.add((Output) i);
                                } else if (i instanceof Activator) {
                                    activators.add((Activator) i);
                                } else if (i instanceof Thoughts) {
                                }
                            }
                        }
                    } catch (Exception e) {}
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }


    private void populateInputs(){
        //Positives
        positiveInputs.add("y");
        positiveInputs.add("yes");
        positiveInputs.add("yeah");
        positiveInputs.add("yesh");
        positiveInputs.add("correct");
        positiveInputs.add("affirmative");
        positiveInputs.add("alright");
        positiveInputs.add("yer");
        positiveInputs.add("indubitably");
        positiveInputs.add("sure");

        negativeInputs.add("n");
        negativeInputs.add("no");
        negativeInputs.add("nope");
        negativeInputs.add("nah");
        negativeInputs.add("negative");
        negativeInputs.add("nup");
        negativeInputs.add("n");
        negativeInputs.add("dubitably");

        unclearInputs.add("not sure");
        unclearInputs.add("I don't know");
        unclearInputs.add("dunno");
        unclearInputs.add("no idea");
        unclearInputs.add("hmm");
        unclearInputs.add("hm");
        unclearInputs.add("beats me");
        unclearInputs.add("don't ask me");
    }

    /**
     * Locks to a specific activator. This method is
     * only called from the Activator class, specifically
     * lock, and unlock methods;
     * @param ac Activator to lock on to
     * @return this instance of Marla
     */
    public final Marla lockToActivator(Activator ac){
        this.lockedActivator = ac;
        return this;
    }

    /**
     * @return the currently locked activator
     */
    @Nullable
    public final Activator getLockedActivator(){
        return lockedActivator;
    }

    /**
     * @return a list of positive strings that could be used by the user to reply to a question.
     */
    public String[] getPositiveInputs(){
        String[] tmp = new String[positiveInputs.size()];
        for (int i = 0; i < positiveInputs.size(); i++) {
            tmp[i] = positiveInputs.get(i);
        }
        return tmp;
    }

    /**
     * @return a list of negative strings that could be used by the user to reply to a question.
     */
    public String[] getNegativeInputs(){
        String[] tmp = new String[negativeInputs.size()];
        for (int i = 0; i < negativeInputs.size(); i++) {
            tmp[i] = negativeInputs.get(i);
        }
        return tmp;
    }

    /**
     * @return a list of unclear strings that could be used by the user to reply to a question.
     */
    public String[] getUnclearInputs(){
        String[] tmp = new String[unclearInputs.size()];
        for (int i = 0; i < unclearInputs.size(); i++) {
            tmp[i] = unclearInputs.get(i);
        }
        return tmp;
    }

    /**
     * Add a negative reply to the list.
     * @param negatives String(s) that can be added to the negative list
     */
    public void addNegativeInputs(String... negatives){
        for (String s : negatives) {
            this.negativeInputs.add(s);
        }
    }

    /**
     * Add a positive reply to the list.
     * @param positives String(s) that can be added to the positive list
     */
    public void addPositiveInputs(String... positives){
        for(String s : positives){
            this.positiveInputs.add(s);
        }
    }

    /**
     * Add an unclear reply to the list.
     * @param unclear String(s) that can be added to the unclear list
     */
    public void addUnclearInputs(String... unclear){
        for(String s : unclear){
            unclearInputs.add(s);
        }
    }

    /**
     * How input is handled. From here Marla will figure out
     * how to parse the input, and what plugins need to be
     * told about the input. Also handles how a user might escape from
     * locked in plugins, or specific Marla only commands.
     * @param in
     * @return this instance of Marla
     */
    public void input(String in){
        //TODO convert all string words into word words for Marla to understand them easier
        //Check if input is a question
        if(lockedActivator == null) {
            for (Activator ac : activators) {
                if (ac.check(in)) {
                    //TODO
                }
            }
        }else{
            if(in.equals("reload"))  ;
            else if(!in.equals("!exit"))
                lockedActivator.in(in);
            else{
                output("Exiting from " + lockedActivator.getActivators()[0]);
                lockedActivator = null;
            }
        }
        //return this;
    }

    /**
     * Reloads Marla fully.
     * It is important that this method is called if you
     * had changed Marla as it will safely turn off all the plugins before stopping.
     * It would be silly to keep constantly calling this method as it
     * would just strain your computer.
     */
    public final void reload(){
        //TODO Finish reloading plugins
        for(Activator ac : activators){
            ac.end();
        }
        for(Output out : outputs){
            out.end();
        }
    }

    /**
     * Most basic form of outputting messages to client.
     * Currently is used as the main way of sending messages
     * but as new data displays appear, more functionality will
     * be needed, as in special classes that are able to group data,
     * in questions, or other ways.
     * @param out
     */
    public void output(String out){
        for (int i = 0; i < outputs.size(); i++) {
            if(lockedActivator != null)
            out = lockedActivator.getName() + SUFFIX + out;
            else
                out = NAME + SUFFIX + out;
            outputs.get(i).out(out);
        }
    }
}
