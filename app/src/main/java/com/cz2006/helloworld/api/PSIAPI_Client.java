package com.cz2006.helloworld.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PSIAPI_Client {

    public static final String BASE_URL = "https://api.data.gov.sg";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitCLient(){


        if(retrofit == null){


            //Defining Retrofit using Builder

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



        }

        return retrofit;

    }



}
