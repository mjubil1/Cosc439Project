package edu.towson.cosc431.valis.networkingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.towson.cosc431.valis.networkingdemo.models.Avatar;
import edu.towson.cosc431.valis.networkingdemo.ImageService;
import edu.towson.cosc431.valis.networkingdemo.adapters.ImageListAdapter;
import edu.towson.cosc431.valis.networkingdemo.async.ImageFetcher;

public class ImageListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ADD_INFO_CODE = 100;
    private static final int MESSAGE_CODE = 200;
    RecyclerView recyclerView;
    Button button,button2;
    TextView avatarTv;
    Intent intent,myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        avatarTv = findViewById(R.id.avatarTv);
        button = (Button)findViewById(R.id.addInfo);
        button2 = (Button)findViewById(R.id.messagesBtn);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        // setup the recycler view
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Avatar> data = ImageService.getInstance().getAvatars();
        // create a handler that is associated with the UI thread (this one)
        Handler uiHandler = new Handler(Looper.getMainLooper());
        recyclerView.setAdapter(new ImageListAdapter(data, uiHandler));
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.messagesBtn:
                Toast.makeText(this,"Navigate to Messages",Toast.LENGTH_SHORT).show();
                myIntent = new Intent(this,MainActivity.class);
                startActivityForResult(myIntent,MESSAGE_CODE);
                break;
            case R.id.addInfo:
                Toast.makeText(this,"Navigate to Info",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, AddInfoActivity.class);
                startActivityForResult(intent, ADD_INFO_CODE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageFetcher.exit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_INFO_CODE) {
            if(resultCode == RESULT_OK) {
                String nameTxt = data.getStringExtra(AddInfoActivity.NAME_KEY);
                String avatarTxt = data.getStringExtra(AddInfoActivity.AVATAR_KEY);
            }
        }
    }
}