package com.gmail.robmadeyou.vocabulary;

/**
 * Created by robertellis on 25/06/2014.
 */
public class DictionaryWord {
    private final String word;
    private final WordType type;
    public DictionaryWord(String word, WordType type){
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
