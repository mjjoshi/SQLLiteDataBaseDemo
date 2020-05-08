package com.pratical.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pratical.Database.DataBaseHelper;
import com.pratical.Database.UserModel;
import com.pratical.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton txtbtnLogin;
    private AppCompatEditText txtUserPassword;
    private AppCompatEditText txtUserName;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private DataBaseHelper dataBaseHelper;
    private List<UserModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initControl();
        setClickListner();

    }

    private void initControl() {
        txtbtnLogin = findViewById(R.id.txtbtnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserPassword = findViewById(R.id.txtUserPassword);
        try {
            dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    private void setClickListner() {
        txtbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUserName.getText().toString().trim();
                String userPassword = txtUserPassword.getText().toString().trim();
                dataList = dataBaseHelper.getUserDeatils(userName, userPassword);
                if (dataList.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, PainterListActivity.class);
                    intent.putExtra("userId", dataList.get(0).getUserId());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "UserName & Password does not match", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Intent intent = new Intent(MainActivity.this, PainterListActivity.class);
                // startActivity(intent);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
