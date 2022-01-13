package com.app.groccery_app.ui.main;

import com.app.groccery_app.schemes.GrocceryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class State {
    public static HashMap<String, LinkedList<GrocceryItem>> groceriesDB = new HashMap<String, LinkedList<GrocceryItem>>();

    public static LinkedList<GrocceryItem> getGroceriesList(String _date) {
        return  groceriesDB.get(_date);
    }

    public static void setGroceriesList(String _date, LinkedList<GrocceryItem> arr){
        groceriesDB.put(_date, arr);
    }


    public static boolean hasGroceries(String _date) {
        if(groceriesDB.get(_date) == null){
            return  false;
        }

        if(groceriesDB.get(_date).size() == 0){
            return false;
        }

        return true;
    }
}
