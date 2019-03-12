package com.example.http_rest_client;

import android.os.Bundle;
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

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class TrackDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_track_layout);
        Intent intent = getIntent();
        String identifier = intent.getStringExtra("identifier");
        String title = intent.getStringExtra("title");
        String singer = intent.getStringExtra("singer");

        TextView identifier_text = (TextView) findViewById(R.id.id_field);
        TextView title_text = (TextView) findViewById(R.id.title_field);
        TextView singer_text = (TextView) findViewById(R.id.singer_field);

        identifier_text.setText(identifier);
        title_text.setText(title);
        singer_text.setText(singer);

    }
}