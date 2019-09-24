package io.netty.example.rpc.common;

import io.netty.channel.ChannelHandlerContext;

public interface IMessageHandler<T> {

    void handle(ChannelHandlerContext ctx, String requestId, T t );

}
