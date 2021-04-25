package com.labrado.chatdemo.Fragments;

import com.labrado.chatdemo.Notifications.MyResponse;
import com.labrado.chatdemo.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content.Type:application/json",
                    "Authorization:key=AAAAVNrzAbg:APA91bGw0H9hOQIkYbuhH9bAYtmMv_qNw5M2hn_ruhJgakrod9iqE5A3ohwY2mAf7GW0HGdS1KkMoWeGM2aMV7-7xHP_ZYUgc3yhvCL1sIw8A_17BhEWqBQ7_IJgU0Fxix8C_iuL8qAl"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
