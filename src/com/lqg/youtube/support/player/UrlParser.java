package com.lqg.youtube.support.player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class UrlParser {
    String YOUTUBE_VIDEO_INFORMATION_URL = "http://www.youtube.com/get_video_info?&video_id=";

    /**
     * Calculate the YouTube URL to load the video.  Includes retrieving a token that YouTube
     * requires to play the video.
     *
     * @param pYouTubeFmtQuality quality of the video.  17=low, 18=high
     * @param bFallback          whether to fallback to lower quality in case the supplied quality is not available
     * @param pYouTubeVideoId    the id of the video
     * @return the url string that will retrieve the video
     * @throws java.io.IOException
     * @throws org.apache.http.client.ClientProtocolException
     * @throws java.io.UnsupportedEncodingException
     */
    public String calculateYouTubeUrl(String pYouTubeFmtQuality, boolean pFallback, String pYouTubeVideoId) throws IOException, UnsupportedEncodingException {

        String lUriStr = null;
        HttpClient lClient = new DefaultHttpClient();

        HttpGet lGetMethod = new HttpGet(YOUTUBE_VIDEO_INFORMATION_URL +
                pYouTubeVideoId);

        HttpResponse lResp = null;

        lResp = lClient.execute(lGetMethod);

        ByteArrayOutputStream lBOS = new ByteArrayOutputStream();
        String lInfoStr = null;

        lResp.getEntity().writeTo(lBOS);
        lInfoStr = new String(lBOS.toString("UTF-8"));

        String[] lArgs = lInfoStr.split("&");
        Map<String, String> lArgMap = new HashMap<String, String>();
        for (int i = 0; i < lArgs.length; i++) {
            String[] lArgValStrArr = lArgs[i].split("=");
            if (lArgValStrArr != null) {
                if (lArgValStrArr.length >= 2) {
                    lArgMap.put(lArgValStrArr[0], URLDecoder.decode(lArgValStrArr[1]));
                }
            }
        }

        //Find out the URI string from the parameters

        //Populate the list of formats for the video
        String lFmtList = URLDecoder.decode(lArgMap.get("fmt_list"));
        ArrayList<Format> lFormats = new ArrayList<Format>();
        if (null != lFmtList) {
            String lFormatStrs[] = lFmtList.split(",");

            for (String lFormatStr : lFormatStrs) {
                Format lFormat = new Format(lFormatStr);
                lFormats.add(lFormat);
            }
        }

        //Populate the list of streams for the video
        String lStreamList = lArgMap.get("url_encoded_fmt_stream_map");
        if (null != lStreamList) {
            String lStreamStrs[] = lStreamList.split(",");
            ArrayList<VideoStream> lStreams = new ArrayList<VideoStream>();
            for (String lStreamStr : lStreamStrs) {
                VideoStream lStream = new VideoStream(lStreamStr);
                lStreams.add(lStream);
            }

            //Search for the given format in the list of video formats
            // if it is there, select the corresponding stream
            // otherwise if fallback is requested, check for next lower format
            int lFormatId = Integer.parseInt(pYouTubeFmtQuality);

            Format lSearchFormat = new Format(lFormatId);
            while (!lFormats.contains(lSearchFormat) && pFallback) {
                int lOldId = lSearchFormat.getId();
                int lNewId = getSupportedFallbackId(lOldId);

                if (lOldId == lNewId) {
                    break;
                }
                lSearchFormat = new Format(lNewId);
            }

            int lIndex = lFormats.indexOf(lSearchFormat);
            if (lIndex >= 0) {
                VideoStream lSearchStream = lStreams.get(lIndex);
                lUriStr = lSearchStream.getUrl();
            }

        }
        //Return the URI string. It may be null if the format (or a fallback format if enabled)
        // is not found in the list of formats for the video
        return lUriStr;
    }


    public int getSupportedFallbackId(int pOldId) {
        final int lSupportedFormatIds[] = {13,  //3GPP (MPEG-4 encoded) Low quality
                17,  //3GPP (MPEG-4 encoded) Medium quality
                18,  //MP4  (H.264 encoded) Normal quality
                22,  //MP4  (H.264 encoded) High quality
                37   //MP4  (H.264 encoded) High quality
        };
        int lFallbackId = pOldId;
        for (int i = lSupportedFormatIds.length - 1; i >= 0; i--) {
            if (pOldId == lSupportedFormatIds[i] && i > 0) {
                lFallbackId = lSupportedFormatIds[i - 1];
            }
        }
        return lFallbackId;
    }

}
