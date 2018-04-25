package edu.towson.cosc431.valis.networkingdemo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by randy on 4/8/17.
 */

public class Pojo {
    public int userId;
    public String title;

    public String serialize() {
        JSONObject json = new JSONObject();
        try {
            json.put("userId", userId);
            json.put("title", title);
        } catch(JSONException ex) {}
        return json.toString();
    }

    public void deserialize(JSONObject json) {
        try {
            title = json.getString("title");
            userId = json.getInt("userId");
        } catch (JSONException ex) {

        }
    }
}