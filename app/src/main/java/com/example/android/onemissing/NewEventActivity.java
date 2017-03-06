package com.example.android.onemissing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewEventActivity extends Activity {

    private final int PICK_IMAGE = 200;
    private final int REQUEST_TAKE_PHOTO = 100;

    private DatabaseReference ref;

    private ImageView imgEvent;
    private EditText eventName;
    private EditText sportName;
    private String imagePath;
    private double LAT;
    private double LON;

    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        imgEvent = (ImageView) findViewById(R.id.imgEvent);
        eventName = (EditText) findViewById(R.id.txtEvent);
        sportName = (EditText) findViewById(R.id.txtSport);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().
                findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                    LAT = place.getLatLng().latitude;
                    LON = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {

            }
        });

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
                        dispatchTakePictureIntent();
                        dialog.dismiss();
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
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
                    imagePath = new File(getRealPathFromURI(selectedImageUri)).getAbsolutePath();
                    imgEvent.setImageURI(selectedImageUri);
                }
                break;

            case REQUEST_TAKE_PHOTO:
                System.out.println(resultCode);
                if(resultCode == RESULT_OK){
                    imagePath = f.getAbsolutePath();
                    imgEvent.setImageURI(Uri.fromFile(f));
                }
                break;
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public void saveEvent(View view) {

        System.out.println("ImagePath ------> "+imagePath);
        String name= eventName.getText().toString();
        String sport = sportName.getText().toString();
        Event event = new Event(imagePath, name, sport, LAT, LON);
        ref.push().setValue(event);

        Intent i = new Intent(NewEventActivity.this, MapMainActivity.class);
        finish(); //Kill the activity from which you will go to next activity
        startActivity(i);
    }
}
