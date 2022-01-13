package com.app.groccery_app.ui.main;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.app.groccery_app.R;
import com.app.groccery_app.adapters.GrocceryListAdapter;
import com.app.groccery_app.schemes.GrocceryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

import com.app.groccery_app.utils.dateUtils;

public class MainFragment extends Fragment {

    public LinkedList<GrocceryItem> grocceryItems = new LinkedList<GrocceryItem>();

    private RecyclerView recyclerView;
    private DatePicker datePicker;
    private Button editGrocceryListButton;
    private NavController navigation;
    private TextView totalProductsText;

    GrocceryListAdapter adapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycle_view);
        datePicker = view.findViewById(R.id.dataPicker);
        editGrocceryListButton = view.findViewById(R.id.editGrocceryListButton);
        totalProductsText = view.findViewById(R.id.textViewTotalProducts);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigation = NavHostFragment.findNavController(this);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                String _date = dateUtils.convertToDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());

                if(State.hasGroceries(_date)){
                    grocceryItems.clear();

                    System.out.println(State.groceriesDB);
                    for (int y = 0; y < State.groceriesDB.get(_date).size(); y++) {
                        grocceryItems.addLast(State.groceriesDB.get(_date).get(y));
                    }

                    calculateTotalProducts();

                    adapter.notifyDataSetChanged();
                }else{
                    grocceryItems.clear();
                    calculateTotalProducts();

                    adapter.notifyDataSetChanged();
                }
            }
        });

        editGrocceryListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DAY", datePicker.getDayOfMonth());
                bundle.putInt("MONTH", datePicker.getMonth());
                bundle.putInt("YEAR", datePicker.getYear());

                navigation.navigate(R.id.action_openGrocceryList, bundle);
            }
        });

    }

    public void calculateTotalProducts() {
        if(totalProductsText == null){
            return;
        }

        String _d = dateUtils.convertToDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());

        if(!State.hasGroceries(_d)){
            if(totalProductsText.getText().toString() != "0"){
                totalProductsText.setText("Total Amount: " + "0");
            }

            return;
        }

        int total = 0;

        for(int i =0; i < State.groceriesDB.get(_d).size(); i++){
            total += State.groceriesDB.get(_d).get(i).grocceryAmount;
        }

        totalProductsText.setText("Total Amount: " + String.valueOf(total));
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new GrocceryListAdapter(grocceryItems);
        recyclerView.setAdapter(adapter);

        String _d = dateUtils.convertToDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());

        if(State.hasGroceries(_d)){
            System.out.println("Has groceries at date");

            grocceryItems.clear();

            System.out.println(State.groceriesDB);
            for (int i = 0; i < State.groceriesDB.get(_d).size(); i++) {
                grocceryItems.addLast(State.groceriesDB.get(_d).get(i));
            }

            adapter.notifyDataSetChanged();

            calculateTotalProducts();
        }

        System.out.println("Started!");
    }
}