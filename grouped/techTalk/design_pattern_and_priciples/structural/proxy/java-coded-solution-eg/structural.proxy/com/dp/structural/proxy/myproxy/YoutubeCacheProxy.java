package com.dp.structural.proxy.myproxy;

import java.util.HashMap;

import com.dp.structural.proxy.extlib.ThirdPartyYoutubeClass;
import com.dp.structural.proxy.extlib.ThirdPartyYoutubeLib;
import com.dp.structural.proxy.extlib.Video;

//On the other hand, to save some bandwidth, we can cache
//request results and keep them for some time. But it may
//be impossible to put such code directly to the service
//class. For example, it could have been provided by third
//party library or/and defined as `final`. That is why we
//put the caching code to a new proxy class which
//implements the same interface as a service class. It is
//going to delegate to the service object only when the
//real requests have to be sent.
public class YoutubeCacheProxy implements ThirdPartyYoutubeLib
{
    private ThirdPartyYoutubeLib youtubeService;

    private HashMap<String, Video> cachePopular = new HashMap<String, Video>();

    private HashMap<String, Video> cacheAll = new HashMap<String, Video>();

    public YoutubeCacheProxy()
    {
        this.youtubeService = new ThirdPartyYoutubeClass();
    }

    @Override
    public HashMap<String, Video> popularVideos()
    {
        if (cachePopular.isEmpty()) {
            cachePopular = youtubeService.popularVideos();
        } else {
            System.out.println("Retrieved list from cache.");
        }
        return cachePopular;
    }

    @Override
    public Video getVideo(String videoId)
    {
        Video video = cacheAll.get(videoId);
        if (video == null) {
            video = youtubeService.getVideo(videoId);
            cacheAll.put(videoId, video);
        } else {
            System.out.println("Retrieved video '" + videoId + "' from cache.");
        }
        return video;
    }

    public void reset()
    {
        cachePopular.clear();
        cacheAll.clear();
    }
}
