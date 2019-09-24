package io.netty.example.embed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class AbsIntegerEncoderTest {
    @Test
    public void testEncoder() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i=0; i > -9; i--) {
            buffer.writeInt(i);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(embeddedChannel.writeOutbound(buffer));

        Assert.assertTrue(embeddedChannel.finish());

        for (Integer i = 0; i<9; i++) {
            Assert.assertEquals(i, embeddedChannel.readOutbound());
        }
        Assert.assertNull(embeddedChannel.readOutbound());
    }

}
