package com.example.http_rest_client;

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

import java.text.ParseException;
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
    private List<Track> trackList;
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
            }
        });
    }

    /*public void getProfile(){
        Call<Profile> call = api.getProfile();

        call.enqueue(new Callback<Profile>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(!response.isSuccessful())
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

                Profile profile = response.body();

                Toast toast = Toast.makeText(getApplicationContext(),
                        String.valueOf("Welcome "+profile.getName()),
                        Toast.LENGTH_SHORT);
                toast.show();

                setTitle(String.valueOf("Welcome "+profile.getName()));

            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unexpected error",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    */

    public void getTracks(){
        Call<List<Track>> call = api.getTracks();

        call.enqueue(new Callback<List<Track>>() {
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

    /*public void sendPost(int id, String title) {
        Call<Post> call = api.savePost(id,title);

        call.enqueue(new Callback<Post>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+response.code(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                int size_recycler = mAdapter.getItemCount();

                Post newPost = response.body();

                postList.add(newPost);

                recyclerView.getAdapter().notifyItemInserted(postList.size());
                recyclerView.smoothScrollToPosition(postList.size());
            }
           @EverythingIsNonNull
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Unable to submit post to API.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }*/

    /*public void updatePost(int id_path, final int id, final String title) {
        Call<Post> call = api.updatePost(id_path,id,title);

        call.enqueue(new Callback<Post>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()) {
                    return;
                }

                int position = 0;

                for(int i=0;i<postList.size();i++){
                    if(postList.get(i).getId()==id){
                        postList.get(i).setTitle(title);
                        position = i;
                    }
                }
                // define an adapter
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(position);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.new_post) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.post_layout, null);
            final AppCompatEditText input = (AppCompatEditText) view.findViewById(R.id.editText);
            final TextView id_text = (TextView) view.findViewById(R.id.text_id);
            if (postList.size() == 0) {
                id_text.append(" ");
                id_text.append(String.valueOf(26));
            }
            else {
                id_text.append(" ");
                id_text.append(String.valueOf(postList.get(postList.size()-1).getId()+1));
            }
            builder.setView(view);
            builder.setPositiveButton("POST",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            final AlertDialog dialog = builder.create();
            dialog.show();

            //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;

                    //Do stuff, possibly set wantToCloseDialog to true then...
                    String title = input.getText().toString();
                    if(title.equals("")) {
                        Toast.makeText(MainActivity.this, "Empty title" + input.getText().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (postList.size() == 0) {
                            sendPost(26, input.getText().toString());
                            wantToCloseDialog=true;
                        }
                        else {
                            sendPost(postList.get(postList.size()-1).getId()+1, input.getText().toString());
                            wantToCloseDialog=true;
                        }
                    }
                    if(wantToCloseDialog)
                        dialog.dismiss();
                }
            });

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(MainActivity.this, "Cancelled" + input.getText().toString(),
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        }
        if(id == R.id.modify_post){
            if(postList.size()==0){
                Toast.makeText(MainActivity.this, "No posts to modify",
                        Toast.LENGTH_LONG).show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.put_layout, null);
                final AppCompatEditText input = (AppCompatEditText) view.findViewById(R.id.editText_modify);
                final AppCompatEditText input_id = (AppCompatEditText) view.findViewById(R.id.editText_id);

                builder.setView(view);
                builder.setPositiveButton("PUT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Boolean wantToCloseDialog = false;

                        //Do stuff, possibly set wantToCloseDialog to true then...
                        String title = input.getText().toString();
                        String id_string = input_id.getText().toString();

                        if (title.equals("")&&id_string.equals("")) {
                            Toast.makeText(MainActivity.this, "Empty title and ID",
                                    Toast.LENGTH_LONG).show();
                        } else if (title.equals("")) {
                            Toast.makeText(MainActivity.this, "Empty title",
                                    Toast.LENGTH_LONG).show();
                        } else if (id_string.equals("")) {
                            Toast.makeText(MainActivity.this, "Empty ID",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                int id_put = Integer.parseInt(id_string);
                                Boolean found = false;
                                for (Post p : postList) {
                                    if (p.getId() == id_put) found = true;
                                }
                                if (found) {
                                    updatePost(id_put, id_put, title);
                                    wantToCloseDialog = true;
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Non-existent identifier",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(MainActivity.this, "ID has to be an Integer",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }
                });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Cancelled",
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
