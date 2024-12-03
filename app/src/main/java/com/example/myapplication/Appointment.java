package com.example.myapplication;

public class Appointment {
    private String title;
    private String date;
    private String address;
    private String time;
    private String phone;
    private int userId;

    public Appointment(String title, String date,String time, String address, String phone, int userId) {
        this.title = title;
        this.date = date;
        this.address = address;
        this.phone = phone;
        this.time=time;
        this.userId = userId;
    }
    public Appointment(String date, String address, String phone ) {
        this.date = date;
        this.address = address;
        this.phone = phone;
        this.time=time;


    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }




    @Override
    public String toString() {
        return "Appointment{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                '}';
    }
}
