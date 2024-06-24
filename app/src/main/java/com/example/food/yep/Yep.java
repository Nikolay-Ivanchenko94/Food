package com.example.food.yep;

public class Yep {
    public char[] getString;
    private Businesses businesses;
    private Categories categories;
    private int coordinate;

    public char[] getGetString() {
        return getString;
    }

    public void setGetString(char[] getString) {
        this.getString = getString;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    private Transaction transaction;


    public Yep(Categories categories, Businesses businesses,Transaction transaction) {
       this.categories = categories;
        this.businesses = businesses;
        this.transaction = transaction;
    }
    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    private int weather;

    private String location;

    public Businesses getBusinesses() {
        return businesses;
    }
    public void  setBusinesses(Businesses businesses) {
        this.businesses= businesses;
    }


    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
