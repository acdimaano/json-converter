package com.example.docconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a simple "document" made up of metadata fields plus a body.
 * This is the object that gets serialized to / deserialized from JSON.
 */
public class Document {

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("date")
    private String date;

    @JsonProperty("content")
    private String content;

    public Document() {
    }

    public Document(String title, String author, String date, String content) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
