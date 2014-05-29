package com.gmail.robmadeyou.plugin.message;

import java.util.ArrayList;

/**
 * Created by rob on 07/05/2014.
 */
public class SmartString {

    /**
     * TODO this is going to be a big big challange for me!
     * Basically takes in a string and tries to determine if it's
     * a legitimate question or not.
     * @param s
     * @return
     */
    public static boolean isQuestion(String s){
        float likeliness = 0f;
        float magic = 0.6f;



        return likeliness > magic;
    }

    /**
     * A check to see if string is included in an array
     * @param string Array of strings you would like to check if they have a
     *               pair with the first in the index.
     *               So for example if I wanted to check for apple in
     *               an array of 5 strings, I would do equalsOneOf("apple",
     *               "pear", "banana", "orange", "plum", "apple") and
     *               the method would return True. if apple wasn't present
     *               then the method would return false
     * @return if the first string has a pair with any of the other strings
     */
    public static boolean equalsOneOf(String... string){
        try {
            for (int i = 1; i < string.length; i++) {
                if (string[0].equalsIgnoreCase(string[i])) return true;
            }
        }catch (Exception e){}
        return false;
    }

    /**
     * A check to see if string is included in an array
     * @param string Array of strings you would like to check if they have a
     *               pair with the first in the index.
     *               So for example if I wanted to check for apple in
     *               an array of 5 strings, I would do equalsOneOf("apple",
     *               "pear", "banana", "orange", "plum", "apple") and
     *               the method would return True. if apple wasn't present
     *               then the method would return false
     * @return if the first string has a pair with any of the other strings
     */
    public static boolean equalsOneOf(ArrayList<String> string){
        String[] tmp = new String[string.size()];
        for (int i = 0; i < string.size(); i++) {
            tmp[i] = string.get(i);
        }
        return equalsOneOf(tmp);
    }

    /**
     * Almost the same thing as @see but this time we are only checking if
     * the main string is contained in one of the strings
     * @see com.gmail.robmadeyou.plugin.message.SmartString#equalsOneOf(String...)
     * @param string
     * @return
     */
    public static boolean containsOneOf(String... string){
        try {
            for (int i = 1; i < string.length; i++) {
                if (string[0].contains(string[i])) return true;
            }
        }catch (Exception e){}
        return false;
    }

    /**
     * Almost the same thing as @see but this time we are only checking if
     * the main string is contained in one of the strings
     * @see com.gmail.robmadeyou.plugin.message.SmartString#equalsOneOf(String...)
     * @param string
     * @return
     */
    public static boolean containstOneOf(ArrayList<String> string){
        String[] tmp = new String[string.size()];
        for (int i = 0; i < string.size(); i++) {
            tmp[i] = string.get(i);
        }
        return containsOneOf(tmp);
    }

    /**
     * Adds a dot if necessary at the end of a sentence
     * @param in string you would like to dotify
     * @return dotified string
     */
    public static String dotify(String in){
        try {
            if (in.charAt(in.length() - 1) == '.' ||
                    in.charAt(in.length() - 1) == '!' ||
                    in.charAt(in.length() - 1) == '?') {
                return in;
            } else {
                in += ".";
            }
        }catch (Exception e){};
        return in;
    }

}
