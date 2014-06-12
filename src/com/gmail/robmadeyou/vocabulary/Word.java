package com.gmail.robmadeyou.vocabulary;

/**
 * Created by robertellis on 12/06/2014.
 */
public class Word {

    private final String word;
    private final WordType type;
    public Word(String word, WordType type){
        this.word = word;
        this.type = type;
    }

    public String getWord(){
        return word;
    }

    public WordType getType(){
        return type;
    }

    public enum WordType{
        VERB, PRONOUN, PREPOSITION, NOUN, ADVERB, ADJECTIVE
    }
}
