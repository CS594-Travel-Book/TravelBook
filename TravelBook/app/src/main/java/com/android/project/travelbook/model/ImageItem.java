package com.android.project.travelbook.model;

import android.graphics.Bitmap;

/**
 * Created by Anu on 06-09-2016.
 */
public class ImageItem {
    private Bitmap image;


    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
