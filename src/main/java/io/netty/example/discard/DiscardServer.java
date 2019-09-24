package io.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)  accepts an incoming connection
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //  用来处理boss所接受的这个connection发来的消息
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)  ServerBootstrap 用来帮助我们创建一个 server
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3) 用来接收 incoming connection 的 channel
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler()); // 接受消息的channel
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)  这里是配置给 NioServerSocketChannel 用的。
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)  这里是配置给 NioServerSocketChannel 下的 子channel用的，

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }

}
