package edu.towson.cosc431.valis.networkingdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.ImageService;
import edu.towson.cosc431.valis.networkingdemo.adapters.ImageListAdapter;
import edu.towson.cosc431.valis.networkingdemo.async.ImageFetcher;

public class ImageListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        // setup the recycler view
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Avatar> data = ImageService.getInstance().getAvatars();
        // create a handler that is associated with the UI thread (this one)
        Handler uiHandler = new Handler(Looper.getMainLooper());
        recyclerView.setAdapter(new ImageListAdapter(data, uiHandler));

        // clears the cache (deletes everything in the cache dir)
        button = (Button)findViewById(R.id.clearCache);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheService.getInstance().clearCache(ImageListActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageFetcher.exit();
    }
}