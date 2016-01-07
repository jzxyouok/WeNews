package com.shenghaiyang.wenews.net;

import com.shenghaiyang.wenews.entity.Info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shenghaiyang on 2016/1/6.
 */
public interface Rest {

    @GET(Constant.WECHAT_URL)
    Call<Info> getData(@Query("pno") int pno, @Query("ps") int ps);

}
