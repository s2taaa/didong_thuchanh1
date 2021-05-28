package com.example.thuchanh1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NameViewHolder> {
    private List<User> users;
    private LayoutInflater inflater;
    private Context context;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public UserAdapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.line_item,parent,false);
       return new NameViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.NameViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_email.setText("Age : " +user.getEmail());
        holder.txt_name.setText("Name : "+user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        private UserAdapter adapter;
        private TextView txt_id,txt_name,txt_email;
        public NameViewHolder(@NonNull View itemView , UserAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_email = itemView.findViewById(R.id.txt_email);
        }
    }
}
