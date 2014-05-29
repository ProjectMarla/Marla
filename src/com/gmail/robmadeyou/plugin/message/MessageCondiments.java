package com.gmail.robmadeyou.plugin.message;

import java.util.ArrayList;

/**
 * Created by rob on 25/05/14.
 */
public class MessageCondiments {

    public static MessageCondiments COLOR_WHITE = addNew(new MessageCondiments("!colorx0")),
                                    COLOR_BLACK = addNew(new MessageCondiments("!colorx1")),
                                    COLOR_RED = addNew(new MessageCondiments("!colorx2")),
                                    COLOR_GREEN = addNew(new MessageCondiments("!colorx3")),
                                    COLOR_BLUE = addNew(new MessageCondiments("!colorx4"));

    private String in;
    public static ArrayList<MessageCondiments> list = new ArrayList<>();
    public MessageCondiments(String in){
        this.in = in;
    }

    @Override
    public String toString() {
        return in;
    }

    /**
     * Add a new message color
     * @param msg
     * @return msg
     */
    public static MessageCondiments addNew(MessageCondiments msg){
        list.add(msg);
        return msg;
    }
}
