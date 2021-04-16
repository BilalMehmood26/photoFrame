package com.example.naturephotoframe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturephotoframe.Model.MyWorkModel;
import com.example.naturephotoframe.R;

import java.util.ArrayList;

public class MyWorkAdapter extends  RecyclerView.Adapter<MyWorkAdapter.MyHolder> {


    public Context context;
    ArrayList<MyWorkModel> models;

    public MyWorkAdapter(Context context, ArrayList<MyWorkModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_workitem, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.imgview.setImageBitmap(models.get(position).getImgwork());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgview;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.imgview = itemView.findViewById(R.id.imagework);
        }
    }
}
