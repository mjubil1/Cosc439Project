package edu.towson.cosc431.valis.networkingdemo.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import edu.towson.cosc431.valis.imageservice.ImageService;
import edu.towson.cosc431.valis.networkingdemo.Pojo;

//import edu.towson.cosc431.valis.networkingdemo.ImageService;

/**
 * Created by randy on 4/8/17.
 */
public class NetManager {
    private static NetManager ourInstance = new NetManager();

    // the base url for fetching an image
    private static String iconUrlTemplate = "http://api.adorable.io/avatars/285/%s@adorable.io.png";

    public static NetManager getInstance() {
        return ourInstance;
    }

    private NetManager() {
    }

    private static String API = "https://jsonplaceholder.typicode.com";

    /**
     * Fetch and parse a sinlge JSON item
     * @return a parsed Pojo from JSON
     */
    public Pojo getJson() {
        Pojo res = new Pojo();

        try {
            URL url = new URL(API + "/posts/1");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] bytes = new char[1024];
            StringBuilder builder = new StringBuilder();
            int byteCount = reader.read(bytes);
            while(byteCount > 0) {
                builder.append(bytes, 0, byteCount);
                byteCount = reader.read(bytes);
            }
            JSONObject obj = new JSONObject(builder.toString());
            res.title = obj.getString("title");
            res.userId = obj.getInt("userId");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Fetch and parse a json array
     * @return a list of Pojo objects parsed from JSON
     */
    public List<Pojo> getJsonArray() {
        List<Pojo> pojos = new ArrayList<>();

        try {
            URL url = new URL(API + "/posts");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] bytes = new char[1024];
            StringBuilder builder = new StringBuilder();
            int byteCount = reader.read(bytes);
            while(byteCount > 0) {
                builder.append(bytes, 0, byteCount);
                byteCount = reader.read(bytes);
            }
            JSONArray arr = new JSONArray(builder.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Pojo res = new Pojo();
                res.deserialize(obj);
                pojos.add(res);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pojos;
    }

    /**
     * Fetch a binary image
     * @param id
     * @return
     */
    public Bitmap getAvatar(String id) {

        try {
            URL url = new URL(String.format(iconUrlTemplate, id));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();

            // UNCOMMENT FOR A SIMPLER API
//            return BitmapFactory.decodeStream(stream);

            // MORE CONTROL
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int bytesRead = stream.read(bytes);
            while(bytesRead > 0) {
                os.write(bytes, 0, bytesRead);
                // read more bytes
                bytesRead = stream.read(bytes);
            }
            bytes = os.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getAvatarJar(int id) {
        List<Integer> ids = ImageService.getInstance().getAvailableImageIds();
        return ImageService.getInstance().getImage(id+"");
    }
}