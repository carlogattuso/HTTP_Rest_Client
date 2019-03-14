package com.example.http_rest_client;

import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class NewTrackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);

        Button send = (Button) findViewById(R.id.new_track_post);
        final EditText id = (EditText) findViewById(R.id.new_track_id_field);
        final EditText title = (EditText) findViewById(R.id.new_track_title_field);
        final EditText singer = (EditText) findViewById(R.id.new_track_singer_field);

        final Intent returnIntent = new Intent();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_text = id.getText().toString();
                String title_text = title.getText().toString();
                String singer_text = singer.getText().toString();

                if (title_text.equals("")||id_text.equals("")||singer_text.equals("")) {
                    Toast.makeText(NewTrackActivity.this, "Empty field/s",
                            Toast.LENGTH_LONG).show();
                } else {
                    returnIntent.putExtra("identifier",id_text);
                    returnIntent.putExtra("title",title_text);
                    returnIntent.putExtra("singer",singer_text);
                    setResult(NewTrackActivity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

        /*Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();*/
    }
}