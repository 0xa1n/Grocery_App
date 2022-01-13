package com.app.groccery_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.groccery_app.R;
import com.app.groccery_app.schemes.GrocceryItem;
import com.app.groccery_app.schemes.OnElementDeletedListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class GrocceryListAdapterWithAction extends RecyclerView.Adapter<GrocceryListAdapterWithAction.GRViewHolder> {
    private final LinkedList<GrocceryItem> localDataSet;

    OnElementDeletedListener onRemoveListener;

    public static class GRViewHolder extends RecyclerView.ViewHolder {
        private final TextView groceryNameTextView;
        private final TextView groceryAmountTextView;
        private final TextView groceryPriceTextView;
        private final Button deleteButton;

        public GRViewHolder(View view){
            super(view);

            groceryNameTextView = (TextView) view.findViewById(R.id.element_grocceryName);
            groceryAmountTextView = (TextView) view.findViewById(R.id.element_grocceryAmount);
            groceryPriceTextView = (TextView) view.findViewById(R.id.element_grocceryPrice);
            deleteButton = (Button) view.findViewById(R.id.deleteElementButton);
        }

        public TextView getGroceryNameTextView() {
            return this.groceryNameTextView;
        }

        public TextView getGroceryAmountTextView() {
            return this.groceryAmountTextView;
        }

        public TextView getGroceryPriceTextView() {
            return this.groceryPriceTextView;
        }

        public Button getDeleteButton() {
            return this.deleteButton;
        }
    }

    public GrocceryListAdapterWithAction(LinkedList<GrocceryItem> dataSet, OnElementDeletedListener listener){
        this.localDataSet = dataSet;
        this.onRemoveListener = listener;
    }

    @Override
    public GrocceryListAdapterWithAction.GRViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_with_action, viewGroup, false);

        return new GrocceryListAdapterWithAction.GRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrocceryListAdapterWithAction.GRViewHolder viewHolder, final int position){
        if(onRemoveListener != null) {
            viewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRemoveListener.OnElementDeleted(viewHolder.getAdapterPosition());
                }
            });
        }

        if(localDataSet.get(position) != null){
            viewHolder.getGroceryNameTextView().setText("Name: "+localDataSet.get(position).grocceryName);
            viewHolder.getGroceryAmountTextView().setText("Amount: " + String.valueOf(localDataSet.get(position).grocceryAmount));
            viewHolder.getGroceryPriceTextView().setText("Price: "+String.valueOf(localDataSet.get(position).grocceryPrice) + "$");
        }
    }


    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
