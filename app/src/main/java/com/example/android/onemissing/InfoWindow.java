package com.example.android.onemissing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Bayer on 05/03/2017.
 */

public class InfoWindow extends MarkerInfoWindow {
    /**
     * @param mapView
     */
    private Context mContext;
    private String path;

    public InfoWindow(Context context, MapView mapView, String path) {
        super(R.layout.event_box, mapView);
        mContext = context;
        this.path = path;
    }

    @Override
    public void onOpen(Object item) {
        mMarkerRef = (Marker)item;

        TextView txtName = (TextView) mView.findViewById(R.id.txtBoxEventName);
        txtName.setText(mMarkerRef.getTitle());

        TextView txtSport = (TextView) mView.findViewById(R.id.txtBoxSportName);
        txtSport.setText(mMarkerRef.getSubDescription());

        ImageView imageView = (ImageView) mView.findViewById(R.id.eventBox);
        File f = new File(path);
        if(f.exists()){
            Glide.with(mContext)
                    .load(Uri.fromFile(f))
                    .centerCrop()
                    .into(imageView);
        }
        imageView.setVisibility(View.VISIBLE);
    }
}
