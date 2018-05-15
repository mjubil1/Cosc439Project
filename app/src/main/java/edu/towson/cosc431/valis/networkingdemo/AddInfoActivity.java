package edu.towson.cosc431.valis.networkingdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.towson.cosc431.valis.networkingdemo.database.AvatarDatastore;
import edu.towson.cosc431.valis.networkingdemo.models.Message;

public class AddInfoActivity extends AppCompatActivity {

    private static final String TAG = AddInfoActivity.class.getName();

    public static final String NAME_KEY = "ADD_NAME";
    public static final String AVATAR_KEY = "AVATAR_KEY";

    AvatarDatastore dataStore;
    EditText nameEt, avatarEt;
    Button save;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        save = findViewById(R.id.saveBtn);
        nameEt = findViewById(R.id.nameEt);
        avatarEt = findViewById(R.id.avatarEt);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
            }
        });
    }

    private void navigateBack() {
        Toast.makeText(this,"Saving to the database",Toast.LENGTH_SHORT).show();

        // Add messaging info to database
        dataStore = new AvatarDatastore(this);

        String nameTxt = nameEt.getText().toString();
        String avatarTxt = avatarEt.getText().toString();

        Message message = new Message(nameTxt,avatarTxt);

        Intent resultIntent = new Intent(getApplicationContext(), ImageListActivity.class);

        // This stores the message that was created to the database
        dataStore.addMessage(message);

        resultIntent.putExtra(NAME_KEY, nameTxt);
        resultIntent.putExtra(AVATAR_KEY, avatarTxt);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}