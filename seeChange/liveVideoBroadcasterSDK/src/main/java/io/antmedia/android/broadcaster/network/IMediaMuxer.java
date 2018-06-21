package io.antmedia.android.broadcaster.network;

import android.content.Context;

/**
 * Created by faraklit on 03.03.2016.
 */
public interface IMediaMuxer {

    int SEND_AUDIO = 0;
    int SEND_VIDEO = 1;
    int STOP_STREAMING = 2;

    /**
     *
     * @return the last audio frame timestamp in milliseconds
     */
    int getLastAudioFrameTimeStamp();


    /**
     *
     * @return the last video frame timestamp in milliseconds
     */
    int getLastVideoFrameTimeStamp();


    boolean open(String uri, Context c);

    boolean isConnected();

    void writeAudio(byte[] data, int size, int presentationTime);

    void writeVideo(byte[] data, int length, int presentationTime);

    void stopMuxer();

    int getFrameCountInQueue();

    int getVideoFrameCountInQueue();
}
