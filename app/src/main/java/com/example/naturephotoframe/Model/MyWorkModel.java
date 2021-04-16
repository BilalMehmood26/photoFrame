package com.example.naturephotoframe.Model;

import android.graphics.Bitmap;

public class MyWorkModel {

    private Bitmap imgwork;
    String ImageName,Path;

    public MyWorkModel(Bitmap imgwork, String imageName, String path) {
        this.imgwork = imgwork;
        ImageName = imageName;
        Path = path;
    }


    public Bitmap getImgwork() {
        return imgwork;
    }

    public void setImgwork(Bitmap imgwork) {
        this.imgwork = imgwork;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}

