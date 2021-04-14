package com.example.naturephotoframe.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.naturephotoframe.BackgrounEraser.MyCustomView;
import com.example.naturephotoframe.R;
import com.example.naturephotoframe.Utils.BitmapUtils;
import com.example.naturephotoframe.Utils.Common;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditMenu extends AppCompatActivity {
    public static PhotoEditorView photoEditorView;
    PhotoEditor mPhotoEditor;
    ImageButton erase;
    RelativeLayout rootLayout;
    Toolbar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        hooks();
        setSupportActionBar(mActionBar);
        getSupportActionBar().setTitle("Edit Menu");
//        mPhotoEditor = new PhotoEditor.Builder(this, photoEditorView)
//                .setPinchTextScalable(true)
//                .build();
        //loadImage();

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        MyCustomView myCustomView = new MyCustomView(EditMenu.this);
        //Get root layout of the activity
        rootLayout = findViewById(R.id.rootLayout);
        //photoEditorView.addView(myCustomView);
        //Add custom view into root layout
        rootLayout.addView(myCustomView);
    }

    private void loadImage() {
        photoEditorView.getSource().setImageBitmap(Common.cameraBitmap);
    }

    public void hooks() {
        photoEditorView = findViewById(R.id.photoEditorView);
        erase = findViewById(R.id.eraser);
        mActionBar = findViewById(R.id.my_awesome_toolbar);
    }

}