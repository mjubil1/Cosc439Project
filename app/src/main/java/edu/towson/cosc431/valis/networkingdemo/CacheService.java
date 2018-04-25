package edu.towson.cosc431.valis.networkingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by randy on 3/30/17.
 */
public class CacheService {
    private static CacheService ourInstance = new CacheService();

    public static CacheService getInstance() {
        return ourInstance;
    }


    private CacheService() {
    }

    public void clearCache(Context ctx) {
        for(File file : ctx.getCacheDir().listFiles()) {
            file.delete();
        }
    }

    public Bitmap getCachedFile(Context ctx, final int id) {
        File[] files = ctx.getCacheDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith(Integer.toString(id) + "000");
            }
        });
        if(files.length == 0) return null;
        return BitmapFactory.decodeFile(files[0].getAbsolutePath());
    }

    public void cacheImage(Context ctx, int id, Bitmap bitmap) {
        try {
            File dir = ctx.getCacheDir();
            File cache = File.createTempFile(Integer.toString(id) + "000","",dir);
            FileOutputStream fos = new FileOutputStream(cache);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}