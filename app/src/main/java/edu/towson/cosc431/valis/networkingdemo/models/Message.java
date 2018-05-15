package edu.towson.cosc431.valis.networkingdemo.models;

/**
 * Created by Montrell on 5/15/2018.
 */

public class Message {
    private String img;
    private String id;
    private String message;
    private String name;

    public Message() {}

    public Message( String message, String name) {
        this.message = message;
        this.name = name;
    }
    //------------------------------------------Setters--------------------------------------------//

    public void setImg(String img) { this.img = img; }

    public void setMessage(String contents) {
        this.message = contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    //------------------------------------------Getters--------------------------------------------//
    public String getImg() {
        return img;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getId() { return id; }
}

