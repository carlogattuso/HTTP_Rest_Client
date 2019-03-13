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

public class TrackDetailsActivity extends AppCompatActivity {

    private Tracks_API api;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_track_layout);

        final Intent adapter_intent = getIntent();
        final String identifier = adapter_intent.getStringExtra("identifier");
        final String title = adapter_intent.getStringExtra("title");
        final String singer = adapter_intent.getStringExtra("singer");
        final Intent intent_main = new Intent(TrackDetailsActivity.this,MainActivity.class);

        TextView identifier_text = (TextView) findViewById(R.id.id_field);
        TextView title_text = (TextView) findViewById(R.id.title_field);
        TextView singer_text = (TextView) findViewById(R.id.singer_field);

        identifier_text.setText(identifier);
        title_text.setText(title);
        singer_text.setText(singer);

        Button edit_button = (Button) findViewById(R.id.edit_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button info_button = (Button) findViewById(R.id.info_button);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_main.putExtra("id_delete",identifier);
                startActivity(intent_main);
                intent_main.removeExtra("id_delete");
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_main.putExtra("id_edit",identifier);
                startActivity(intent_main);
                intent_main.removeExtra("id_edit");
            }
        });
    }
}