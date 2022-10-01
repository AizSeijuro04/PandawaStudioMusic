package com.example.pandawastudiomusic.Model;

import com.google.firebase.Timestamp;

public class BookingInformation {
    private String cityBook, customerName, customerPhone, time, studiosID, studiosName,studioID, studioName, studioAddress;
    private Long slot;
    private Timestamp timestamp;
    private boolean done;

    public BookingInformation() {
    }

    public BookingInformation(String customerName, String customerPhone, String time, String studiosID, String studiosName, String studioID, String studioName, String studioAddress, Long slot) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.time = time;
        this.studiosID = studiosID;
        this.studiosName = studiosName;
        this.studioID = studioID;
        this.studioName = studioName;
        this.studioAddress = studioAddress;
        this.slot = slot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStudiosID() {
        return studiosID;
    }

    public void setStudiosID(String studiosID) {
        this.studiosID = studiosID;
    }

    public String getStudiosName() {
        return studiosName;
    }

    public void setStudiosName(String studiosName) {
        this.studiosName = studiosName;
    }

    public String getStudioID() {
        return studioID;
    }

    public void setStudioID(String studioID) {
        this.studioID = studioID;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getStudioAddress() {
        return studioAddress;
    }

    public void setStudioAddress(String studioAddress) {
        this.studioAddress = studioAddress;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getCityBook() {
        return cityBook;
    }

    public void setCityBook(String cityBook) {
        this.cityBook = cityBook;
    }
}
