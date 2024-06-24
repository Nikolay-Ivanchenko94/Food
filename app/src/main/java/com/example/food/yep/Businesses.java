package com.example.food.yep;

import java.util.List;

public class Businesses {
    private int id;
    private String alias;
    private String name;
    private String image_url;
    private String is_closed;
    private String url;
    private double review_count;
    List<Categories> categories;
    private String Coordinates;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    List<Transaction> transactions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(String is_closed) {
        this.is_closed = is_closed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getReview_count() {
        return review_count;
    }

    public void setReview_count(double review_count) {
        this.review_count = review_count;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }
    public Businesses(int id,String alias,String name,
                      String image_url,String is_closed,String url,
                      double review_count,String coordinates) {
        this.alias = alias;
        this.id = id;
        this.image_url = image_url;
        this.name = name;
        this.is_closed = is_closed;
        this.url = url;
        this.review_count = review_count;
    }
}
