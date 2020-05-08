package com.pratical.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pratical.Adapter.PainterListAdapter;
import com.pratical.Database.DataBaseHelper;
import com.pratical.Database.PainterList;
import com.pratical.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PainterListActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private List<PainterList> painterLists;
    private RecyclerView recylerPainterList;
    private PainterListAdapter painterListAdapter;
    private int userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_list);
        try {
            dataBaseHelper = new DataBaseHelper(getApplicationContext());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        painterLists = dataBaseHelper.getPainterName();
        userId = getIntent().getIntExtra("userId", 0);
        initControl();

    }

    private void initControl() {
        recylerPainterList = findViewById(R.id.recylerPainterList);
        recylerPainterList.setHasFixedSize(false);
        LinearLayoutManager llmBasic = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        painterListAdapter = new PainterListAdapter(this, painterLists,userId);
        llmBasic.setOrientation(LinearLayoutManager.VERTICAL);
        recylerPainterList.setLayoutManager(llmBasic);
        recylerPainterList.setAdapter(painterListAdapter);
    }
}
