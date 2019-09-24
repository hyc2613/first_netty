package io.netty.example.rpc2.common;


import java.util.HashMap;
import java.util.Map;

public class MessageHandlers {

    private Map<String, IMessageHandler> map = new HashMap<>();
    private IMessageHandler<MessageInput> defaultMessageHandler;

    public IMessageHandler get(String name) {
        return map.get(name);
    }

    public void registry(String name, IMessageHandler handler) {
        map.put(name, handler);
    }

    public IMessageHandler<MessageInput> getDefaultMessageHandler() {
        return defaultMessageHandler;
    }

    public MessageHandlers setDefault(IMessageHandler<MessageInput> defaultMessageHandler) {
        this.defaultMessageHandler = defaultMessageHandler;
        return this;
    }

}
