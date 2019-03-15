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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_track_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        final Intent adapter_intent = getIntent();

        final String id = adapter_intent.getStringExtra("id");
        final String title = adapter_intent.getStringExtra("title");
        final String singer = adapter_intent.getStringExtra("singer");

        final Intent intent_main = new Intent(TrackDetailsActivity.this,MainActivity.class);
        final Intent update_intent = new Intent(TrackDetailsActivity.this,UpdateTrackActivity.class);

        final TextView id_text = (TextView) findViewById(R.id.view_track_id_field);
        TextView title_text = (TextView) findViewById(R.id.view_track_title_field);
        TextView singer_text = (TextView) findViewById(R.id.view_track_singer_field);

        id_text.setText(id);
        title_text.setText(title);
        singer_text.setText(singer);

        Button edit_button = (Button) findViewById(R.id.edit_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button info_button = (Button) findViewById(R.id.info_button);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(TrackDetailsActivity.this)
                        //set message, title, and icon

                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete " + title + "-" + singer + "?")
                        .setPositiveButton("Delete Track", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                deleteTrack(id);
                                dialog.dismiss();
                                startActivity(intent_main);
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
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_intent.putExtra("id_edit",id);
                startActivity(update_intent);
            }
        });
    }

    public void deleteTrack(final String id) {
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