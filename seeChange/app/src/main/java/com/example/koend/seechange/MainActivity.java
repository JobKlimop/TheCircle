package com.example.koend.seechange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.OpentokError;
import android.support.annotation.NonNull;
import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements  Session.SessionListener, PublisherKit.PublisherListener{

    private static String API_KEY = "46134312";
    private static String SESSION_ID = "2_MX40NjEzNDMxMn5-MTUyODM2ODczNzQ0OH5CeFE4aGNkdGF2OEdKK3dtOURTdjF1ODB-UH4";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjEzNDMxMiZzaWc9YjIwMTU4YjA1MGI3MzQzMTdlNGJkNmNlMzU1YTc4ZDUwNDU0M2M1YzpzZXNzaW9uX2lkPTJfTVg0ME5qRXpORE14TW41LU1UVXlPRE0yT0Rjek56UTBPSDVDZUZFNGFHTmtkR0YyT0VkS0szZHRPVVJUZGpGMU9EQi1VSDQmY3JlYXRlX3RpbWU9MTUyODM2ODczOCZub25jZT0wLjc2MTY5MDk5ODE4MTU2ODkmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTUyODQ1NTEzOCZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static String currentStreamerId = "1";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private Session mSession;
    private Publisher mPublisher;

    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.startStreaming);
        Button stopButton = (Button) findViewById(R.id.stopStreaming);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSession == null) {
                    requestPermissions();
                } else {
                    //nothing
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSession != null) {
                    mSession.disconnect();
                    mSession = null;

                } else {
                    // nothing
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }  

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);

            // initialize and connect to the session
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

// SessionListener methods

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    //publisher methods

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }
}
