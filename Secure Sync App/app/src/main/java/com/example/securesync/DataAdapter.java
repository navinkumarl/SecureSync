package com.example.securesync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private ArrayList<ExampleItem> data_list;
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name,mobile_no,email;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name=itemView.findViewById(R.id.users_name);
            mobile_no=itemView.findViewById(R.id.users_no);
            email=itemView.findViewById(R.id.users_mail);
        }
    }

    public DataAdapter(ArrayList<ExampleItem> data) {
        data_list=data;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_item,parent,false);
        DataViewHolder dataViewHolder=new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        ExampleItem exampleItem=data_list.get(position);
        holder.user_name.setText(exampleItem.getUser_name());
        holder.mobile_no.setText(exampleItem.getMobile_no());
        holder.email.setText(exampleItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }
}
