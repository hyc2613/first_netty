package io.netty.example.rpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.example.rpc.common.IMessageHandler;
import io.netty.example.rpc.common.MessageHandlers;
import io.netty.example.rpc.common.MessageInput;
import io.netty.example.rpc.common.MessageRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageCollector extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MessageCollector.class);

    private ThreadPoolExecutor threadPoolExecutor;
    private MessageHandlers handlers;
    private MessageRegistry registry;

    public MessageCollector(MessageHandlers handlers, MessageRegistry registry, int workerThreads) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(1000);
        ThreadFactory threadFactory = new ThreadFactory() {
            private AtomicInteger i = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc"+i.getAndIncrement());
                return t;
            }
        };

        this.threadPoolExecutor = new ThreadPoolExecutor(1, workerThreads, 30, TimeUnit.SECONDS, queue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        this.handlers = handlers;
        this.registry = registry;
    }

    public void closeGracefully() {
        this.threadPoolExecutor.shutdown();
        try {
            this.threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        this.threadPoolExecutor.shutdownNow();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("connection comes");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("connection leaves");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageInput) {
            this.threadPoolExecutor.execute(() -> {
                this.handleMessage(ctx, (MessageInput) msg);
            });
        }
    }

    private void handleMessage(ChannelHandlerContext ctx, MessageInput msg) {
        Class<?> aClass = registry.get(msg.getType());
        if (aClass == null) {
            handlers.getDefaultHandler().handle(ctx, msg.getRequestId(), msg);
        }
        Object o = msg.getPayload(aClass);
        IMessageHandler handler = handlers.get(msg.getType());
        if (handler != null) {
            handler.handle(ctx, msg.getRequestId(), o);
        }
        else {
            handlers.getDefaultHandler().handle(ctx, msg.getRequestId(), msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.error("handle error", cause);
        ctx.close();
    }
}
