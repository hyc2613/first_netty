package io.netty.example.talk;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TalkMessageEncoder extends MessageToByteEncoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(TalkMessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        logger.info("out msg: {}", msg);
        out.writeBytes(msg.getBytes());
    }
}
