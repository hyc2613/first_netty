package io.netty.example.rpc.common;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlers {

    private Map<String, IMessageHandler<?>> handlerMap = new HashMap();
    private IMessageHandler<MessageInput> defaultHandler;

    public void registry(String type, IMessageHandler handler) {
        handlerMap.put(type, handler);
    }

    public IMessageHandler get(String type) {
        return handlerMap.get(type);
    }

    public IMessageHandler<MessageInput> getDefaultHandler() {
        return defaultHandler;
    }

    public MessageHandlers defaultHandler(IMessageHandler<MessageInput> defaultHandler) {
        this.defaultHandler = defaultHandler;
        return this;
    }
}
