package edu.towson.cosc431.valis.networkingdemo.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import edu.towson.cosc431.valis.networkingdemo.BuildConfig;
import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.ImageService;
import edu.towson.cosc431.valis.networkingdemo.CacheService;

/**
 * Created by randy on 3/29/17.
 */

public class ImageFetcher {

    public interface Callback {
        void onSuccess(Bitmap img, Avatar avatar);
        void onError(String src);
    }


    private Context ctx;
    public ImageFetcher(Context ctx) {
        this.ctx = ctx;
    }

    static ThreadPoolExecutor executor =
            (ThreadPoolExecutor)Executors.newFixedThreadPool(2);

    public static void fetch(final Context ctx, final Avatar avatar, final Callback cb) {
        //Bitmap image = CacheService.getInstance().getCachedFile(ctx, avatar.id);
        //if(image != null) {
        //  cb.onSuccess(image, avatar);
        //} else {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    // Code here runs on a background thread that displays the image
                    Bitmap b = ImageService.getInstance().getImage(null, avatar.id);
                    //CacheService.getInstance().cacheImage(ctx, avatar.id, b);
                    cb.onSuccess(b, avatar);
                } catch (Exception e) {
                    cb.onError("Oops");
                }
            }
        };
//            ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo network = cm.getActiveNetworkInfo();
//            Log.i("TAG", network.toString());
//            if(network.isAvailable() && network.isConnected()) {
//                // will not work on emulator!!!!
//                if(network.getType() == ConnectivityManager.TYPE_WIFI) {
//                    executor.execute(runnable);
//                } else {
//                    cb.onError("Not on WIFI");
//                }
//            } else {
//                cb.onError("Not connected to network");
//            }

        // check the data connectivity
        TelephonyManager ts = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if(ts.getDataState() == TelephonyManager.DATA_CONNECTED && ts.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            executor.execute(runnable);
        } else {
            cb.onError("Not on LTE network. Not downloading");
            ts.listen(new ImagePhoneStateListener(runnable), PhoneStateListener.LISTEN_SERVICE_STATE);
        }
        //}
    }

    public static void exit() {
        executor.shutdown();
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
    }

    static class ImagePhoneStateListener extends PhoneStateListener {
        Runnable runnable;
        public ImagePhoneStateListener(Runnable runnable) {
            this.runnable = runnable;
        }
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
            executor.execute(runnable);
        }
    }
}
