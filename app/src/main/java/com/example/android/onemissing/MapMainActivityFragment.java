package com.example.android.onemissing;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapMainActivityFragment extends Fragment {

    private double latitudeBCN = 41.390205;
    private double longitudeBCN = 2.154007;
    private MapView map;
    private DatabaseReference ref;


    public MapMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_main, container, false);

        map = (MapView) view.findViewById(R.id.map);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");

        //First, set map view options.
        setMap();



        return view;
    }


    /**
     * This method configures the map visualization and gets a Controller to set a start point location.
     */
    public void setMap(){
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(latitudeBCN, longitudeBCN);
        mapController.setCenter(startPoint);
    }
}
