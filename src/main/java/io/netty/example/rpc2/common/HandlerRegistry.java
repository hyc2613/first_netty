package io.netty.example.rpc2.common;

import java.util.HashMap;
import java.util.Map;

public class HandlerRegistry {

    private Map<String, Class<?>> map = new HashMap<>();

    public void put(String name, Class aClass) {
        map.put(name, aClass);
    }
    public Class<?> get(String name) {
        return map.get(name);
    }

}
