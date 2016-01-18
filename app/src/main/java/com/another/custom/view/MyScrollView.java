package com.another.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by bigwen on 2016/1/18.
 */
public class MyScrollView extends ScrollView  {
    private OnScrollListener onScrollListener;
    private String TAG = MyScrollView.class.getName();

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i(TAG, "onScrollChanged "+l+"   "+t+"   "+oldl+"   "+oldt);
        onScrollListener.onScrollY(this.getScrollY());
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener {
        void onScrollY(int scrollY);
    }


}
