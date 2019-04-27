package com.dp.structural.facade;

import java.io.File;

public class Client
{
    public static void main(String[] args)
    {
        VideoConversionFacade converter = new VideoConversionFacade();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
    }
}
