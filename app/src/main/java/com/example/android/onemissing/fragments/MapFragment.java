package com.example.android.onemissing.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.onemissing.Event;
import com.example.android.onemissing.InfoWindow;
import com.example.android.onemissing.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import static org.osmdroid.views.overlay.Marker.ANCHOR_CENTER;
import static org.osmdroid.views.overlay.Marker.ANCHOR_TOP;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapFragment extends Fragment {

    private double latitudeBCN = 41.390205;
    private double longitudeBCN = 2.154007;
    private DatabaseReference ref;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_main, container, false);

        final MapView map = (MapView) view.findViewById(R.id.map);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");
        final RadiusMarkerClusterer poiMarkers = new RadiusMarkerClusterer(getContext());

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Event event = dataSnapshot.getValue(Event.class);
                Marker marker = new Marker(map);
                marker.setPosition(new GeoPoint(event.getLat(), event.getLon()));
                int offsetX = (int)(ANCHOR_CENTER*1) - (int)(ANCHOR_CENTER*1);
                int offsetY = (int)(ANCHOR_TOP*1) - (int)(ANCHOR_CENTER*1);

                marker.setTitle(event.getEventName());
                marker.setSubDescription(event.getSport());
                marker.setInfoWindow(new InfoWindow(getContext(), map, event.getImgPath()));
                marker.getInfoWindow().open(marker, marker.getPosition(), offsetX, offsetY);
                poiMarkers.add(marker);
                poiMarkers.invalidate();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        setMap(map);
        map.getOverlays().add(poiMarkers);
        map.invalidate();
        ref.addChildEventListener(childEventListener);

        return view;
    }


    /**
     * This method configures the map visualization and gets a Controller to set a start point location.
     */
    public void setMap(MapView map){
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(12);
        GeoPoint startPoint = new GeoPoint(latitudeBCN, longitudeBCN);
        mapController.setCenter(startPoint);
    }
}
