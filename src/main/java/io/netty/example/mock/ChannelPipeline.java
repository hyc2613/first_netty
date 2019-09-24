package io.netty.example.mock;

import java.util.LinkedList;

public class ChannelPipeline {

    private AbstractHandlerContext head;
    private AbstractHandlerContext tail;

    private Channel channel;

    public ChannelPipeline(Channel channel) {
        this.channel = channel;
        head = new HeadAbstractHandlerContext();
        tail = new HeadAbstractHandlerContext();

        head.next = tail;
        tail.pre = head;
    }

    public ChannelPipeline addLast(Handler handler) {
        AbstractHandlerContext ctx = new DefaultHandlerContext(handler);
        addLast0(ctx);
        return this;
    }

    public final ChannelPipeline fireChannelRead(String msg) {
        head.fireNext(msg);
        return this;
    }

    private class HeadAbstractHandlerContext extends AbstractHandlerContext implements Handler {

        @Override
        public Handler handle() {
            return this;
        }

        @Override
        public void handle(String msg) {
            System.out.println("do nothing");
        }
    }

    private void addLast0(AbstractHandlerContext ctx) {
        AbstractHandlerContext prev = tail.pre;
        ctx.pre = prev;
        ctx.next = tail;
        prev.next = ctx;
        tail.pre = ctx;
    }

}
