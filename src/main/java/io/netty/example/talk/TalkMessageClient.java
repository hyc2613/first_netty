package io.netty.example.talk;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TalkMessageClient {

    private static final TalkMessageOutputHandler handler = new TalkMessageOutputHandler();

    private static void run() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1", 8080)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(handler);
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect().sync();
            send();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
         run();
    }

    // 在这里开始发送
    private static void send() {
        int count = 30;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            // 发送
            sb.append("number_"+i+"#");
        }
        handler.send(sb.toString());
    }

}
