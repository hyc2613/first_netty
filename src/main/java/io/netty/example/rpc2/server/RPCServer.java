package io.netty.example.rpc2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.rpc2.common.HandlerRegistry;
import io.netty.example.rpc2.common.IMessageHandler;
import io.netty.example.rpc2.common.MessageHandlers;

import java.nio.channels.SocketChannel;

public class RPCServer {

    private String ip;
    private int port;
    private int ioThreads;
    private int workThreads;
    private HandlerRegistry registry = new HandlerRegistry();
    private MessageHandlers handlers = new MessageHandlers();

    public RPCServer(String ip, int port, int ioThreads, int workThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workThreads = workThreads;
    }

    public RPCServer registry(String name, Class aClass, IMessageHandler handler) {
        registry.put(name, aClass);
        handlers.registry(name, handler);
        return this;
    }

    private Channel channel;
    private ServerBootstrap bootstrap;

    public void start() {
        bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(ioThreads))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast().addLast();
                    }
                });
        Channel channel = bootstrap.bind(ip, port).channel();
    }
    public void stop() {
        channel.close();
    }


}
