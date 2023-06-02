package com.appdroid.ignitesolassignment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appdroid.ignitesolassignment.Adapter.BooksAdapter;
import com.appdroid.ignitesolassignment.Holder.BookHolder;
import com.appdroid.ignitesolassignment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView booksRecyclerView;
    private List<BookHolder> bookList;
    public static BooksAdapter booksAdapter;
    private RelativeLayout progress_layout;
    private NestedScrollView scrollViewLayout;
    private ImageView backBtn;
    private TextView genreTitle;
    private androidx.appcompat.widget.SearchView searchView;

    String genreName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        genreName = (String) getIntent().getStringExtra("genre");

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        genreTitle = findViewById(R.id.genreName);
        backBtn = findViewById(R.id.backBtn);
        scrollViewLayout = findViewById(R.id.scrollViewLayout);
        progress_layout = findViewById(R.id.progress_layout);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BookListActivity.this,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        booksRecyclerView.setLayoutManager(gridLayoutManager);
        bookList = new ArrayList<>();
        booksAdapter = new BooksAdapter(bookList, BookListActivity.this);
        booksRecyclerView.setAdapter(booksAdapter);

        genreTitle.setText(genreName);

        booksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Check if the last item is visible and not currently loading more data
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    // End of items reached, fetch new data
                    fetchMoreBooksData(genreName);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        prepareLink(genreName);

        /*if (genreName.toLowerCase().equals("fiction")){
            getBooks("https://gutendex.com/books/?topic=fiction");
        }else if (genreName.toLowerCase().equals("drama")){
            getBooks("https://gutendex.com/books/?topic=drama");
        }else if (genreName.toLowerCase().equals("humor")){
            getBooks("https://gutendex.com/books/?topic=humor");
        }else if (genreName.toLowerCase().equals("politics")){
            getBooks("https://gutendex.com/books/?topic=politics");
        }else if (genreName.toLowerCase().equals("philosophy")){
            getBooks("https://gutendex.com/books/?topic=philosophy");
        }else if (genreName.toLowerCase().equals("history")){
            getBooks("https://gutendex.com/books/?topic=history");
        }else if (genreName.toLowerCase().equals("adventure")){
            getBooks("https://gutendex.com/books/?topic=adventure");
        }*/

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void fetchMoreBooksData(String genreName) {
        String nextPageUrl = "https://gutendex.com/books/?page=2&topic="+ genreName.toLowerCase();
        getBooks(nextPageUrl);
    }

    private void prepareLink(String genreName) {
        String genreNameLowerCase = genreName.toLowerCase();
        String url = "https://gutendex.com/books/?topic=";

        switch (genreNameLowerCase) {
            case "fiction":
                url += "fiction";
                break;
            case "drama":
                url += "drama";
                break;
            case "humor":
                url += "humor";
                break;
            case "politics":
                url += "politics";
                break;
            case "philosophy":
                url += "philosophy";
                break;
            case "history":
                url += "history";
                break;
            case "adventure":
                url += "adventure";
                break;
            default:
                Toast.makeText(this, "Data not found...", Toast.LENGTH_SHORT).show();
                break;
        }

        getBooks(url);
    }

    private void searchList(String s) {
        String searchUrl = "https://gutendex.com/books/?search="+s;
        progress_layout.setVisibility(View.VISIBLE);

        bookList.clear();
        scrollViewLayout.setVisibility(View.GONE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray resultArray = response.getJSONArray("results");

                    for (int i = 0; i < resultArray.length(); i++){
                        BookHolder bookHolder = new BookHolder();
                        JSONObject bookObjectData = resultArray.getJSONObject(i);

                        bookHolder.setTitle(bookObjectData.getString("title"));

                        JSONArray authorsArray = bookObjectData.getJSONArray("authors");
                        JSONObject authorObject = authorsArray.getJSONObject(0);
                        bookHolder.setAuthorName(authorObject.getString("name"));

                        JSONObject formatObject = bookObjectData.getJSONObject("formats");
                        bookHolder.setCoverImage(formatObject.getString("image/jpeg"));
                        bookHolder.setHtmlLink(formatObject.getString("text/html"));
                        if (formatObject.has("text/plain")) {
                            bookHolder.setPlainLink(formatObject.getString("text/plain"));
                        }else if (formatObject.has("text/plain; charset=us-ascii")){
                            bookHolder.setPlainLink(formatObject.getString("text/plain; charset=us-ascii"));
                        }else if (formatObject.has("text/plain; charset=utf-8")){
                            bookHolder.setPlainLink(formatObject.getString("text/plain; charset=utf-8"));
                        }
                        booksAdapter.notifyDataSetChanged();

                        bookList.add(bookHolder);
                        scrollViewLayout.setVisibility(View.VISIBLE);
                        progress_layout.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(BookListActivity.this).add(request);

    }

    private void getBooks(String URL) {

        progress_layout.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray resultArray = response.getJSONArray("results");

                    for (int i = 0; i < resultArray.length(); i++){
                        BookHolder bookHolder = new BookHolder();
                        JSONObject bookObjectData = resultArray.getJSONObject(i);

                        bookHolder.setTitle(bookObjectData.getString("title"));

                        JSONArray authorsArray = bookObjectData.getJSONArray("authors");
                        JSONObject authorObject = authorsArray.getJSONObject(0);
                        bookHolder.setAuthorName(authorObject.getString("name"));

                        JSONObject formatObject = bookObjectData.getJSONObject("formats");
                        bookHolder.setCoverImage(formatObject.getString("image/jpeg"));
                        bookHolder.setHtmlLink(formatObject.getString("text/html"));
                        if (formatObject.has("text/plain")) {
                            bookHolder.setPlainLink(formatObject.getString("text/plain"));
                        }else if (formatObject.has("text/plain; charset=us-ascii")){
                            bookHolder.setPlainLink(formatObject.getString("text/plain; charset=us-ascii"));
                        }else if (formatObject.has("text/plain; charset=utf-8")){
                            bookHolder.setPlainLink(formatObject.getString("text/plain; charset=utf-8"));
                        }
                        booksAdapter.notifyDataSetChanged();

                        bookList.add(bookHolder);
                        scrollViewLayout.setVisibility(View.VISIBLE);
                        progress_layout.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(BookListActivity.this).add(request);

    }
};