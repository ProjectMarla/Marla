package com.gmail.robmadeyou.brain;

import com.gmail.robmadeyou.plugin.Activator;
import com.gmail.robmadeyou.plugin.Base;
import com.gmail.robmadeyou.plugin.Output;
import com.gmail.robmadeyou.plugin.Thoughts;

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

    private ArrayList<Output> outputs = new ArrayList<>();
    private ArrayList<Activator> activators = new ArrayList<>();

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

    public void input(String in){
        //Check if input is a question
        in.toLowerCase();
        if(in.endsWith("?") || in.startsWith("what")){

        }
        for(Activator ac : activators){
            ac.check(in);
        }
    }

    public void output(String out){
        for (int i = 0; i < outputs.size(); i++) {
            outputs.get(i).out(out);
        }
    }
}
