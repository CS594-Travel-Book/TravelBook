/*
Project Name : TravelBook Project
Project Part: Building a Camera
Day-Time: May 11, 2016 @ 2:18 a.m.  Version Number: 1
Done By: Eman Jaan



   ----------- Main Idea ------------

     1. Capturing a photo by the hardware of the phone ( DONE )
     2. Select a photo from the Gallery  ( NOT YET )
     3. Give the 1 and 2 as option for the user in a list menu ( NOT YET )
     4. save photos into database ( NOT YET)
     5. put the above options in a menu
 */




package com.project.android.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;



public class MainActivity extends AppCompatActivity
{
    //----------------------VARS & OBJ--------------------------

    public static final int CAPTURING_A_PHOTO = 1; // creating a var


    // Creating the needed Objects
    ImageView OUTPUT_PHOTO; // creating an object
    Button CLICK_BUTTON;
    Intent RUN;
    Bundle READ_PHOTO;
    Bitmap PHOTO_DATA;


    //-----------------------onCreate Method-------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting up the objects
        CLICK_BUTTON = (Button) findViewById(R.id.cameraButton);
        OUTPUT_PHOTO = (ImageView) findViewById(R.id.imageView);


        //check for the camera
        if (!checkForCamera())
        {
            CLICK_BUTTON.setEnabled(false);
        }
    }
    //------------------------------------------------


    // if there is a camera then it will return ture if not then it will return a false
    // and from here we can change it if we want either the front camera or the back camera
    public boolean checkForCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //------------------------------------------------

    public void launchCamera(View view)
    {
        RUN = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(RUN, CAPTURING_A_PHOTO);
    }

    //------------------------------------------------


    // this method will check of their is a photo taken by camera and if yes display it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == CAPTURING_A_PHOTO && resultCode == RESULT_OK)
        {
            READ_PHOTO = data.getExtras();
            PHOTO_DATA = (Bitmap)READ_PHOTO.get("data");
            OUTPUT_PHOTO.setImageBitmap(PHOTO_DATA);
        }

    }

    //------------------------------------------------

}
