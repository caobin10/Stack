package com.xiangsheng.jzfp_sc;

import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.http.HttpResponse;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/2/27.
 */

public interface DataService
{
    @GET("api/account/Login?")
    Observable<HttpResponse<TicketAuth>> login(@Query("username") String username, @Query("password") String password);

    @GET("api/account/tokenTest?")
    Observable<HttpResponse<Object>> TokenTest();


    @GET("api/Account/GetRCToken?")
    Observable<HttpResponse<JSONObject>> getRCToken(@Query("userid") String userId);

    @GET("Admin/Page/Basic/GetList?")
    Observable<HttpResponse<Object>> getList(@Query("depIds") String depIds);

    @POST("api/basic/test2")
//    Observable<HttpResponse<Object>> Test();
    Observable<HttpResponse<Object>> Test2(@Query("start") int start,@Query("limit") int limit,@Query("orderCulum") String orderCulum,@Query("orderType") String orderType,@Query("where") String where);

    @GET("api/common/getunit")
    Observable<HttpResponse<Object>> GetUnit(@Query("parentCode") String parentCode,@Query("codeScope") String codeScope);

    @GET("api/account/getuserunit")
    Observable<HttpResponse<Object>> GetUserUnit(@Query("userid") String userid);


}
