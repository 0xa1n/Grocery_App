package com.app.groccery_app.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.groccery_app.R;
import com.app.groccery_app.adapters.GrocceryListAdapterWithAction;
import com.app.groccery_app.schemes.GrocceryItem;
import com.app.groccery_app.schemes.OnElementDeletedListener;
import com.app.groccery_app.utils.dateUtils;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GrocceryListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrocceryListView extends Fragment implements OnElementDeletedListener {

    private int mDay;
    private int mMonth;
    private int mYear;



    TextView textViewCurrentDate;
    Button onAddGroceryButton;
    Button onSaveGroceriesButton;

    EditText groceryNameEditText;
    EditText groceryAmountEditText;
    EditText groceryPriceEditText;

    GrocceryListAdapterWithAction adapterWithAction;

    RecyclerView recyclerView;

    public LinkedList<GrocceryItem> grocceryItems = new LinkedList<GrocceryItem>();

    private NavController navigation;

    MainApplication app;

    public GrocceryListView() {
        // Required empty public constructor
    }

    public static GrocceryListView newInstance(int day, int month, int year) {
        GrocceryListView fragment = new GrocceryListView();
        Bundle args = new Bundle();
        args.putInt("DAY", day);
        args.putInt("MONTH", month);
        args.putInt("YEAR",year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDay = getArguments().getInt("DAY");
            mMonth = getArguments().getInt("MONTH");
            mYear = getArguments().getInt("YEAR");

//            textViewCurrentDate.setText(dateUtils.convertToDate(mDay, mMonth, mYear).toString());
            System.out.println(dateUtils.convertToDate(mDay, mMonth, mYear));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groccery_list_view, container, false);

        textViewCurrentDate = view.findViewById(R.id.textViewCurrentDate);
        onAddGroceryButton = view.findViewById(R.id.addGroceryButton);
        onSaveGroceriesButton = view.findViewById(R.id.saveGroceriesButton);

        groceryNameEditText = view.findViewById(R.id.editTextGroceryName);
        groceryAmountEditText = view.findViewById(R.id.editTextGroceryAmount);
        groceryPriceEditText = view.findViewById(R.id.editTextGroceryPrice);

        recyclerView = view.findViewById(R.id.groceriesRecyclerView);

        navigation = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        adapterWithAction = new GrocceryListAdapterWithAction(grocceryItems, this);
        recyclerView.setAdapter(adapterWithAction);

        String _d = dateUtils.convertToDate(mDay, mMonth, mYear);

        if(State.hasGroceries(_d)){
            grocceryItems.clear();

            System.out.println(State.groceriesDB);
            for (int i = 0; i < State.groceriesDB.get(_d).size(); i++) {
                grocceryItems.addLast(State.groceriesDB.get(_d).get(i));
            }

            adapterWithAction.notifyDataSetChanged();
        }

        onAddGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(groceryNameEditText.getText() == null || groceryAmountEditText.getText() == null || groceryPriceEditText == null){
                    return;
                }

                grocceryItems.addLast(new GrocceryItem(
                        groceryNameEditText.getText().toString(),
                        Integer.parseInt(groceryAmountEditText.getText().toString()),
                        Integer.parseInt(groceryPriceEditText.getText().toString())
                ));

                if(adapterWithAction != null)
                    adapterWithAction.notifyDataSetChanged();

                System.out.println(grocceryItems.size());
            }
        });

        onSaveGroceriesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if(grocceryItems.size() > 0)
                    State.setGroceriesList(dateUtils.convertToDate(mDay, mMonth, mYear), grocceryItems);

                navigation.navigate(R.id.action_closeGrocceryList);
            }
        });

        textViewCurrentDate.setText(dateUtils.getDate(mDay, mMonth, mYear).toString());
    }

    @Override
    public void OnElementDeleted(int position) {
        if(grocceryItems.get(position) != null)
            grocceryItems.remove(position);

        if(adapterWithAction != null)
            adapterWithAction.notifyDataSetChanged();
    }
}