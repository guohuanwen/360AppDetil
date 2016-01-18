package com.another.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcgtgjyb.test.myapplication.R;

/**
 * Created by bigwen on 2016/1/18.
 */
public class Example extends LinearLayout {
    private Context mContext;
    private TextView top;
    private TextView center;
    private int centerHeight;
    private String TAG = Example.class.getName();
    private MyScrollView scroll ;
    private TextView bottom;
    private int topHeight ;

    public Example(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public Example(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.custom_view,this);
        top = (TextView)findViewById(R.id.top);
        center = (TextView)findViewById(R.id.center);
        center.post(new Runnable() {
            @Override
            public void run() {
                topHeight = top.getMeasuredHeight();
                centerHeight = center.getMeasuredHeight();
                Log.i(TAG, "run centerHeight   topHeight"+centerHeight+" "+topHeight);
            }
        });
        scroll = (MyScrollView)findViewById(R.id.scroll);
        bottom = (TextView)findViewById(R.id.bottom);
        initScroll();
    }

    private void initScroll() {
        scroll.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollY(int scrollY) {
                Log.i(TAG, "onScrollY "+scrollY);
                if(scrollY >= topHeight){
                    bottom.setVisibility(View.VISIBLE);
                    center.setVisibility(View.INVISIBLE);
                }else {
                    bottom.setVisibility(View.INVISIBLE);
                    center.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
