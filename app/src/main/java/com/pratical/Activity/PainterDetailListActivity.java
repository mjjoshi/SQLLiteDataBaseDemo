package com.pratical.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.pratical.Adapter.ImagePagerAdapter;
import com.pratical.Database.DataBaseHelper;
import com.pratical.Database.PaintingList;
import com.pratical.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PainterDetailListActivity extends AppCompatActivity {
    private List<PaintingList> paintingLists;
    private ArrayList<String> imageLists;
    private ViewPager viewpagerImage;
    int position;
    int usersId;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_practical_list);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        usersId = intent.getIntExtra("usersId", 0);
        try {
            dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        paintingLists = dataBaseHelper.getPaintingList(position);
        imageLists = new ArrayList<>();
        for (int i = 0; i < paintingLists.size(); i++) {
            imageLists.add(paintingLists.get(i).getPaintingImage());

        }
        initcontrol();
    }

    private void initcontrol() {
        viewpagerImage = findViewById(R.id.viewpagerImage);
        viewpagerImage.setAdapter(new ImagePagerAdapter(this, paintingLists, usersId));
    }


}

