package com.pratical.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    //private static String DB_PATH = "/data/data/com.demo.dietplan/databases/";

    private static String DB_PATH = "";

    private static String DB_NAME = "PainterData.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 2);

        if (android.os.Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
        boolean dbexist = checkDataBase();

        if (dbexist) {
            try {
                openDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("База данных не существует!");
            createDataBase();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    public void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        // TODO Auto-generated method stub
        try {
            myDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE + SQLiteDatabase.CREATE_IF_NECESSARY);
            try {
                onCreate(myDataBase);
            } catch (Exception e) {
            }
            return myDataBase;
        } catch (Exception e) {
            if (myDataBase != null)
                myDataBase.close();
        }
        return myDataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<PainterList> getPainterName() {
        List<PainterList> list = new ArrayList<>();
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        String Query = "select * from Painter";
        Cursor c = myDataBase.rawQuery(Query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                PainterList model = new PainterList();
                model.setPainterId(c.getInt(0));
                model.setPainterName(c.getString(1));
                list.add(model);
                c.moveToNext();
            }
        }
        c.close();
        return list;
    }


    public List<UserModel> getUserDeatils(String name, String password) {
        List<UserModel> list = new ArrayList<>();
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        //"select * from UserData where UserName='" + userName + "' AND UserPassword='" + User123 + "'"
        //String Query = "select * from UserData where UserName = " + name + " AND UserPassword = " + password;
        String Query = "SELECT * FROM UserData WHERE UserName='" + name + "' AND UserPassword='" + password + "'";
        Cursor c = myDataBase.rawQuery(Query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                UserModel model = new UserModel();
                model.setUserId(c.getInt(0));
                model.setUserName(c.getString(1));
                model.setUserPassword(c.getString(2));
                list.add(model);
                c.moveToNext();
            }
        }
        c.close();
        return list;
    }

    public List<PaintingList> getPaintingList(int id) {
        List<PaintingList> list = new ArrayList<>();
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())

            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        String Query = "SELECT * FROM Painting where painterId =" + id;
        Cursor c = myDataBase.rawQuery(Query, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                PaintingList model = new PaintingList();
                model.setPaintingId(c.getInt(0));
                model.setPaintingImage(c.getString(1));
                model.setLikeId(c.getString(2));
                model.setPainterId(c.getInt(3));
                list.add(model);
                c.moveToNext();
            }
        }
        c.close();
        return list;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateLikeData(int userId, int paintingId, ArrayList<String> likeId) {
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        likeId.add(userId + "");
       // String likeList = TextUtils.join(",", likeId);
        String likeList = String.join(", ", likeId);
        cv.put("LikeId", likeList);
        myDataBase.execSQL("UPDATE " + "Painting" + " SET LikeId = " + "'" + likeList + "' " + "WHERE PaintingId = " + "'" + paintingId + "'");
//        if (result == -1)
//            Log.d("result", "f");
//        else
//            Log.d("result", "t");
    }


}