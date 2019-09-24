package io.netty.example.talk;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

public class TalkMessageDecoder extends ReplayingDecoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(TalkMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("writeIndex:{}, readIndex:{}", in.writerIndex(), in.readerIndex());
        int size = in.writerIndex() - in.readerIndex();
        byte[] bytes = new byte[size];
            in.readBytes(bytes);
            String str = new String(bytes);
            out.add(str);
    }
}
