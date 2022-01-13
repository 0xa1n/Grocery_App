package com.app.groccery_app.schemes;

public class GrocceryItem {
    public String grocceryName;
    public int grocceryAmount;
    public int grocceryPrice;

    public GrocceryItem(String _grocceryName, int _grocceryAmount, int _grocceryPrice){
        this.grocceryName = _grocceryName;
        this.grocceryAmount = _grocceryAmount;
        this.grocceryPrice = _grocceryPrice;
    }
}
