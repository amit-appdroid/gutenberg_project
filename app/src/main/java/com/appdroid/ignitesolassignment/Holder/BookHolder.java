package com.appdroid.ignitesolassignment.Holder;

public class BookHolder {

    String title, authorName, coverImage, htmlLink, plainLink;

    public BookHolder() {
    }

    public BookHolder(String title, String authorName, String coverImage, String htmlLink, String plainLink) {
        this.title = title;
        this.authorName = authorName;
        this.coverImage = coverImage;
        this.htmlLink = htmlLink;
        this.plainLink = plainLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public String getPlainLink() {
        return plainLink;
    }

    public void setPlainLink(String plainLink) {
        this.plainLink = plainLink;
    }
}
