package com.gmail.robmadeyou.plugin.message;

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
        for (int i = 1; i < string.length; i++) {
            if(string[0].equals(string[i])) return true;
        }
        return false;
    }

}
