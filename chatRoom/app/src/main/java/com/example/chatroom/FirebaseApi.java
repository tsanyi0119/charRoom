package com.example.chatroom;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class FirebaseApi {

    // Retrofit 接口定义
    public interface FirebaseService {
        @GET("messages.json") // 资源路径
        Observable<Response<Map<String, Message>>> getMessages();
    }

    private final String BASE_URL = "https://chatroom-efe55-default-rtdb.firebaseio.com/"; // 基础 URL

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private final FirebaseService service = retrofit.create(FirebaseService.class);

    public Observable<Map<String, Message>> fetchMessages() {
        return service.getMessages()
                .map(Response::body)
                .subscribeOn(Schedulers.io());
    }
}
