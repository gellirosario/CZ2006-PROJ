package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.activities.NewsActivity;
import com.cz2006.helloworld.api.ApiNewsClient;
import com.cz2006.helloworld.api.ApiNewsInterface;
import com.cz2006.helloworld.models.Article;
import com.cz2006.helloworld.models.News;
import com.cz2006.helloworld.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public static final String API_KEY= "81ebf347fbed47dab591f6ee934b1221";
    public static final String domain = "straitstimes.com,channelnewsasia.com";
    public static final String search = "singapore-environment";
    public static final String sortBy = "relevancy";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsAdapter mNewsAdapter;
    private String TAG = NewsActivity.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    public void LoadJson(){

        ApiNewsInterface apiNewsInterface = ApiNewsClient.getApiNewsClient().create(ApiNewsInterface.class);

        Call<News> call;
        call = apiNewsInterface.getNews(search,domain,sortBy,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    mNewsAdapter = new NewsAdapter(articles, getActivity());

                    mRecyclerView.setAdapter(mNewsAdapter);
                    mNewsAdapter.notifyDataSetChanged();

                    initListener();

                } else {
                    Toast.makeText(getActivity(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
    private void initListener(){

        mNewsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Article article = articles.get(position);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);


        mRecyclerView = view.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        LoadJson();

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
