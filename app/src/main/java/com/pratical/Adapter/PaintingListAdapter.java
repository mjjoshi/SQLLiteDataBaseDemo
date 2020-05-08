package com.pratical.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratical.Database.PaintingList;
import com.pratical.R;

import java.util.List;

public class PaintingListAdapter extends RecyclerView.Adapter<PaintingListAdapter.MyViewHolder> {
    private List<PaintingList> dataList;
    private Context context;


    public PaintingListAdapter(Context context, List<PaintingList> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public PaintingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_painting_list, parent, false);
        return new PaintingListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaintingListAdapter.MyViewHolder holder, final int position) {
        final PaintingList data = dataList.get(position);
        Glide.with(context)
                .load(data.getPaintingImage())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(holder.imgPainterImage);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgPainterImage;
        MyViewHolder(View view) {
            super(view);
            imgPainterImage = view.findViewById(R.id.imgPainterImage);


        }
    }

}