package com.example.mystudy;

public class TaskModel {
    String recycler_title;
    String recycler_description;
    String recycler_date;
    String recycler_time;

    public TaskModel(String recycler_title, String recycler_description, String recycler_date, String recycler_time) {
        this.recycler_title = recycler_title;
        this.recycler_description = recycler_description;
        this.recycler_date = recycler_date;
        this.recycler_time = recycler_time;
    }
    public String getRecycler_title() { return recycler_title; }

    public void setRecycler_title(String recycler_title) {this.recycler_title =  recycler_title; }

    public String getRecycler_description() { return recycler_description; }

    public void setRecycler_description(String recycler_description) {this.recycler_description = recycler_description; }

    public String getRecycler_date() { return  recycler_date; }

    public void setRecycler_date(String recycler_date) { this.recycler_date =  recycler_date; }

    public String getRecycler_time() { return recycler_time; }

    public void setRecycler_time(String recycler_time) { this.recycler_time = recycler_time; }

}
