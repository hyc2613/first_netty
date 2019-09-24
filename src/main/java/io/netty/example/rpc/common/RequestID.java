package io.netty.example.rpc.common;

import java.util.UUID;

/**
 * 生成请求ID
 */
public class RequestID {

    public static String nextId() {
        return UUID.randomUUID().toString();
    }
}
