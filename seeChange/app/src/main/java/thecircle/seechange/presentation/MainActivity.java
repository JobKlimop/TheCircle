package thecircle.seechange.presentation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import thecircle.seechange.R;
import thecircle.seechange.presentation.fragments.Middle_Fragment;
import thecircle.seechange.presentation.fragments.Top_Fragment;

//public class MainActivity extends AppCompatActivity {
//
//    /**
//     * PLEASE WRITE RTMP BASE URL of the your RTMP SERVER.
//     */
//    public static final String RTMP_BASE_URL = "rtmp://10.2.41.95/LiveApp/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    public void openVideoBroadcaster(View view) {
//        Intent i = new Intent(this, StreamActivity.class);
//        startActivity(i);
//    }
//
//
//}
public class MainActivity extends FragmentActivity {
    public static final String RTMP_BASE_URL = "rtmp://10.2.41.95/LiveApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Top_Fragment frg=new Top_Fragment();//create the fragment instance for the top fragment
        Middle_Fragment frg1=new Middle_Fragment();//create the fragment instance for the middle fragment

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager

        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.My_Container_1_ID, frg, "Frag_Top_tag");
        transaction.add(R.id.My_Container_2_ID, frg1, "Frag_Middle_tag");


        transaction.commit();

    }


}
