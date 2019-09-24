package io.netty.example.rpc.common;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 对输出消息进行编码
 */
public class MessageEncoder extends MessageToMessageEncoder<MessageOutput> {


    protected void encode(ChannelHandlerContext ctx, MessageOutput msg, List<Object> out) throws Exception {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        write(buf, msg.getRequestId());
        write(buf, msg.getType());
        write(buf, JSON.toJSONString(msg.getPayload()));
        out.add(buf);
    }

    private void write(ByteBuf byteBuf, String str) {
        byteBuf.writeInt(str.length());
        byteBuf.writeBytes(str.getBytes(Charsets.UTF8));
    }

}
