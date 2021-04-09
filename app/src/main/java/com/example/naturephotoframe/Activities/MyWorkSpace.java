package com.example.naturephotoframe.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturephotoframe.Adapter.FiltersAdapter;
import com.example.naturephotoframe.Adapter.FramesAdapter;
import com.example.naturephotoframe.Model.Frames;
import com.example.naturephotoframe.R;
import com.example.naturephotoframe.Utils.BitmapUtils;
import com.example.naturephotoframe.Utils.Common;
import com.jsibbold.zoomage.ZoomageView;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

public class MyWorkSpace extends AppCompatActivity implements FiltersAdapter.ThumbnailsAdapterListener {


    ZoomageView mImage;
    RecyclerView recyclerView;
    ImageView framesImage;
    ImageButton filterBtn,frames,stickers,background;
    List<ThumbnailItem> thumbnailItemList;
    FiltersAdapter filtersAdapter;
    MyWorkSpaceListener myWorkSpaceListener;
    static String IMAGE_NAME = "placeholder.jpg";
    Bitmap originalImage;
    Bitmap filteredImage;
    Bitmap finalImage;
    ArrayList<Frames> frameList = new ArrayList<>();
    ArrayList<Frames> stickersList = new ArrayList<>();
    FramesAdapter framesAdapter;
    ArrayList<Frames> backgroundList = new ArrayList<>();

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_space);
        hooks();
        mImage.setImageURI(Common.imageUri);
        frameList.add(new Frames(R.drawable.frame1));
        frameList.add(new Frames(R.drawable.frame13));
        stickersList.add(new Frames(R.drawable.sticker1));
        stickersList.add(new Frames(R.drawable.sticker11));
        backgroundList.add(new Frames(R.drawable.background));
        backgroundList.add(new Frames(R.drawable.placeholder));


        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterRecyclerView();
            }
        });
        frames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framesRecyclerView();
            }
        });

        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stikersRecyclerView();
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundRecyclerView();

            }
        });
        framesImage.setImageResource(FramesAdapter.image);
        framesAdapter.notifyDataSetChanged();

    }

    public void hooks() {
        mImage = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recyclerView);
        filterBtn = findViewById(R.id.filters);
        frames = findViewById(R.id.frames);
        stickers = findViewById(R.id.stickers);
        background = findViewById(R.id.background);
        framesImage = findViewById(R.id.framesImage);
    }

    // load the default image from assets on app launch
    private void loadImage() {
        originalImage = BitmapUtils.getBitmapFromAssets(this, IMAGE_NAME, 300, 300);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        mImage.setImageBitmap(Common.cameraBitmap);
    }

    public void framesRecyclerView(){
        framesAdapter = new FramesAdapter(frameList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(framesAdapter);

    }
    public void backgroundRecyclerView(){
        framesAdapter = new FramesAdapter(backgroundList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(framesAdapter);

    }
    public void stikersRecyclerView(){


        framesAdapter = new FramesAdapter(stickersList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(framesAdapter);
    }
    public void filterRecyclerView() {
        loadImage();
        thumbnailItemList = new ArrayList<>();
        filtersAdapter = new FiltersAdapter(getApplicationContext(), thumbnailItemList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(filtersAdapter);
        prepareThumbnail(Common.cameraBitmap);

    }

    public void prepareThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage;

                if (bitmap == null) {
                    thumbImage = BitmapUtils.getBitmapFromAssets(getApplicationContext(), IMAGE_NAME, 100, 100);
                } else {
                    thumbImage = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
                }

                if (thumbImage == null)
                    return;

                ThumbnailsManager.clearThumbs();
                thumbnailItemList.clear();

                // add normal bitmap first
                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImage;
                thumbnailItem.filterName = getString(R.string.filter_normal);
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters = FilterPack.getFilterPack(MyWorkSpace.this);

                for (Filter filter : filters) {
                    ThumbnailItem tI = new ThumbnailItem();
                    tI.image = thumbImage;
                    tI.filter = filter;
                    tI.filterName = filter.getName();
                    ThumbnailsManager.addThumb(tI);
                }

                thumbnailItemList.addAll(ThumbnailsManager.processThumbs(MyWorkSpace.this));

                MyWorkSpace.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        filtersAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        new Thread(r).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // clear bitmap memory
        originalImage.recycle();
        finalImage.recycle();
        finalImage.recycle();

        originalImage = Common.cameraBitmap.copy(Bitmap.Config.ARGB_8888, true);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        mImage.setImageBitmap(originalImage);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (myWorkSpaceListener != null)
            myWorkSpaceListener.onFilterSelected(filter);
        // reset image controls
        originalImage = Common.cameraBitmap.copy(Bitmap.Config.ARGB_8888, true);
        // applying the selected filter
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        mImage.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    public void setListener(MyWorkSpaceListener listener) {
        this.myWorkSpaceListener = listener;
    }

    public interface MyWorkSpaceListener {
        void onFilterSelected(Filter filter);
    }
}