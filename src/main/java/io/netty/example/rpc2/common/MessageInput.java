package io.netty.example.rpc2.common;

import com.alibaba.fastjson.JSON;

public class MessageInput {

    private String type;
    private String payload;
    private String requestId;

    public MessageInput(String type, String payload, String requestId) {
        this.type = type;
        this.payload = payload;
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public <T> T getPayload(Class<T> tClass) {
        if (payload == null) {
            return null;
        }
        return JSON.parseObject(payload, tClass);
    }

    public String getRequestId() {
        return requestId;
    }
}
