package com.example.myapplication;

public class Appointment {
    private String title;
    private String date;

    public Appointment(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
