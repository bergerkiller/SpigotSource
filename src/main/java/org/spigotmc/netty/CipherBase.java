package org.spigotmc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

/**
 * Class to expose an
 * {@link #cipher(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf)} method to
 * aid in the efficient passing of ByteBuffers through a cipher.
 */
class CipherBase
{

    private final Cipher cipher;
    private ThreadLocal<byte[]> heapInLocal = new EmptyByteThreadLocal();
    private ThreadLocal<byte[]> heapOutLocal = new EmptyByteThreadLocal();

    private static class EmptyByteThreadLocal extends ThreadLocal<byte[]>
    {

        @Override
        protected byte[] initialValue()
        {
            return new byte[ 0 ];
        }
    }

    protected CipherBase(Cipher cipher)
    {
        this.cipher = cipher;
    }

    private byte[] bufToByte(ByteBuf in)
    {
        byte[] heapIn = heapInLocal.get();
        int readableBytes = in.readableBytes();
        if ( heapIn.length < readableBytes )
        {
            heapIn = new byte[ readableBytes ];
            heapInLocal.set( heapIn );
        }
        in.readBytes( heapIn, 0, readableBytes );
        return heapIn;
    }

    protected ByteBuf cipher(ChannelHandlerContext ctx, ByteBuf in) throws ShortBufferException
    {
        int readableBytes = in.readableBytes();
        byte[] heapIn = bufToByte( in );

        ByteBuf heapOut = ctx.alloc().heapBuffer( cipher.getOutputSize( readableBytes ) );
        heapOut.writerIndex( cipher.update( heapIn, 0, readableBytes, heapOut.array(), heapOut.arrayOffset() ) );

        return heapOut;
    }

    protected void cipher(ByteBuf in, ByteBuf out) throws ShortBufferException
    {
        int readableBytes = in.readableBytes();
        byte[] heapIn = bufToByte( in );

        byte[] heapOut = heapOutLocal.get();
        int outputSize = cipher.getOutputSize( readableBytes );
        if ( heapOut.length < outputSize )
        {
            heapOut = new byte[ outputSize ];
            heapOutLocal.set( heapOut );
        }
        out.writeBytes( heapOut, 0, cipher.update( heapIn, 0, readableBytes, heapOut ) );
    }
}
