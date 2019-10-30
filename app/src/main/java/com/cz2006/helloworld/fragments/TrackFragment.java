package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.activities.AddUsageActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
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

        String[] arraySpinner = new String[] {"Electricity", "Water", "Gas"};

        Spinner s = (Spinner) view.findViewById (R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(adapter);

        //chart - WIP
        LineChart chart = (LineChart) view.findViewById(R.id.line_chart);

        List<Entry> entries = new ArrayList<Entry>();

        final String[] months = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return months[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //sample data to test out chart
        entries.add(new Entry(1,10));
        entries.add(new Entry(2,20));
        entries.add(new Entry(3,15));

        LineDataSet dataSet = new LineDataSet(entries, "Electricity Usage");

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        //LineChart chart = (LineChart) view.findViewById(R.id.line_chart);
        //LineData lineData = new LineData(dataSet);
        float Eyearsum, Gyearsum, Wyearsum;
        SessionManager trackFragSessionM = new SessionManager(getContext());
        int userid = trackFragSessionM.getUserDetails().get("userID");
        UsageManager trackFragUsageManager = new UsageManager(getContext());
        trackFragUsageManager.open();
        Calendar date = Calendar.getInstance();
        int yearnow = date.get(Calendar.YEAR);
        Eyearsum = trackFragUsageManager.calYearSum(userid, yearnow, 'E');
        Gyearsum = trackFragUsageManager.calYearSum(userid, yearnow, 'G');
        Wyearsum = trackFragUsageManager.calYearSum(userid, yearnow, 'W');
        TextView go = view.findViewById(R.id.TFsum);
        go.setText("Sum : " + Eyearsum + "," + Gyearsum + "," + Wyearsum); //RETURN userID?!
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
