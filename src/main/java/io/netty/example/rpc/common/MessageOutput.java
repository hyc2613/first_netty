package io.netty.example.rpc.common;

/**
 * 发送的消息
 */
public class MessageOutput {

    private String requestId;
    private String type;
    private Object payload;

    public MessageOutput(String requestId, String type, Object payload) {
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

    public Object getPayload() {
        return payload;
    }
}
