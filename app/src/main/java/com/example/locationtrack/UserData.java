package com.example.locationtrack;

public class UserData {
    private String name;
    private String emailAddress;
    private Long busNumber;
    private String place;
    private String rollNumber;
    private String year;

    public UserData() {}

    public UserData(String name, String emailAddress, Long busNumber, String place, String rollNumber, String year) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.busNumber = busNumber;
        this.place = place;
        this.rollNumber = rollNumber;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(Long busNumber) {
        this.busNumber = busNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
