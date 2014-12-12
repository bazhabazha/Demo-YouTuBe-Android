package com.lqg.youtube.support.http;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.lqg.youtube.support.util.LogUtil;
import com.lqg.youtube.support.util.SearchSetting;

import java.io.IOException;

public class Search {

    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    public static String apiKey = "YOURAPIKEY";

    private static YouTube youtube;

    static {
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("YOURAPPLICATIONNAME").build();
    }

    public static SearchListResponse search(String queryTerm) {

        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setType("video");
            search.setFields("items(id(videoId),snippet(title,description,thumbnails/default/url))");
            search.setMaxResults(Long.valueOf(SearchSetting.getInstance().getMaxResults()));
            search.setOrder(SearchSetting.getInstance().getOrder());
            search.setSafeSearch(SearchSetting.getInstance().getSafeSearch());
            search.setVideoDefinition(SearchSetting.getInstance().getVideoDefinition());
            search.setVideoDuration(SearchSetting.getInstance().getVideoDuration());
            search.setVideoType(SearchSetting.getInstance().getVideoType());
            return search.execute();
        } catch (GoogleJsonResponseException e) {
            LogUtil.d("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            LogUtil.d("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            LogUtil.d(t);
        }

        return null;
    }

}
