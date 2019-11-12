package com.cz2006.helloworld.api;

import com.cz2006.helloworld.models.PSI_info;
import com.cz2006.helloworld.models.PSI_twentyfourhour;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Represents PSIAPI_Interface
 *
 * @author Edmund
 */
public interface PSIAPI_Interface {

    @GET("/v1/environment/psi")
    Call<PSI_info> get24hourPSI();


}
