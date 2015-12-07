package com.view.button;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcgtgjyb.test.myapplication.R;

/**
 * Created by bigwen on 2015/12/6.
 */
public class TabView extends LinearLayout {
    private Context mContext;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private int initTab=0;

    public TabView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.tab_view, this);
        mTextView1=(TextView)findViewById(R.id.center1);
        mTextView2=(TextView)findViewById(R.id.center2);
        mTextView3=(TextView)findViewById(R.id.center3);
        setTab(initTab);
    }

    public void setInitTab(int position){
        initTab=position;
    }

    public void setTab(int position){
       switch (position){
           case 0:
               mTextView1.setBackgroundColor(Color.parseColor("#80deea"));
               mTextView2.setBackgroundColor(Color.parseColor("#ffffff"));
               mTextView3.setBackgroundColor(Color.parseColor("#ffffff"));
               break;
           case 1:
               mTextView1.setBackgroundColor(Color.parseColor("#ffffff"));
               mTextView2.setBackgroundColor(Color.parseColor("#80deea"));
               mTextView3.setBackgroundColor(Color.parseColor("#ffffff"));
               break;
           case 2:
               mTextView1.setBackgroundColor(Color.parseColor("#ffffff"));
               mTextView2.setBackgroundColor(Color.parseColor("#ffffff"));
               mTextView3.setBackgroundColor(Color.parseColor("#80deea"));
               break;
       }
    }
}
