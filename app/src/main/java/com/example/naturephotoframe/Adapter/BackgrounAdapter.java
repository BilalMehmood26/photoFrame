package com.example.naturephotoframe.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturephotoframe.Model.Frames;
import com.example.naturephotoframe.R;

import java.util.ArrayList;

import butterknife.BindView;

public class BackgrounAdapter extends RecyclerView.Adapter<BackgrounAdapter.ViewHolder> {
    ArrayList<Frames> stickerList = new ArrayList<>();
    private int selectedIndex = -1;

    public BackgrounAdapter(ArrayList<Frames> stickerList) {
        this.stickerList = stickerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frames_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frames frames = stickerList.get(position);
        holder.frameImages.setImageResource(frames.getFrames());

        if(selectedIndex == position)
            holder.check.setVisibility(View.VISIBLE);
        else
            holder.check.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView frameImages;
        @BindView(R.id.img_check)
        ImageView check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frameImages = itemView.findViewById(R.id.thumbnail_frame);
        }
    }
}
