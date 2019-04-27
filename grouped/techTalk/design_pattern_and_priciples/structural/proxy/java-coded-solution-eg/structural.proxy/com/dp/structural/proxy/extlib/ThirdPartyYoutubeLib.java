package com.dp.structural.proxy.extlib;

import java.util.HashMap;

//The interface of a remote service.
public interface ThirdPartyYoutubeLib
{
    public HashMap<String, Video> popularVideos();

    public Video getVideo(String videoId);
}
