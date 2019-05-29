package com.example.http_rest_client;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.drm.DrmStore;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {

    private Tracks_API api;
    private RecyclerView.Adapter mAdapter;
    // use a linear layout manager
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private List<Track> trackList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.192.220.45:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        this.getTracks();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // define an adapter
                //Update posts!!
                Intent i = new Intent(MainActivity.this, NewTrackActivity.class);
                startActivity(i);
            }
        });
    }

    public void getTracks(){
        Call<List<Track>> call = api.getTracks();

        call.enqueue(new Callback<List<Track>>(){
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

                trackList = response.body();

                mAdapter = new MyAdapter(trackList, MainActivity.this);
                recyclerView.setAdapter(mAdapter);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unexpected error",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
