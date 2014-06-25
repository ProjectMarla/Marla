package com.gmail.robmadeyou.vocabulary;

/**
 * Created by robertellis on 12/06/2014.
 * As we all know, sentences are made up of an array of words, so that's exactly
 * what we are going to do. Add an array of words to our list and have some cool
 * functionality
 */
public class Sentence
{

    private Word[] list;
    public Sentence(Word... words)
    {
        this.list = words;
    }

    /**
     * @return A list of words that the sentence contains
     */
    public Word[] getWordList()
    {
        return this.list;
    }
}
