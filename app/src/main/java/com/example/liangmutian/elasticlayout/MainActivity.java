package com.example.liangmutian.elasticlayout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout mLinearLayout1, mLinearLayout2;
    int l2Height;
    private int nowNumber;
    private ArrayList<View> mViews = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout1 = (LinearLayout) findViewById(R.id.activity_main);
        mLinearLayout2 = (LinearLayout) findViewById(R.id.ll1);


        mLinearLayout2.post(new Runnable() {
            @Override
            public void run() {
                l2Height = mLinearLayout2.getHeight();
                // w = mLinearLayout2.getWidth();
                //AnimTools.anim(mLinearLayout2,mLinearLayout2.getHeight(),mLinearLayout2.getHeight(),1);

                //Toast.makeText(MainActivity.this, "h:" + l2Height, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void on(View v) {

        //Toast.makeText(MainActivity.this, "h1:" + mLinearLayout2.getHeight(), Toast.LENGTH_SHORT).show();

        AnimTools.anim(mLinearLayout2, 0, l2Height, 500);


    }

    public void off(View v) {


        l2Height = mLinearLayout2.getHeight();


        AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), 0, 500);


    }

    public void add(View v) {

        nowNumber++;

        final View view = LayoutInflater.from(this).inflate(R.layout.item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_item);

        mViews.add(view);

        textView.setText("num:" + nowNumber);



        mLinearLayout2.addView(view);


        AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), mLinearLayout2.getHeight() + AnimTools.dip2px(this, 50), 200);


    }

    public void del(View v) {

        AnimTools.anim(mLinearLayout2, mLinearLayout2.getHeight(), mLinearLayout2.getHeight() - AnimTools.dip2px(this, 50), 200);

        new Handler().postDelayed(new Runnable() {

            public void run() {

                mLinearLayout2.removeView(mViews.get(mViews.size() - 1));
                mViews.remove(mViews.size() - 1);


            }

        }, 200);


    }
}
