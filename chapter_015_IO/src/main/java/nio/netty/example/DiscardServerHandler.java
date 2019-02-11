package nio.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Handler обрабатывающий соединение
 * @autor aoliferov
 * @since 10.02.2019
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        System.out.println(msg);
        // Discard the received data silently.
        ((ByteBuf) msg).release(); // (3)
        ctx.channel().writeAndFlush(
                ctx.alloc().buffer().writeBytes("OK\r\n".getBytes(Charset.forName("UTF8")))
        );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
