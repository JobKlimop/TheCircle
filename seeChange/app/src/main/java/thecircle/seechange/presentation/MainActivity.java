package thecircle.seechange.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import thecircle.seechange.R;
public class MainActivity extends AppCompatActivity {

    /**
     * PLEASE WRITE RTMP BASE URL of the your RTMP SERVER.
     */
    public static final String RTMP_BASE_URL = "rtmp://145.49.56.105/LiveApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openVideoBroadcaster(View view) {
        Intent i = new Intent(this, StreamActivity.class);
        startActivity(i);
    }


}
