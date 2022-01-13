package com.app.groccery_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.groccery_app.R;
import com.app.groccery_app.schemes.GrocceryItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class GrocceryListAdapter extends RecyclerView.Adapter<GrocceryListAdapter.ViewHolder> {
        private LinkedList<GrocceryItem> localDataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewName;
            private final TextView textViewAmount;
            private final TextView textViewPrice;

            public ViewHolder(View view){
                super(view);

                textViewName = (TextView) view.findViewById(R.id.list_grocceryName);
                textViewAmount = (TextView) view.findViewById(R.id.list_grocceryAmount);
                textViewPrice = (TextView) view.findViewById(R.id.list_grocceryPrice);
            }

            public TextView getTextViewName() {
                return textViewName;
            }
            public TextView getTextViewAmount() {return textViewAmount;}
            public TextView getTextViewPrice() {return  textViewPrice;}


        }

        public GrocceryListAdapter( LinkedList<GrocceryItem> dataSet){
            localDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position){
            if(localDataSet.get(position) != null){
                viewHolder.getTextViewName().setText("Name : " + localDataSet.get(position).grocceryName);
                viewHolder.getTextViewAmount().setText("Amount : " + String.valueOf(localDataSet.get(position).grocceryAmount));
                viewHolder.getTextViewPrice().setText("Price : " + String.valueOf(localDataSet.get(position).grocceryPrice) + "$");
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
