package io.netty.example.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.example.rpc.common.MessageInput;
import io.netty.example.rpc.common.MessageOutput;
import io.netty.example.rpc.common.MessageRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MessageCollector extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageCollector.class);
    private MessageRegistry registry;
    private RPCClient rpcClient;
    private ChannelHandlerContext ctx;
    private ConcurrentHashMap<String, RPCFuture<?>> pendingTasks = new ConcurrentHashMap<>();

    private Throwable ConnectionClosed = new Exception("rpc connection not active error");

    public MessageCollector(MessageRegistry registry, RPCClient rpcClient) {
        this.registry = registry;
        this.rpcClient = rpcClient;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = null;
        pendingTasks.forEach((a, future) -> {
            future.fail(ConnectionClosed);
        });
        pendingTasks.clear();
        // 尝试重连
        ctx.channel().eventLoop().schedule(() -> {
            rpcClient.reconnect();
        }, 1, TimeUnit.SECONDS);
    }

    public <T> RPCFuture<T> send(MessageOutput output) {
        RPCFuture<T> future = new RPCFuture<T>();
        if (ctx != null) {
            ctx.channel().eventLoop().execute(() -> {
                pendingTasks.put(output.getRequestId(), future);
                ctx.writeAndFlush(output);
            });
        } else {
            future.fail(ConnectionClosed);
        }
        return future;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof MessageInput)) {
            return;
        }
        MessageInput input = (MessageInput) msg;
        // 业务逻辑在这里
        Class<?> clazz = registry.get(input.getType());
        if (clazz == null) {
            logger.error("unrecognized msg type {}", input.getType());
            return;
        }
        Object o = input.getPayload(clazz);
        @SuppressWarnings("unchecked")
        RPCFuture<Object> future = (RPCFuture<Object>) pendingTasks.remove(input.getRequestId());
        if (future == null) {
            logger.error("future not found with type {}", input.getType());
            return;
        }
        future.success(o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    public void close() {
        if (ctx != null) {
            ctx.close();
        }
    }
}
