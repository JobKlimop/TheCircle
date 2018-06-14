package thecircle.seechange.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.antmedia.android.broadcaster.LiveVideoBroadcaster;
import thecircle.seechange.R;
import thecircle.seechange.presentation.StreamActivity;

public class Fragment1 extends Fragment {
    private ViewGroup mRootView;
    private GLSurfaceView mGLView;
    private Intent mLiveVideoBroadcasterServiceIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view=inflater.inflate(R.layout.fragment_frag, container, false);
        mLiveVideoBroadcasterServiceIntent = new Intent(getActivity(), LiveVideoBroadcaster.class);
        //this makes service do its job until done
//        startService(mLiveVideoBroadcasterServiceIntent);
        mRootView = (ViewGroup) container.findViewById(R.id.root_layout);
        mGLView = (GLSurfaceView) container.findViewById(R.id.cameraPreview_surfaceView);
        if (mGLView != null) {
            mGLView.setEGLContextClientVersion(2);     // select GLES 2.0
        }
        return view;
    }
}
