package com.pratical.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratical.Database.DataBaseHelper;
import com.pratical.Database.PaintingList;
import com.pratical.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private DataBaseHelper dataBaseHelper;
    private List<PaintingList> paintingLists;
    private ArrayList<String> likeList;
    private int usersid;

    public ImagePagerAdapter(Context context, List<PaintingList> arrayList, int usersId) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paintingLists = arrayList;
        this.usersid = usersId;

    }

    @Override
    public int getCount() {
        if (paintingLists != null) {
            return paintingLists.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        likeList = new ArrayList<>();
        View itemView = layoutInflater.inflate(R.layout.item_viewpager_layout, container, false);
        final AppCompatImageView imageView = itemView.findViewById(R.id.viewPagerItem_image1);
        final AppCompatImageView imgLike = itemView.findViewById(R.id.imgLike);
        Glide.with(context)
                .load(paintingLists.get(position).getPaintingImage())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(imageView);
        container.addView(itemView);
        try {
            dataBaseHelper = new DataBaseHelper(context);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        if (paintingLists.get(position).getLikeId() != null) {
            imgLike.setEnabled(false);
            imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_like_active));
            likeList.add(paintingLists.get(position).getLikeId());

        }
        imgLike.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_like_active));
                dataBaseHelper.updateLikeData(usersid, paintingLists.get(position).getPaintingId(), likeList);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}
