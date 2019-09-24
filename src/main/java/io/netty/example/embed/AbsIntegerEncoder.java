package io.netty.example.embed;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class AbsIntegerEncoder extends MessageToMessageEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        ByteBuf byteBuf = ((ByteBuf) msg);
        while (byteBuf.readableBytes() >= 4) {
            int i = Math.abs(byteBuf.readInt());
            out.add(i);
        }
    }
}
