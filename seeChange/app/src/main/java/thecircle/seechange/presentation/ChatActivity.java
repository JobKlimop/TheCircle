package thecircle.seechange.presentation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import thecircle.seechange.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatFragment frg=new ChatFragment();
        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager

        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.container, frg, "Frag_Top_tag");


        transaction.commit();
    }
}
