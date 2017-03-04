package com.example.android.onemissing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;

public class NewEventActivity extends Activity {
    private final int PICK_IMAGE = 200;
    private int REQUEST_TAKE_PHOTO = 1;
    private ImageView imgEvent;
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        imgEvent = (ImageView) findViewById(R.id.imgEvent);

        imgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("DIALOG", "Just Clicked on ImageView!");
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.select_photo_dialog);
                dialog.setTitle("Photo");
                dialog.show();

                Button btDialogCamera = (Button) dialog.findViewById(R.id.btTakePhoto);
                Button btDialogGallery = (Button) dialog.findViewById(R.id.btSelectPhotoFromGallery);

                btDialogCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dispatchTakePictureIntent();
                    }
                });


                btDialogGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK_IMAGE);
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = Utils.createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.getMessage();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                f = photoFile;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case PICK_IMAGE:
                if(resultCode==RESULT_OK && data!=null){
                    Uri selectedImageUri = data.getData();
                    imgEvent.setImageURI(selectedImageUri);
                }
        }
    }


    public void onClickLocation(View view) {


    }
}
