package thecircle.seechange.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import thecircle.seechange.R;
import thecircle.seechange.presentation.fragment.Fragment1;
import thecircle.seechange.presentation.fragment.Fragment2;

public class MainActivity extends AppCompatActivity {
    public static final String RTMP_BASE_URL = "rtmp://10.2.41.95/LiveApp/";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager

//            FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction
//            int id = item.getItemId();
//            Fragment fragment = null;
//            Class fragmentClass = null;
//
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    fragmentClass = Fragment1.class;
//                    Intent i = new Intent (getApplicationContext(), Fragment1.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                    return true;
//                case R.id.navigation_dashboard:
//                    fragmentClass = Fragment2.class;
//
//                    return true;
//                case R.id.navigation_notifications:
//                    fragmentClass = Fragment2.class;
//                    return true;
//            }
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//            return false;

//
//
            int id = item.getItemId();
            Fragment fragment = null;
            Class fragmentClass = null;
            if (id == R.id.navigation_home) {
                fragmentClass = Fragment1.class;
            } else if (id == R.id.navigation_dashboard) {
                fragmentClass = Fragment2.class;
            }
            else if (id == R.id.navigation_notifications) {
                fragmentClass = Fragment2.class;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}