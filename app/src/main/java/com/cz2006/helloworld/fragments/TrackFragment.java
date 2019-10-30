package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.EthiopicCalendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.activities.AddUsageActivity;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.managers.UsageManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.LineData;

/**
 * Represents Track Fragment linking from Main Activity
 *
 * @author Rosario Gelli Ann
 *
 */
public class TrackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackFragment newInstance(String param1, String param2) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view = inflater.inflate(R.layout.fragment_track, container, false);

        //chart - WIP
        //LineChart chart = (LineChart) view.findViewById(R.id.line_chart);
        //LineData lineData = new LineData(dataSet);
        float Eyearsum, Gyearsum, Wyearsum;
        SessionManager trackFragSessionM = new SessionManager(getContext());
        int userid = trackFragSessionM.getUserDetails().get("userID");
        UsageManager trackFragUsageManager = new UsageManager(getContext());
        trackFragUsageManager.open();
        Eyearsum = trackFragUsageManager.calyearsum(userid, 2019, 'E');
        TextView go = view.findViewById(R.id.TFsum);
        go.setText("Sum : " + Eyearsum); //RETURN userID?!
        FloatingActionButton AddUsageBtn = (FloatingActionButton) view.findViewById(R.id.AddUsageButton);
        AddUsageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getActivity(), AddUsageActivity.class);
                startActivity(go);
            }
        });
        // Inflate the layout for this fragment
        return view;

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

}
