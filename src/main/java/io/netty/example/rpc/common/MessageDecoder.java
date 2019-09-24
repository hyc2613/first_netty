package io.netty.example.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<MessageInput> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        String requestId = read(buf);
        String type = read(buf);
        String payload = read(buf);
        out.add(new MessageInput(requestId, type, payload));
    }

    private String read(ByteBuf buf) {
        int len = buf.readInt();
        if (len < 0 || len > (1 << 20)) {
            throw new DecoderException("长度超长");
        }
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return new String(bytes);
    }
}
