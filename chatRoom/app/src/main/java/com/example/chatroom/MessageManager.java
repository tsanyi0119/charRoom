package com.example.chatroom;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MessageManager {

    private final FirebaseApi firebaseApi;

    public MessageManager() {
        firebaseApi = new FirebaseApi();
    }

    public Single<List<Message>> getMessages() {
        return firebaseApi.fetchMessages()
                .flatMap(messageMap -> Observable.fromIterable(messageMap.values()))
                .map(jsonMessage -> {
                    // 使用Gson或手动解析将JSON对象转换为Message对象
                    Message message = new Message();
                    message.setMessage(jsonMessage.getMessage());
                    message.setSender(jsonMessage.getSender());
                    message.setReceiver(jsonMessage.getReceiver());
                    message.setTimestamp(jsonMessage.getTimestamp());
                    return message;
                })
                .toList()
                .subscribeOn(Schedulers.io());
    }
}
