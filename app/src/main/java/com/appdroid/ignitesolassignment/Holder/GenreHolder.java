package com.appdroid.ignitesolassignment.Holder;

import androidx.recyclerview.widget.RecyclerView;

public class GenreHolder {
    int image;
    String genreName;

    public GenreHolder(int image, String genreName) {
        this.image = image;
        this.genreName = genreName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
