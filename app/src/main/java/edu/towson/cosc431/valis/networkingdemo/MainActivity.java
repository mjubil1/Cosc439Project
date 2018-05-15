package edu.towson.cosc431.valis.networkingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.adapters.AvatarAdapter;
import edu.towson.cosc431.valis.networkingdemo.models.Message;
import edu.towson.cosc431.valis.networkingdemo.networking.NetManager;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getName();
    private static final int MESSAGE_CODE = 200;

    TextView nameTv,messageTv;
    Button goHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTv = findViewById(R.id.name);
        messageTv = findViewById(R.id.message);
        goHomeBtn = findViewById(R.id.homeBtn);
        Log.d(TAG,"onCreate");

        final NetManager manager = NetManager.getInstance();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Pojo res = manager.getJson();
                Log.i("TAG", res.title);
            }
        });

        thread.start();

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

        nameTv.setText("Avatar 1");
        messageTv.setText("Hello World");

        goHomeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG,"Home button clicked!!!");
                Intent i = new Intent(getApplicationContext(),ImageListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MESSAGE_CODE) {
            if(resultCode == RESULT_OK) {
                String nameTxt = data.getStringExtra(AddInfoActivity.NAME_KEY);
                String messageTxt = data.getStringExtra(AddInfoActivity.AVATAR_KEY);
                messageTv.setText(messageTxt);
                nameTv.setText(nameTxt);
                }
            }
        }
    }