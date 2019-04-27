package com.dp.structural.facade.complexvideolib;

public class CodecFactory
{
    public static Codec extract(String codecType)
    {
        if (codecType.equals("mp4")) {
            System.out.println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        } else {
            System.out.println("CodecFactory: extracting ogg audio...");
            return new OggCompressionCodec();
        }
    }
}
