package com.feathernet.jayfit.rest;

import com.feathernet.jayfit.rest.pojos.register.RegistrationPOJO;
import com.feathernet.jayfit.rest.pojos.category.CategoryPOJO;
import com.feathernet.jayfit.rest.pojos.subcategory.SubCategoryPOJO;
import com.feathernet.jayfit.rest.pojos.video.VideoPOJO;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VincityAPI {

    @POST("register")
    public Call<RegistrationPOJO> register(@Body RequestBody params);

    @POST("category")
    public Call<CategoryPOJO> category();

    @POST("sub_category")
    public Call<SubCategoryPOJO> subCategory();

    @POST("video")
    public Call<VideoPOJO> video();
}
