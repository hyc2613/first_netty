package io.netty.example.rpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.example.rpc.common.IMessageHandler;
import io.netty.example.rpc.common.MessageInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHandler implements IMessageHandler<MessageInput> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, MessageInput messageInput) {
        logger.error("error message type {} ", messageInput.getType());
        ctx.close();
    }
}
