package com.lqg.youtube.ui.play;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.lqg.youtube.R;
import com.lqg.youtube.support.http.Search;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LQG on 2014/12/4.
 */
@EActivity(R.layout.playvideo_youtube)
@Fullscreen
public class PlayVideoUsingYouTuBeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    @ViewById(R.id.player)
    YouTubePlayerView player;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @AfterViews
    void config() {
        player.initialize(Search.apiKey, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, " onInitializationFailure ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        String videoId = getIntent().getStringExtra("videoId");
        if (!b) {
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            player.initialize(Search.apiKey, this);
        }
    }

}
