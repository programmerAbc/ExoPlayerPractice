package com.practice.exoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PICK_FILE = 1;
    SimpleExoPlayer simpleExoPlayer;
    @BindView(R.id.simpleExoPlayerView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.openBtn)
    Button openBtn;
    private IntentIntegrator qrScan;
    boolean isMirrored = false;
    int videoWidth = 1;
    int videoHeight = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        final TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.setVideoDebugListener(new VideoRendererEventListener() {
            @Override
            public void onVideoEnabled(DecoderCounters counters) {

            }

            @Override
            public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

            }

            @Override
            public void onVideoInputFormatChanged(Format format) {

            }

            @Override
            public void onDroppedFrames(int count, long elapsedMs) {

            }

            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                videoWidth = width;
                videoHeight = height;
            }

            @Override
            public void onRenderedFirstFrame(Surface surface) {

            }

            @Override
            public void onVideoDisabled(DecoderCounters counters) {

            }
        });
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        qrScan = new IntentIntegrator(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.mirrorBtn, R.id.sampleVideo1Btn, R.id.sampleVideo2Btn, R.id.openBtn, R.id.scanBtn, R.id.btnSpeed0_2, R.id.btnSpeed0_4, R.id.btnSpeed0_6, R.id.btnSpeed0_8, R.id.btnSpeed1})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.openBtn:
                new MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(REQUEST_CODE_PICK_FILE)
                        .withHiddenFiles(true)
                        .start();
                break;
            case R.id.scanBtn:
                qrScan.initiateScan();
                break;
            case R.id.btnSpeed0_2:
                simpleExoPlayer.setPlaybackParameters(new PlaybackParameters(0.2f, 1f));
                break;
            case R.id.btnSpeed0_4:
                simpleExoPlayer.setPlaybackParameters(new PlaybackParameters(0.4f, 1f));
                break;
            case R.id.btnSpeed0_6:
                simpleExoPlayer.setPlaybackParameters(new PlaybackParameters(0.6f, 1f));
                break;
            case R.id.btnSpeed0_8:
                simpleExoPlayer.setPlaybackParameters(new PlaybackParameters(0.8f, 1f));
                break;
            case R.id.btnSpeed1:
                simpleExoPlayer.setPlaybackParameters(new PlaybackParameters(1f, 1f));
                break;
            case R.id.sampleVideo1Btn: {
                String filePath = "/storage/emulated/0/aiyige/cache/video/wudaoefe212e81fb42c0c94557f9dcca20f99.mp4";
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayerPractice"), new DefaultBandwidthMeter());
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(Uri.fromFile(new File(filePath)), dataSourceFactory, extractorsFactory, null, null);
                simpleExoPlayer.prepare(mediaSource);
                break;
            }
            case R.id.sampleVideo2Btn: {
                String filePath = "/storage/emulated/0/aiyige/cache/video/wudao4563f1505f5625603670df4b1c9f0225.mp4";
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayerPractice"), new DefaultBandwidthMeter());
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(Uri.fromFile(new File(filePath)), dataSourceFactory, extractorsFactory, null, null);
                simpleExoPlayer.prepare(mediaSource);
                break;
            }
            case R.id.mirrorBtn: {
                TextureView textureView = (TextureView) simpleExoPlayerView.getVideoSurfaceView();
                if (isMirrored) {
                    int dw = simpleExoPlayerView.getMeasuredWidth();
                    int dh = simpleExoPlayerView.getMeasuredHeight();
                    double ar = (double) videoWidth / (double) videoHeight;
                    double dar = (double) dw / (double) dh;
                    if (dar < ar)
                        dh = (int) (dw / ar);
                    else
                        dw = (int) (dh * ar);

                    Matrix matrix = new Matrix();
                    matrix.setScale(-1, 1);
                    matrix.postTranslate(dw, 0);
                    textureView.setTransform(matrix);
                } else {
                    textureView.setTransform(new Matrix());
                }
                isMirrored = !isMirrored;
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        simpleExoPlayer.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_FILE: {
                if (resultCode == RESULT_OK) {
                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayerPractice"), new DefaultBandwidthMeter());
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(Uri.fromFile(new File(filePath)), dataSourceFactory, extractorsFactory, null, null);
                    simpleExoPlayer.prepare(mediaSource);
                }
                break;
            }
            case IntentIntegrator.REQUEST_CODE: {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null && result.getContents() != null) {
                    String urlStr = result.getContents();
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayerPractice"), new DefaultBandwidthMeter());
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(urlStr), dataSourceFactory, extractorsFactory, null, null);
                    simpleExoPlayer.prepare(mediaSource);
                } else {
                    Toast.makeText(this, "Scan failed!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        simpleExoPlayer.release();
        super.onDestroy();
    }

}
