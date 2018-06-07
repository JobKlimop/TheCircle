package thecircle.seechange;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class CameraFragment extends Fragment {


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.publisher_container, CameraFragment.newInstance())
                    .commit();
        }
    }
    private FrameLayout mPublisherViewContainer;

   // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, View view) {
        // Inflate the layout for this fragment
        mPublisherViewContainer = (FrameLayout) view.findViewById(R.id.camera);
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }
}
