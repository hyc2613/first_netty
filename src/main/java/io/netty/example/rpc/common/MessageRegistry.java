package io.netty.example.rpc.common;

import java.util.HashMap;
import java.util.Map;

public class MessageRegistry {

    private Map<String, Class<?>> registryMap = new HashMap<String, Class<?>>();

    public Class<?> get(String type) {
        return registryMap.get(type);
    }

    public void register(String type, Class<?> classType) {
        registryMap.put(type, classType);
    }

}
