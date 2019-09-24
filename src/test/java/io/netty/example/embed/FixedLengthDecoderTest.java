package io.netty.example.embed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class FixedLengthDecoderTest {

    @Test
    public void testDecode() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf copyedByteBuf = buffer.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthDecoder(3));
        Assert.assertFalse(channel.writeInbound(copyedByteBuf.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(copyedByteBuf.readBytes(7)));

        Assert.assertTrue(channel.finish());

        Assert.assertEquals(channel.readInbound(), buffer.readSlice(3));
        Assert.assertEquals(channel.readInbound(), buffer.readSlice(3));
        Assert.assertEquals(channel.readInbound(), buffer.readSlice(3));
//
//        Assert.assertNull(channel.readInbound());
    }

}
