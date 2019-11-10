package com.cz2006.helloworld.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.adapters.Leaderboard_AlltimeAdapter;
import com.cz2006.helloworld.adapters.Leaderboard_MonthAdapter;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.util.SQLiteDatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonthleaderboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonthleaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthleaderboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String DB_NAME = "HelloWorldDB.db";
    private int DB_VERSION = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView monthRV;



    private SQLiteDatabase LBdatabase;
    private TextView rankmonthTV, namemonthTV,ptsmonthTV;
    private Leaderboard_MonthAdapter mAdapter;

    public MonthleaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthleaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthleaderboardFragment newInstance(String param1, String param2) {
        MonthleaderboardFragment fragment = new MonthleaderboardFragment();
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
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_month_leaderboard, container, false);


        SQLiteDatabaseHelper LBdbHelper = new SQLiteDatabaseHelper(getContext(), DB_NAME, null, DB_VERSION);
        LBdatabase = LBdbHelper.getReadableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.monthRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Leaderboard_MonthAdapter(getContext(),getAllItems());
        recyclerView.setAdapter(mAdapter);

        rankmonthTV = view.findViewById(R.id.rankmonthTV);
        namemonthTV = view.findViewById(R.id.namemonthTV);
        ptsmonthTV = view.findViewById(R.id.ptsmonthTV);












        return view;
    }

    private Cursor getAllItems(){

        return LBdatabase.query(

                AccountManager.TABLE_NAME_ACCOUNT,
                null,
                null,
                null,
                null,
                null,
                AccountManager.TABLE_ACCOUNT_COLUMN_POINTS + " DESC"

        );
    }



}