package com.websoftq.socialnetwork.camera2API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.websoftq.socialnetwork.R;
import com.websoftq.socialnetwork.camera2API.customVideoViews.BackgroundTask;
import com.websoftq.socialnetwork.camera2API.customVideoViews.BarThumb;
import com.websoftq.socialnetwork.camera2API.customVideoViews.CustomRangeSeekBar;
import com.websoftq.socialnetwork.camera2API.customVideoViews.OnRangeSeekBarChangeListener;
import com.websoftq.socialnetwork.camera2API.customVideoViews.OnVideoTrimListener;
import com.websoftq.socialnetwork.camera2API.customVideoViews.TileView;
import com.websoftq.socialnetwork.camera2API.utils.Utility;

import java.io.File;

import gun0912.tedbottompicker.util.RealPathUtil;


public class VideoTrimmerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtVideoCancel;
    private TextView txtVideoUpload;
    private RelativeLayout rlVideoView;
    private TileView tileView;
    private CustomRangeSeekBar mCustomRangeSeekBarNew;
    private VideoView mVideoView;
    private ImageView imgPlay;
    private int mDuration = 0;
    private int mTimeVideo = 0;
    private int mStartPosition = 0;
    private int mEndPosition = 0;
    // set your max video trim seconds
    private int mMaxDuration = 90;
    private Handler mHandler = new Handler();
    private ProgressDialog mProgressDialog;
    String srcFile;
    String dstFile;
    private final int REQUEST_VIDEO_TRIMMER = 0x12;
    OnVideoTrimListener mOnVideoTrimListener = new OnVideoTrimListener() {
        @Override
        public void onTrimStarted() {
            // Create an indeterminate progress dialog
            mProgressDialog = new ProgressDialog(VideoTrimmerActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Saving....");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        public void getResult(Uri uri) {
            Log.e("TAG", "uri : "+uri);
            mProgressDialog.dismiss();
            Bundle conData = new Bundle();
            conData.putString("INTENT_VIDEO_FILE", uri.getPath());
            Log.e("TAG", "uri : "+uri.getPath());
            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void cancelAction() {
            mProgressDialog.dismiss();
        }

        @Override
        public void onError(String message) {
            mProgressDialog.dismiss();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trim);
        txtVideoCancel = (TextView) findViewById(R.id.txtVideoCancel);
        txtVideoUpload = (TextView) findViewById(R.id.txtVideoUpload);
        rlVideoView = (RelativeLayout) findViewById(R.id.llVideoView);
        tileView = (TileView) findViewById(R.id.timeLineView);
        mCustomRangeSeekBarNew = ((CustomRangeSeekBar) findViewById(R.id.timeLineBar));
        mVideoView = (VideoView) findViewById(R.id.videoView);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        txtVideoCancel.setOnClickListener(this);
        txtVideoUpload.setOnClickListener(this);
        Intent intent = new
                Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent,"SelectVideo"),REQUEST_VIDEO_TRIMMER);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                onVideoPrepared(mp);
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onVideoCompleted();
            }
        });

        // handle your range seekbar changes
        mCustomRangeSeekBarNew.addOnRangeSeekBarListener(new OnRangeSeekBarChangeListener() {
            @Override
            public void onCreate(CustomRangeSeekBar customRangeSeekBarNew, int index, float value) {
                // Do nothing
            }

            @Override
            public void onSeek(CustomRangeSeekBar customRangeSeekBarNew, int index, float value) {
                onSeekThumbs(index, value);
            }

            @Override
            public void onSeekStart(CustomRangeSeekBar customRangeSeekBarNew, int index, float value) {
                if (mVideoView != null) {
                   // mHandler.removeCallbacks(mUpdateTimeTask);
                    mVideoView.seekTo(mStartPosition * 1000);
                    mVideoView.pause();
                    imgPlay.setBackgroundResource(R.drawable.ic_play_button);
                }
            }

            @Override
            public void onSeekStop(CustomRangeSeekBar customRangeSeekBarNew, int index, float value) {
                onStopSeekThumbs();
            }
        });

        imgPlay.setOnClickListener(this);

        // handle changes on seekbar for video play
    }

    @Override
    public void onClick(View view) {
        if (view == txtVideoCancel) {
            finish();
        } else if (view == txtVideoUpload) {
            int diff = mEndPosition - mStartPosition;
            ///if (diff < 5) {
            //                Toast.makeText(VideoTrimmerActivity.this, getString(R.string.video_length_validation_min),
            //                        Toast.LENGTH_LONG).show();
            //            }else
            if (diff > 60) {
                Toast.makeText(VideoTrimmerActivity.this, getString(R.string.video_length_validation_max),
                        Toast.LENGTH_LONG).show();
            } else {
                MediaMetadataRetriever
                        mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(VideoTrimmerActivity.this, Uri.parse(srcFile));
                final File file = new File(srcFile);

                //notify that video trimming started
                if (mOnVideoTrimListener != null)
                    mOnVideoTrimListener.onTrimStarted();

                BackgroundTask.execute(
                        new BackgroundTask.Task("", 0L, "") {
                            @Override
                            public void execute() {
                                try {
                                   Utility.startTrim(file, dstFile, mStartPosition * 1000, mEndPosition * 1000, mOnVideoTrimListener);
                                } catch (final Throwable e) {
                                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                                }
                            }
                        }
                );
            }

        } else if (view == imgPlay) {
            if (mVideoView.isPlaying()) {
                if (mVideoView != null) {
                    mVideoView.pause();
                    imgPlay.setBackgroundResource(R.drawable.ic_play_button);
                }
            } else {
                if (mVideoView != null) {
                    mVideoView.start();
                    imgPlay.setBackgroundResource(R.drawable.ic_pause_button);
                    /*if (seekBarVideo.getProgress() == 0) {
                        txtVideoLength.setText("00:00");
                        updateProgressBar();
                    }*/
                }
            }
        }
    }

    private void setBitmap(Uri mVideoUri) {
        tileView.setVideo(mVideoUri);
    }

    private void onVideoPrepared(@NonNull MediaPlayer mp) {
        mDuration = mVideoView.getDuration() / 1000;
        setSeekBarPosition();
    }

  /*  public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }
*/
/*
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (seekBarVideo.getProgress() >= seekBarVideo.getMax()) {
                seekBarVideo.setProgress((mVideoView.getCurrentPosition() - mStartPosition * 1000));
                txtVideoLength.setText(milliSecondsToTimer(seekBarVideo.getProgress()) + "");
                mVideoView.seekTo(mStartPosition * 1000);
                mVideoView.pause();
                seekBarVideo.setProgress(0);
                txtVideoLength.setText("00:00");
                imgPlay.setBackgroundResource(R.drawable.ic_play_button);
            } else {
                seekBarVideo.setProgress((mVideoView.getCurrentPosition() - mStartPosition * 1000));
                txtVideoLength.setText(milliSecondsToTimer(seekBarVideo.getProgress()) + "");
                mHandler.postDelayed(this, 100);
            }
        }
    };
*/

    private void setSeekBarPosition() {

        if (mDuration >= mMaxDuration) {
            mStartPosition = 0;
            mEndPosition = mMaxDuration;

            mCustomRangeSeekBarNew.setThumbValue(0, (mStartPosition * 100) / mDuration);
            mCustomRangeSeekBarNew.setThumbValue(1, (mEndPosition * 100) / mDuration);

        } else {
            mStartPosition = 0;
            mEndPosition = mDuration;
        }


        mTimeVideo = mDuration;
        mCustomRangeSeekBarNew.initMaxWidth();
       // seekBarVideo.setMax(mMaxDuration * 1000);
        mVideoView.seekTo(mStartPosition * 1000);

      /*  String mStart = mStartPosition + "";
        if (mStartPosition < 10)
            mStart = "0" + mStartPosition;

        int startMin = Integer.parseInt(mStart) / 60;
        int startSec = Integer.parseInt(mStart) % 60;

        String mEnd = mEndPosition + "";
        if (mEndPosition < 10)
            mEnd = "0" + mEndPosition;

        int endMin = Integer.parseInt(mEnd) / 60;
        int endSec = Integer.parseInt(mEnd) % 60;*/
    }

    /**
     * called when playing video completes
     */
    private void onVideoCompleted() {
       /* mHandler.removeCallbacks(mUpdateTimeTask);
        seekBarVideo.setProgress(0);*/
        mVideoView.seekTo(mStartPosition * 1000);
        mVideoView.pause();
        imgPlay.setBackgroundResource(R.drawable.ic_play_button);
    }

    /**
     * Handle changes of left and right thumb movements
     *
     * @param index index of thumb
     * @param value value
     */
    private void onSeekThumbs(int index, float value) {
        switch (index) {
            case BarThumb.LEFT: {
                mStartPosition = (int) ((mDuration * value) / 100L);
                mVideoView.seekTo(mStartPosition * 1000);
                break;
            }
            case BarThumb.RIGHT: {
                mEndPosition = (int) ((mDuration * value) / 100L);
                break;
            }
        }
        mTimeVideo = (mEndPosition - mStartPosition);
       /* seekBarVideo.setMax(mTimeVideo * 1000);
        seekBarVideo.setProgress(0);*/
        mVideoView.seekTo(mStartPosition * 1000);

      /*  String mStart = mStartPosition + "";
        if (mStartPosition < 10)
            mStart = "0" + mStartPosition;

        int startMin = Integer.parseInt(mStart) / 60;
        int startSec = Integer.parseInt(mStart) % 60;

        String mEnd = mEndPosition + "";
        if (mEndPosition < 10)
            mEnd = "0" + mEndPosition;
        int endMin = Integer.parseInt(mEnd) / 60;
        int endSec = Integer.parseInt(mEnd) % 60;*/

    }

    private void onStopSeekThumbs() {
//        mMessageHandler.removeMessages(SHOW_PROGRESS);
//        mVideoView.pause();
//        mPlayView.setVisibility(View.VISIBLE);
    }


    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString;
        String minutesString;


        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        finalTimerString = finalTimerString + minutesString + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_VIDEO_TRIMMER:
                    final Uri selectedUri = data.getData();
                    srcFile = RealPathUtil.getRealPath(VideoTrimmerActivity.this, selectedUri);
                    Log.e("TAG","videoPath : "+srcFile);
                    if (srcFile != null) {
                        File newFile = new File(srcFile);
                        if (newFile.exists()) {
                            Log.e("TAG", "srcFile : "+srcFile);
                            File mySubDir = new File(getCacheDir(), "MyCache");
                            if (!mySubDir.exists()) {
                                mySubDir.mkdir();
                            }
                            File file = new File(mySubDir, "LastTrim"+System.currentTimeMillis());
                            if (file.exists()) {
                                file.delete();
                            }
   /*     dstFile = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + new Date().getTime()
                + Utility.VIDEO_FORMAT;*/
                            dstFile = file.toString()+ Utility.VIDEO_FORMAT;;
                            Log.e("TAG", "dstFile : "+dstFile);
                            tileView.post(new Runnable() {
                                @Override
                                public void run() {
                                    setBitmap(Uri.parse(srcFile));
                                    mVideoView.setVideoURI(Uri.parse(srcFile));
                                }
                            });

                        }else {
                            Intent intent = new Intent();
                            setResult(RESULT_CANCELED, intent);
                            finish();
                        }
                    }else {
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                    break;
            }
        }else {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }
}
