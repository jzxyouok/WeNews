package com.shenghaiyang.wenews.net;

import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by shenghaiyang on 2016/1/6.
 */
public final class Client {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();
    private static final Rest REST = RETROFIT.create(Rest.class);

    public static Rest getRest() {
        return REST;
    }
}
