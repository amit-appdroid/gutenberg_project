package com.appdroid.ignitesolassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.appdroid.ignitesolassignment.Adapter.GenreAdapter;
import com.appdroid.ignitesolassignment.Holder.GenreHolder;
import com.appdroid.ignitesolassignment.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<GenreHolder> list;
    RecyclerView genreRecyclerView;
    GenreAdapter genreAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genreRecyclerView = findViewById(R.id.genreRecyclerView);
        list = new ArrayList<>();
        list = getData();
        genreAdapter = new GenreAdapter(list, MainActivity.this);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        genreRecyclerView.setAdapter(genreAdapter);

    }

    private List<GenreHolder> getData()
    {
        List<GenreHolder> list = new ArrayList<>();
        list.add(new GenreHolder(R.drawable.ic_fiction,
                getString(R.string.fiction)));

        list.add(new GenreHolder(R.drawable.ic_drama,
                getString(R.string.drama)));

        list.add(new GenreHolder(R.drawable.ic_humour,
                getString(R.string.humor)));

        list.add(new GenreHolder(R.drawable.ic_politics,
                getString(R.string.politics)));

        list.add(new GenreHolder(R.drawable.ic_philosophy,
                getString(R.string.philosophy)));

        list.add(new GenreHolder(R.drawable.ic_history,
                getString(R.string.history)));

        list.add(new GenreHolder(R.drawable.ic_adventure,
                getString(R.string.adventure)));

        return list;
    }
}