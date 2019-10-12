package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.cz2006.helloworld.Manifest;
import com.cz2006.helloworld.R;
import com.cz2006.helloworld.managers.DisplayManager;
import com.cz2006.helloworld.models.MapDetail;
import com.cz2006.helloworld.util.XMLSaxHandler;
import com.cz2006.helloworld.util.XMLSaxParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Represents Map Fragment linking from Main Activity
 *
 * @author Rosario Gelli Ann
 *
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener{

    public static final int RequestPermissionCode = 1;
    public static final int REQUEST_CHECK_SETTINGS = 123;
    private GoogleMap mMap;
    private ArrayList<MapDetail> details = new ArrayList<>();
    private String TAG = "GPS";
    private FloatingActionButton floatingActionButton;
    private RecyclerView mRecyclerView;
    private DisplayManager displayManager;
    private TextView mCardView_Title;

    //Store Longitude and Latitude
    private double lat = 0, lng = 0;

    private NestedScrollView nestedScrollViewBSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private FloatingSearchView mSearchView;

    //Google ApiClient
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void init(View v) {
        // Initialize variables
        this.nestedScrollViewBSheet = v.findViewById(R.id.bottom_sheet);
        this.mBottomSheetBehavior = BottomSheetBehavior.from(nestedScrollViewBSheet);
        this.floatingActionButton = v.findViewById(R.id.currentLocationBtn);
        this.mRecyclerView = v.findViewById(R.id.recycler_view);
        this.mSearchView = v.findViewById(R.id.floating_search_view);
        this.mCardView_Title = v.findViewById(R.id.cardView_Title);
        this.displayManager = new DisplayManager(details, getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        init(v);

        // Fill in Map with KML File data
        new getData().execute();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Bottom Sheet Behaviour Methods
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        // Floating Action Button Click Method
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });

        // Add Recyler View Data
        mRecyclerView.setAdapter(displayManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Search View Methods
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                displayManager.getFilter().filter(newQuery);

                if (displayManager.getItemCount() == 0) {
                    mBottomSheetBehavior.setPeekHeight(convertDpToPx(150));
                    mCardView_Title.setText("No Results Found");
                }
                else {
                    mBottomSheetBehavior.setPeekHeight(convertDpToPx(250));
                    mCardView_Title.setText("Recycling Points");
                }
            }
        });

    }

    public static int convertDpToPx(int dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void sortDetails() {
        if (details != null) {
            Location currLocation = new Location("");
            currLocation.setLatitude(lat);
            currLocation.setLongitude(lng);

            for (MapDetail detail : details) {
                Location location = new Location("");
                if(detail.getMarker() != null)
                {
                    location.setLatitude(detail.getMarker().getPosition().latitude);
                    location.setLongitude(detail.getMarker().getPosition().longitude);
                    detail.setDistance(currLocation.distanceTo(location));
                }
            }

            Collections.sort(details);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (checkPermission()) {
            //Initializing Google API client
            buildGoogleApiClient();
            // Check the location settings of the user and create the callback to react to the different possibilities
            LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequestBuilder.build());
            result.setResultCallback(mResultCallbackFromSettings);
        } else {
            requestPermission();
        }

    }

    // Initialize Google API Client
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]
                {
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                }, RequestPermissionCode);

    }

    // The callback for the management of the user settings regarding location
    private ResultCallback<LocationSettingsResult> mResultCallbackFromSettings = new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(LocationSettingsResult result) {
            final Status status = result.getStatus();
            //final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                getActivity(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.e(TAG, "Settings change unavailable. We have no way to fix the settings so we won't show the dialog.");
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean finelocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarselocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (finelocation && coarselocation) {

                        if (checkPermission())
                            buildGoogleApiClient();
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }


    private void setLocation(Location location){
        lat = (float) location.getLatitude();
        lng = (float) location.getLongitude();

        sortDetails();

    }

    @Override
    public void onLocationChanged(Location location) {
        setLocation(location);

        if (displayManager != null)
            displayManager.notifyDataSetChanged();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //moveMap((float)marker.getPosition().latitude,(float)marker.getPosition().longitude);
        return true;
    }



    //Getting current location
    private void getCurrentLocation() {
        Location location = null;
        if (checkPermission()) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (location != null) {
            //Getting longitude and latitude
            lng = location.getLongitude();
            lat = location.getLatitude();

            //moving the map to location
            moveMap(lat,lng);
        }
    }

    //Function to move the map
    private void moveMap(double lat, double lng) {

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(lat, lng);

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        //  Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // Retrieve data from KML File
    private class getData extends AsyncTask<String, Void, String> {
        String result;

        @Override
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                details.addAll(XMLSaxParser.parse(getActivity().getAssets().open("e-waste-recycling-kml.kml")));
                result = "in";
            } catch (IOException e) {
                e.printStackTrace();
                result = "out";
            }

            return result;
        }

        @Override
        public void onPostExecute(String result) {
            if (result.equalsIgnoreCase("in")) {
                sortDetails();

                if (mMap != null)
                    refreshMap();
            }
        }
    }

    // Add marker data from details
    private void refreshMap() {

        CameraPosition cameraPosition;

        if (lat == 0 && lng == 0)
        {
            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(1.3521-0.06, 103.8198))
                    .zoom(11)
                    .bearing(0)
                    .tilt(0)
                    .build();
        }
        else
        {
            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat-0.02, lng))
                    .zoom(11)
                    .bearing(0)
                    .tilt(0)
                    .build();
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (details != null) {
            for (int i = 0; i < details.size(); i++) {
                MapDetail detail = details.get(i);

                Marker m = detail.getMarker();

                if (m != null)
                    m.remove();

                StringTokenizer tokens = new StringTokenizer(detail.getCoordinates(), ",");
                String longitude = tokens.nextToken(); // Longitude coordinate
                String latitude = tokens.nextToken(); // Latitude coordinate

                Marker detailMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)))
                        .title(detail.getName()).snippet(detail.getAddressBlockHouseNumber()+ " " +
                                detail.getAddressStreetName() + " " +
                                detail.getAddressPostalCode()));
                detail.setMarker(detailMarker);

            }

        }

        displayManager.notifyDataSetChanged();

    }


}
