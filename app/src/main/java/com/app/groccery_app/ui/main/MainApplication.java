package com.app.groccery_app.ui.main;

import android.app.Application;

import com.app.groccery_app.schemes.GrocceryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainApplication extends Application {

    public HashMap<Date, ArrayList<GrocceryItem>> groceriesDB = new HashMap<Date, ArrayList<GrocceryItem>>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ArrayList<GrocceryItem> getGroceriesList(Date _date) {
        if(hasGroceries(_date)){
            return  groceriesDB.get(_date);
        }

        return  new ArrayList<GrocceryItem>();
    }

    public void setGroceriesList(Date _date, ArrayList<GrocceryItem> arr){
        if(!hasGroceries(_date)){
            groceriesDB.put(_date, arr);
        }else {
            groceriesDB.get(_date).clear();

            for (int i = 0; i < groceriesDB.get(_date).size(); i++){
                groceriesDB.get(_date).set(i, arr.get(i));
            }
        }
    }


    public boolean hasGroceries(Date _date) {
        if(groceriesDB.get(_date) == null){
            return  false;
        }

        if(groceriesDB.get(_date).size() == 0){
            return false;
        }

        return true;
    }
}
