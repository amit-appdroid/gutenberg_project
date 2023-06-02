package com.appdroid.ignitesolassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appdroid.ignitesolassignment.Activities.BookListActivity;
import com.appdroid.ignitesolassignment.Holder.GenreHolder;
import com.appdroid.ignitesolassignment.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{

    List<GenreHolder> list;
    Context context;

    public GenreAdapter(List<GenreHolder> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item, parent,false);
        return new GenreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenreHolder genreHolder = list.get(position);

        holder.genreName.setText(genreHolder.getGenreName());
        holder.genreIcon.setImageResource(genreHolder.getImage());

        holder.genreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookListActivity.class);
                i.putExtra("genre",genreHolder.getGenreName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout genreLayout;
        ImageView genreIcon;
        TextView genreName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            genreLayout = itemView.findViewById(R.id.genreLayout);
            genreIcon = itemView.findViewById(R.id.genreIcon);
            genreName = itemView.findViewById(R.id.genreName);

        }
    }
}
