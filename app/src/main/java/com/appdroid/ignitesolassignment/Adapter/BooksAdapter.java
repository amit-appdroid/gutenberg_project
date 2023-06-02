package com.appdroid.ignitesolassignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appdroid.ignitesolassignment.Holder.BookHolder;
import com.appdroid.ignitesolassignment.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{

    List<BookHolder> bookList;
    Context context;

    public BooksAdapter(List<BookHolder> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent,false);
        return new BooksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        BookHolder bookHolder = bookList.get(position);

        holder.bookTitle.setText(bookHolder.getTitle());
        holder.authorName.setText(bookHolder.getAuthorName());
        Glide.with(context).load(bookHolder.getCoverImage()).into(holder.bookImage);

        holder.bookItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.background));

                if (!bookHolder.getHtmlLink().equals("") || bookHolder.getHtmlLink() != null) {
                    openCustomTab((Activity) context, customIntent.build(), Uri.parse(bookHolder.getHtmlLink()));
                }else{
                    openCustomTab((Activity) context, customIntent.build(), Uri.parse(bookHolder.getHtmlLink()));
                }
            }
        });
    }

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {

        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);

            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout bookItemLayout;
        ImageView bookImage;
        TextView bookTitle, authorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookItemLayout = itemView.findViewById(R.id.bookItemLayout);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            authorName = itemView.findViewById(R.id.authorName);

        }
    }
}
