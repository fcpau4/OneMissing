package com.example.android.onemissing.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.onemissing.Event;
import com.example.android.onemissing.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

/**
 * Created by Arfera on 07/03/2017.
 */

public class UserEventsFragment extends Fragment {

    private FirebaseListAdapter firebaseListAdapter;
    private DatabaseReference ref;


    public UserEventsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_events, container, false);

        GridView gvEvents = (GridView) view.findViewById(R.id.gallery);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");

        firebaseListAdapter = new FirebaseListAdapter<Event>(getActivity(), Event.class, R.layout.gv_event_square, ref) {
            @Override
            protected void populateView(View v, Event event, int position) {
                ImageView imgEvent = (ImageView) v.findViewById(R.id.photo_event);
                Glide.with(getContext())
                        .load(Uri.fromFile(new File(event.getImgPath())))
                        .centerCrop()
                        .into(imgEvent);
            }
        };

        gvEvents.setAdapter(firebaseListAdapter);

        return view;
    }
}
