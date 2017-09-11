package bakingapp.project.anuja.com.bakeapp.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import bakingapp.project.anuja.com.bakeapp.R;
import bakingapp.project.anuja.com.bakeapp.pojoclass.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by USER on 22-07-2017.
 */

public class StepDetailViewFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.recipe_playerView)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.button_Next)
    Button buttonNext;
    @BindView(R.id.button_prev)
    Button buttonPrev;
    Unbinder unbinder;



    public static final String LIST_POSITION = "position";
    public static final String STEP_LIST = "step";
    private List<Step> step_List;
    private int position;
    private SimpleExoPlayer mSimpleExoPlayer;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        System.out.println("---------- inside onCreateView --------------------");
        if(savedInstanceState!= null){
            step_List = savedInstanceState.getParcelableArrayList(STEP_LIST);
            position = savedInstanceState.getInt(LIST_POSITION);

            System.out.println("----------  step_List --------------------"+step_List);
            System.out.println("----------  position --------------------"+position);
        }
        View rootView = inflater.inflate(R.layout.activity_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (step_List != null) {

//            mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
//                    (getResources(), R.drawable.mybakestudio));
            System.out.println("------------------------------ inside step_List not null-----------------");

            initializeMediaSession();
            initializePlayer(Uri.parse(step_List.get(position).getVideoURL()));

            description.setText(step_List.get(position).getDescription());

            System.out.println("------------------------------ getDescription -----------------"+step_List.get(position).getDescription());
            System.out.println("------------------------------ getVideoURL -----------------"+step_List.get(position).getVideoURL());

            System.out.println("----------  step_List --------------------"+step_List);
            System.out.println("----------  position --------------------"+position);
//            if (step_List.get(position).getThumbnailURL() == "")
//                Picasso.with(getContext()).load(step_List.get(position).getThumbnailURL()).into(thumbnail);

        } else {
            Toast.makeText(getContext(), "No Steps Present", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("------------------------------ inside onSaveInstanceState-----------------");
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, (ArrayList<? extends Parcelable>) step_List);
        outState.putInt(LIST_POSITION, position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStep(List<Step> step) {
        this.step_List = step;
    }

    private void initializePlayer(Uri videoURL) {
        if (mSimpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);

            mSimpleExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoURL, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }



    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), StepDetailViewFragment.class.getSimpleName());

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    @Nullable
    @OnClick(R.id.button_prev)
    public void StepPrev() {
        if (position == 0)
            position = step_List.size() - 1;
        else
            position--;

        releasePlayer();
        description.setText(step_List.get(position).getDescription());
        initializePlayer(Uri.parse(step_List.get(position).getVideoURL()));

    }

    @Nullable
    @OnClick(R.id.button_Next)
    public void StepNext() {
        if (position < step_List.size() - 1)
            position++;
        else
            position = 0;

        releasePlayer();
        description.setText(step_List.get(position).getDescription());
        initializePlayer(Uri.parse(step_List.get(position).getVideoURL()));
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("step");
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mSimpleExoPlayer.setPlayWhenReady(true);
        }


        @Override
        public void onSkipToPrevious() {
            mSimpleExoPlayer.seekTo(0);
        }

        @Override
        public void onPause() {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }


    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
}

