package com.example.oneapptorulethemall.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.util.*;

import java.io.Serializable;


@Entity (tableName = "notes")
public class Note implements Serializable {


    // table
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "title")
    private String title;

    @ColumnInfo(name= "subtitle")
    private String subtitle;

    @ColumnInfo(name= "date_time")
    private String dateTime;

    @ColumnInfo(name= "note_text")
    private String note_text;

    @ColumnInfo(name= "image_pah")
    private String image_pah;

    @ColumnInfo(name= "color")
    private String color;

    @ColumnInfo(name= "web_link")
    private String web_link;


    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }

    public String getImage_pah() {
        return image_pah;
    }

    public void setImage_pah(String image_pah) {
        this.image_pah = image_pah;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }

    @NonNull
    @Override
    public String toString() {
        return title + " : " + dateTime;
    }
}
