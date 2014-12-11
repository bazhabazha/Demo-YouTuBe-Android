package com.lqg.youtube.ui.play;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.lqg.youtube.R;
import com.lqg.youtube.support.player.UrlParser;
import com.lqg.youtube.support.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by LQG on 2014/12/5.
 */
@EActivity(R.layout.playvideo_videoview)
@Fullscreen
public class PlayVideoUsingVideoViewActivity extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    @ViewById
    VideoView player;

    @ViewById
    ProgressBar playPb;

    MediaController mc;

    @AfterViews
    void afterView() {
        mc = new MediaController(this);
        player.setMediaController(mc);
        player.setOnErrorListener(this);
        player.setOnPreparedListener(this);

        String videoId = getIntent().getStringExtra("videoId");
        play(videoId);
    }

    @Background
    void play(String videoId) {
        try {
            String videoUrl = getUrlFromId(videoId);
            //LogUtil.d(videoUrl);
            startPlay(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    String getUrlFromId(String videoId) throws IOException {
        String lYouTubeFmtQuality = "18";
        return new UrlParser().calculateYouTubeUrl(lYouTubeFmtQuality, true, videoId);
    }

    @UiThread
    void startPlay(String url) {
        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        player.setVideoURI(Uri.parse(url));
        player.requestFocus();
        player.start();
        mc.show();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        finish();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playPb.setVisibility(View.GONE);
    }
}
