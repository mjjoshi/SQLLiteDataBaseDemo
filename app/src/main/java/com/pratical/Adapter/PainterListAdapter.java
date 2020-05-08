package com.pratical.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pratical.Database.DataBaseHelper;
import com.pratical.Database.PainterList;
import com.pratical.Database.PaintingList;
import com.pratical.Activity.PainterDetailListActivity;
import com.pratical.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PainterListAdapter extends RecyclerView.Adapter<PainterListAdapter.MyViewHolder> {
    private List<PainterList> dataList;
    private List<PaintingList> paintingLists;
    private Context context;
    private PaintingListAdapter paintingListAdapter;
    private DataBaseHelper dataBaseHelper;
    private int usersId;


    public PainterListAdapter(Context context, List<PainterList> dataList, int userId) {
        this.dataList = dataList;
        this.context = context;
        this.usersId = userId;
    }

    @Override
    public PainterListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_painter_list, parent, false);
        return new PainterListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PainterListAdapter.MyViewHolder holder, final int position) {
        final PainterList data = dataList.get(position);
        holder.txtPainterName.setText(data.getPainterName());
        try {
            dataBaseHelper = new DataBaseHelper(context);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        paintingLists = dataBaseHelper.getPaintingList(position + 1);
        holder.rvImageList.setHasFixedSize(false);
        LinearLayoutManager llmBasic = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        paintingListAdapter = new PaintingListAdapter(context, paintingLists);
        llmBasic.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rvImageList.setLayoutManager(llmBasic);
        holder.rvImageList.setAdapter(paintingListAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PainterDetailListActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("paintingList", (Serializable) paintingLists);
                intent.putExtra("position", position + 1);
                intent.putExtra("usersId", usersId);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView txtPainterName;
        RecyclerView rvImageList;
        CardView cardPainterList;

        MyViewHolder(View view) {
            super(view);
            cardPainterList = view.findViewById(R.id.cardPainterList);
            txtPainterName = view.findViewById(R.id.txtPainterName);
            rvImageList = view.findViewById(R.id.rvImageList);

        }
    }

}