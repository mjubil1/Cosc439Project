package edu.towson.cosc431.valis.networkingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.networking.NetManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NetManager manager = NetManager.getInstance();
        // fetch a single json item...
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Pojo res = manager.getJson();
                Log.i("TAG", res.title);
            }
        });
        thread.start();

        // fetch a list of json items...
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Pojo> res = manager.getJsonArray();
                for (Pojo pojo : res) {
                    Log.i("TAG", pojo.userId + ": " + pojo.title);
                }
            }
        });
        thread2.start();
    }
}
