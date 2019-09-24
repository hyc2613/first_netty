package io.netty.example.talk;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class TalkMessageServer {

    private static void start() throws InterruptedException {
        int port = 8080;

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
//                                .addLast(new TalkMessageDecoder())
//                                .addLast(new LineBasedFrameDecoder(20))
                                .addLast(new DelimiterBasedFrameDecoder(20, Unpooled.wrappedBuffer(new byte[] {'#'})))
                                .addLast(new TalkMessageDecoder())
                                .addLast(new TalkMessageEncoder())
                        .addLast(new TalkMessageInputHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        start();
    }

}
