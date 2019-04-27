package com.dp.structural.facade;

import java.io.File;

import com.dp.structural.facade.complexvideolib.AudioMixer;
import com.dp.structural.facade.complexvideolib.BitrateReader;
import com.dp.structural.facade.complexvideolib.Codec;
import com.dp.structural.facade.complexvideolib.CodecFactory;
import com.dp.structural.facade.complexvideolib.VideoFile;

public class VideoConversionFacade
{
    public File convertVideo(String fileName, String format)
    {
        System.out.println("VideoConversionFacade: conversion started.");
        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file.getCodecType());
        Codec destinationCodec = CodecFactory.extract(format);
        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        File result = AudioMixer.fix(intermediateResult);
        System.out.println("VideoConversionFacade: conversion completed.");
        return result;
    }
}
