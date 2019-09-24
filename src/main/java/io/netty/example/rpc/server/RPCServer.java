package io.netty.example.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.rpc.common.*;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class RPCServer {

    private String ip;
    private int port;
    private int ioThreads;
    private int workThreads;
    private MessageHandlers handlers = new MessageHandlers();
    private MessageRegistry registry = new MessageRegistry();

    {
        handlers.defaultHandler(new DefaultHandler());
    }

    public RPCServer(String ip, int port, int ioThreads, int workThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workThreads = workThreads;
    }

    public RPCServer service(String type, Class msgClass, IMessageHandler handler) {
        registry.register(type, msgClass);
        handlers.registry(type, handler);
        return this;
    }

    private ServerBootstrap bootstrap;
    private EventLoopGroup group;
    private MessageCollector collector;
    private Channel serverChannel;

    public void start() throws InterruptedException {
        bootstrap = new ServerBootstrap();
        group = new NioEventLoopGroup(ioThreads);
        bootstrap.group(group);
        collector = new MessageCollector(handlers, registry, workThreads);
        MessageEncoder encoder = new MessageEncoder();
        bootstrap.channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ReadTimeoutHandler(60))
                                .addLast(new MessageDecoder())
                                .addLast(encoder)
                                .addLast(collector);
                    }
                });
        bootstrap.option(ChannelOption.SO_BACKLOG, 100).option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true);
        serverChannel = bootstrap.bind(ip, port).channel();
    }
    public void stop() {
        // 先关闭服务端套件字
        serverChannel.close();
        // 再斩断消息来源，停止io线程池
        group.shutdownGracefully();
        // 最后停止业务线程
        collector.closeGracefully();
    }
}
