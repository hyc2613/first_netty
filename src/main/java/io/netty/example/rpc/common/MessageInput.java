package io.netty.example.rpc.common;

import com.alibaba.fastjson.JSON;

/**
 * 接收消息
 */
public class MessageInput {
    // 请求id
    private String requestId;
    // 请求类型
    private String type;
    // 请求内容
    private String payload;

    public MessageInput(String requestId, String type, String payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getRequestId() {
        return requestId;
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
}
