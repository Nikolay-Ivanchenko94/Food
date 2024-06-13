package com.example.food.foods;

public class Foods {
    private Category category;
    private Drinks drinks;
    private Cookies cookies;
    private Groceries groceries;
    private Delivery delivery;



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Drinks getDrinks() {
        return drinks;
    }

    public void setDrinks(Drinks drinks) {
        this.drinks = drinks;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public Groceries getGroceries() {
        return groceries;
    }

    public void setGroceries(Groceries groceries) {
        this.groceries = groceries;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Foods(Category category, Drinks drinks, Cookies cookies, Groceries groceries, Delivery delivery) {
        this.category = category;
        this.drinks = drinks;
        this.cookies = cookies;
        this.groceries = groceries;
        this.delivery = delivery;
    }

}
