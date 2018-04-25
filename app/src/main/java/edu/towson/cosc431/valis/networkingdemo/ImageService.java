package edu.towson.cosc431.valis.networkingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.networking.NetManager;

/**
 * Created by randy on 3/29/17.
 */
public class ImageService {
    private static ImageService ourInstance = new ImageService();

    public static ImageService getInstance() {
        return ourInstance;
    }

    List<Avatar> data;
    private ImageService() {
        data = new ArrayList<>(100);
        for (int i = 0; i < 30; i+=3) {
            Avatar avatar = new Avatar();
            avatar.id = i;
            avatar.img = "abott1";
            avatar.name = "Avatar 1";
            data.add(avatar);
            avatar = new Avatar();
            avatar.id = i + 1;
            avatar.img = "abott2";
            avatar.name = "Avatar 2";
            data.add(avatar);
            avatar = new Avatar();
            avatar.id = i + 2;
            avatar.img = "abott3";
            avatar.name = "Avatar 3";
            data.add(avatar);
        }
    }

    List<Avatar> getAvatars() {
        return data;
    }

    public Bitmap getImage(Context ctx, int id) {
        NetManager manager = NetManager.getInstance();
        Avatar avatar = data.get(id);
        return manager.getAvatarJar(avatar.id);
    }
}