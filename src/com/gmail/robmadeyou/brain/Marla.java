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

    public static String VERSION = "0.0.1.0";
    public static String NAME = "Marla";
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
        try {
            Files.walk(Paths.get("brains/")).forEach(filePath -> {
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
     * How input is handled. From here Marla will figure out
     * how to parse the input, and what plugins need to be
     * told about the input. Also handles how
     * @param in
     * @return this instance of Marla
     */
    public void input(String in){
        //Check if input is a question
        if(lockedActivator == null) {
            for (Activator ac : activators) {
                if (ac.check(in)) {

                }
            }
        }else{
            if(!in.equals("!exit"))
                lockedActivator.in(in);
            else{
                output("Exiting from " + lockedActivator.getActivators()[0]);
                lockedActivator = null;
            }
        }
        //return this;
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
            outputs.get(i).out(out);
        }
    }
}
