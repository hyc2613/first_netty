package io.netty.example.mock;


public class Channel {

    private ChannelPipeline pipeline;

    public Channel() {
        this.pipeline = new ChannelPipeline(this);
    }

    public ChannelPipeline pipeline() {
        return pipeline;
    }

    public void read(String msg) {
        pipeline.fireChannelRead(msg);

    }

    public static void main(String[] args) {
        Channel channel = new Channel();
        channel.pipeline.addLast(new HelloHandler()).addLast(new HeyHandler());
        channel.read("huangyc");
    }
}
