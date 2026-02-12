package com.iti.cuisine.video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.cuisine.R;
import com.iti.cuisine.utils.presenter.PresenterHost;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class VideoFragment extends Fragment {

    private String videoUrl;

    private PresenterHost presenterHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VideoFragmentArgs args = VideoFragmentArgs.fromBundle(getArguments());
        videoUrl = args.getVideoUrl();
        presenterHost = (PresenterHost) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.video_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        view.findViewById(R.id.btnBack).setOnClickListener(v -> presenterHost.navigateBack());

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = extractVideoId(videoUrl);
                if (videoId != null && !videoId.isEmpty()) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }
        });
    }
    private String extractVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) {
            return null;
        }

        if (videoUrl.contains("youtu.be/")) {
            int startIndex = videoUrl.indexOf("youtu.be/") + 9;
            int endIndex = videoUrl.indexOf("?", startIndex);
            return endIndex != -1 ? videoUrl.substring(startIndex, endIndex) : videoUrl.substring(startIndex);
        }

        if (videoUrl.contains("v=")) {
            int startIndex = videoUrl.indexOf("v=") + 2;
            int endIndex = videoUrl.indexOf("&", startIndex);
            return endIndex != -1 ? videoUrl.substring(startIndex, endIndex) : videoUrl.substring(startIndex);
        }

        return null;
    }
}