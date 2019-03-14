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
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        this.getTracks();
        //this.getProfile();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // define an adapter
                //Update posts!!
                Intent i = new Intent(MainActivity.this, NewTrackActivity.class);
                startActivityForResult(i, 1);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getString("id_delete")!=null) {

                final String value = extras.getString("id_delete");
                final String song_name = extras.getString("title_delete");
                final String singer_name = extras.getString("singer_delete");

                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                        //set message, title, and icon

                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete " + song_name + "-" + singer_name + "?")
                        .setPositiveButton("Delete Track", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                int position = 0;
                                for(int i=0;i<trackList.size();i++){
                                    if(trackList.get(i).getId().equals(value)){
                                        position = i;
                                    }
                                }
                                deleteTrack(value,position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Delete cancelled",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                dialog.dismiss();
                            }
                        })
                        .create();

                myQuittingDialogBox.create();
                myQuittingDialogBox.show();
                //The key argument here must match that used in the other activity
            }
            if(extras.getString("id_edit")!=null) {

                final String value = extras.getString("id_edit");
                final String song_name = extras.getString("title_edit");
                final String singer_name = extras.getString("singer_edit");

                AlertDialog myUpdatingDialogBox = new AlertDialog.Builder(this)
                        //set message, title, and icon

                        .setTitle("Update")
                        .setMessage("Are you sure you want to update ?")
                        .setPositiveButton("Update Track", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                int position = 0;
                                for(int i=0;i<trackList.size();i++){
                                    if(trackList.get(i).getId().equals(value)){
                                        position = i;
                                    }
                                }
                                Track t = new Track(value,song_name,singer_name);
                                updateTrack(t,position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Delete cancelled",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                dialog.dismiss();
                            }
                        })
                        .create();

                myUpdatingDialogBox.create();
                myUpdatingDialogBox.show();
                //The key argument here must match that used in the other activity
            }
        }
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

    public void saveTrack(Track t) {
        Call<Track> call = api.saveTrack(t);

        call.enqueue(new Callback<Track>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {

                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Track newTrack = response.body();

                trackList.add(newTrack);

                recyclerView.getAdapter().notifyItemInserted(trackList.size()-1);
                recyclerView.smoothScrollToPosition(trackList.size());
            }
           @EverythingIsNonNull
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unable to submit post to API.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == MainActivity.RESULT_OK) {
                String id = data.getStringExtra("identifier");
                String title = data.getStringExtra("title");
                String singer = data.getStringExtra("singer");

                Track posted_track = new Track(id, title, singer);
                saveTrack(posted_track);
            }
            if (resultCode == MainActivity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Post cancelled",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void updateTrack(final Track t, final int position) {
        Call<Void> call = api.updateTrack(t);

        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                trackList.get(position).setTitle(t.getTitle());
                trackList.get(position).setSinger(t.getSinger());

                // define an adapter
                recyclerView.getAdapter().notifyItemChanged(position);
                recyclerView.smoothScrollToPosition(position);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unable to submit put to API.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void deleteTrack(final String id, final int position) {
        Call<Void> call = api.deleteTrack(id);

        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                trackList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unable to submit delete to API.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
