package com.model.DTO.pages;

import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import lombok.Data;


public class YouTubeVideoTo {
    private VideoDetails videoDetails;

    public YouTubeVideoTo(YoutubeVideo video) {
        videoDetails = video.details();
    }

    public String getTitle() {
        return videoDetails.title();
    }

    public String getLengthMinutesAndSeconds() {
        String length = Integer.toString(videoDetails.lengthSeconds() / 60);

        if(videoDetails.lengthSeconds() % 60 / 10 > 0)
            length += ':' + Integer.toString(videoDetails.lengthSeconds() % 60);
        else
            length += ":0" + Integer.toString(videoDetails.lengthSeconds() % 60);

        return length;
    }
}
