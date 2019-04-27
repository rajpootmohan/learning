package com.dp.structural.proxy.downloader;

import java.util.HashMap;

import com.dp.structural.proxy.extlib.ThirdPartyYoutubeLib;
import com.dp.structural.proxy.extlib.Video;

//Media downloader app
public class YoutubeDownloader
{
    private ThirdPartyYoutubeLib api;

    public YoutubeDownloader(ThirdPartyYoutubeLib api)
    {
        this.api = api;
    }

    public void renderVideoPage(String videoId)
    {
        Video video = api.getVideo(videoId);
        System.out.println("\n-------------------------------");
        System.out.println("Video page (imagine fancy HTML)");
        System.out.println("ID: " + video.id);
        System.out.println("Title: " + video.title);
        System.out.println("Video: " + video.data);
        System.out.println("-------------------------------\n");
    }

    public void renderPopularVideos()
    {
        HashMap<String, Video> list = api.popularVideos();
        System.out.println("\n-------------------------------");
        System.out.println("Most popular videos on Youtube (imagine fancy HTML)");
        for (Video video : list.values()) {
            System.out.println("ID: " + video.id + " / Title: " + video.title);
        }
        System.out.println("-------------------------------\n");
    }
}
