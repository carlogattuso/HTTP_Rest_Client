package com.example.http_rest_client;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private JSON_API api;
    private List<Post> values;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView id;
        public View layout;
        public Button delete;
        public  Button image;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.firstLine);
            id = (TextView) v.findViewById(R.id.secondLine);
            delete = (Button) v.findViewById(R.id.delete);
        }
    }

    public void add(int position, Post item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Post> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/eperezcosano/JSON-server/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(JSON_API.class);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Post post = values.get(position);
        holder.title.setText(post.getTitle());
        holder.id.setText(String.valueOf("Id: " + post.getId()));
        holder.delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost(post.getId(),holder.getAdapterPosition());
            }
        });
    }

    public void deletePost(final int id, final int position_to_remove) {
        Call<Void> call = api.deletePost(id);

        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()) {
                    remove(position_to_remove);
                    return;
                }

                remove(position_to_remove);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
