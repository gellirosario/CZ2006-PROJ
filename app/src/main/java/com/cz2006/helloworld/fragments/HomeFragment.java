package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.cz2006.helloworld.R;
import com.cz2006.helloworld.activities.NewsActivity;
import com.cz2006.helloworld.api.PSIAPI_Client;
import com.cz2006.helloworld.api.PSIAPI_Interface;
import com.cz2006.helloworld.managers.AccountManager;
import com.cz2006.helloworld.managers.SessionManager;
import com.cz2006.helloworld.models.PSI_info;
import com.cz2006.helloworld.models.PSI_twentyfourhour;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Represents Home Fragment linking from Main Activity
 *
 * @author Rosario Gelli Ann
 */


public class HomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AccountManager HomeaccountManager;
    private SessionManager HomesessionManager;

    TextView userNameTV, northpsiTV,southpsiTV,centralTV,eastpsiTV,westpsiTV, refreshTV;
    Button psiButton;



    // LogInActivity logInActivity = new LogInActivity();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        HomeaccountManager = new AccountManager(getActivity());
        HomesessionManager = new SessionManager(getActivity());

        userNameTV = view.findViewById(R.id.userName);
        northpsiTV= view.findViewById(R.id.textViewNorth);
        southpsiTV= view.findViewById(R.id.textViewSouth);
        centralTV= view.findViewById(R.id.textViewCentral);
        eastpsiTV= view.findViewById(R.id.textViewEast);
        westpsiTV= view.findViewById(R.id.textViewWest);

        psiButton = view.findViewById(R.id.buttonPSI);

        refreshTV = view.findViewById(R.id.textViewRefresh);

        int userID = HomesessionManager.getUserDetails().get("userID");

        HomeaccountManager.open();
        String name = HomeaccountManager.getAccountWithID(String.valueOf(userID)).getUserName();
        userNameTV.setText(name); // Set Text View
        
        TextView clickTextView = (TextView) view.findViewById(R.id.viewmoreClickableTV);
        clickTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getContext(), "TOTOTOTOTO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

            }
        });

        psiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPSIdetails();
            }
        });

        fetchPSIdetails();

        return view;

        // return inflater.inflate(R.layout.fragment_home, container, false);

    }

    public void fetchPSIdetails(){


        Retrofit retrofit = PSIAPI_Client.getRetrofitCLient();

        PSIAPI_Interface PSIAPI = retrofit.create(PSIAPI_Interface.class);

        Call call = PSIAPI.get24hourPSI();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if(response.body() !=null){

                    PSI_info psi_info = (PSI_info) response.body();

                    northpsiTV.setText("North: " +  psi_info.getItems().get(0).getReadings().getPsiTwentyFourHourly().getNorth());
                    southpsiTV.setText("South: " + psi_info.getItems().get(0).getReadings().getPsiTwentyFourHourly().getSouth());
                    centralTV.setText("Central: " + psi_info.getItems().get(0).getReadings().getPsiTwentyFourHourly().getCentral());
                    eastpsiTV.setText("East: "+ psi_info.getItems().get(0).getReadings().getPsiTwentyFourHourly().getEast());
                    westpsiTV.setText("West: "+ psi_info.getItems().get(0).getReadings().getPsiTwentyFourHourly().getWest());

                    refreshTV.setText("Accurate as at: "+ psi_info.getItems().get(0).getUpdateTimeStamp());




                }



            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });










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

        //Close database connection
        if(HomeaccountManager!=null)
        {
            HomeaccountManager.close();
            HomeaccountManager = null;
        }
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
