package com.example.naturephotoframe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.naturephotoframe.Adapter.MyWorkAdapter;
import com.example.naturephotoframe.Model.MyWorkModel;
import com.example.naturephotoframe.R;

import java.io.File;
import java.util.ArrayList;

public class MyWork extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyWorkModel> listofgallery;
    private File file;
    private MyWorkAdapter recyclerViewAdapter;
    private File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);
        recyclerView = (RecyclerView) findViewById(R.id.gridrecyclerview);

        listofgallery = new ArrayList<MyWorkModel>();
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!",
                    Toast.LENGTH_LONG).show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/NaturePhotoFrame");
        }
        if (file.isDirectory()) {
            listFile = file.listFiles();
            if (listFile == null) {
            } else {
                for (int i = 0; i < listFile.length; i++) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(listFile[i].getAbsolutePath());
//                    list.add(myBitmap, listFile[i].getName(), file.listFiles()[i].getAbsolutePath());
                    listofgallery.add(new MyWorkModel(myBitmap, listFile[i].getName(), file.listFiles()[i].getAbsolutePath()));


                }
            }
        }



        setUpRecyclerView();


    }

    private void setUpRecyclerView() {


        if (listofgallery.size() == 0) {

            recyclerView.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            recyclerViewAdapter = new MyWorkAdapter(getApplicationContext(), listofgallery);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}