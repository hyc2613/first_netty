package io.netty.example.rpc2.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.example.rpc2.common.HandlerRegistry;
import io.netty.example.rpc2.common.IMessageHandler;
import io.netty.example.rpc2.common.MessageHandlers;
import io.netty.example.rpc2.common.MessageInput;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageCollector extends ChannelInboundHandlerAdapter {

    private HandlerRegistry registry;
    private MessageHandlers handlers;
    private ThreadPoolExecutor executor;

    public MessageCollector(HandlerRegistry registry, MessageHandlers handlers, int workThreads) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(1000);
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger i = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setName("rpc_"+i.getAndIncrement());
                return t;
            }
        };
        this.executor = new ThreadPoolExecutor(1, workThreads, 30, TimeUnit.SECONDS, queue, factory, new ThreadPoolExecutor.CallerRunsPolicy());
        this.registry = registry;
        this.handlers = handlers;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageInput) {
            // executor.execute(-> ());
        }
    }

    private void handleMessage(ChannelHandlerContext ctx, MessageInput msg) {
        Class<?> aClass = registry.get(msg.getType());
        if (aClass == null) {
            handlers.getDefaultMessageHandler().handle(ctx, msg.getRequestId(), msg);
        }
        Object o = msg.getPayload(aClass);
        IMessageHandler handler = handlers.get(msg.getType());
        if (handler != null) {
            handler.handle(ctx, msg.getRequestId(), o);
        }
        else {
            handlers.getDefaultMessageHandler().handle(ctx, msg.getRequestId(), msg);
        }
    }

}
