package com.android.project.travelbook.model;

import android.location.Location;

/**
 * Created by Anu on 06-09-2016.
 */
public class Images {
    Location location;
    String image;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
