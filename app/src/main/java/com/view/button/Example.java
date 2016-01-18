package com.view.button;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.another.custom.view.AnotherActivity;
import com.bcgtgjyb.test.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class Example extends LinearLayout {
    private TabView center;
    private View topView;
    private ScrollView mScrollView;
    private String TAG = "MyScrollView";
    private OverScroller mScroller;
    private boolean isTopHidden = false;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity, mMinimumVelocity;
    private Context mContext;
    private ViewPager viewPager;
    private float startX;
    private ViewGroup view;
    private ScrollView scrollView2;
    private ScrollView scrollView3;
    private LinearLayout thisLayout;
    private int topHeight = 0;

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
        mScroller = new OverScroller(mContext);
        mVelocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(mContext).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(mContext).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        Log.i(TAG, "MyScrollView: ");
        thisLayout = this;
        topView = findViewById(R.id.topView);
        center = (TabView) findViewById(R.id.tab_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        initViewPager();
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                topHeight = topView.getMeasuredHeight();
                ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                params.height = getMeasuredHeight() - topView.getMeasuredHeight();
                viewPager.setLayoutParams(params);
                thisLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, thisLayout.getMeasuredHeight() + topView.getMeasuredHeight()));

            }
        });

        topView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AnotherActivity.class));
            }
        });
    }

    private void initViewPager() {
        List<View> list = new ArrayList<View>();
        view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.viewpager, null);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView1);
        scrollView2 = (ScrollView) view.findViewById(R.id.scrollView2);
        scrollView3 = (ScrollView) view.findViewById(R.id.scrollView3);

        view.removeAllViews();
        list.add(mScrollView);
        list.add(scrollView2);
        list.add(scrollView3);
        viewPager.setAdapter(new TabPagerAdapter(list));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                center.setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, topView.getMeasuredHeight());
        invalidate();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }


    int lastY = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent ");
        int action = ev.getAction();
        float y = ev.getY();
        float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = y;
                startX = x;
                lastY = (int)y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - startY;
                //横向滑动不拦截
                if (Math.abs(x - startX) > Math.abs(y - startY)) {
//                    return false;
                }else {
                    ScrollView scrollView;
                    if(viewPager.getCurrentItem()==0){
                        scrollView = mScrollView;
                    }else if(viewPager.getCurrentItem()==1){
                        scrollView = scrollView2;
                    }else {
                        scrollView = scrollView3;
                    }
                    //手指向上滑
                    if(dy<0){
                        if(this.getScrollY() < topHeight) {
                            Log.i(TAG, "dispatchTouchEvent this="+dy);
//                            return true;
                        }else {
                            Log.i(TAG, "dispatchTouchEvent mScrollView=" + dy);
//                            scrollView.scrollBy(0,-(int)dy);
//                            return false;
                        }
                    }
                    //手指向下滑
                    else {

                        if(scrollView.getScrollY()==0){
//                            this.scrollBy(0,-(int)dy);
//                            viewPager.requestDisallowInterceptTouchEvent(true);
//                            return true;
//                            this.onInterceptTouchEvent(ev);
                            Log.i(TAG, "dispatchTouchEvent 手指向下滑"+y+"   "+lastY);
                            Log.i(TAG, "dispatchTouchEvent 手指向下滑"+(-y+lastY));
                            this.scrollBy(0,-(int)y+lastY);
                        }else {
//                            return false;
//                            scrollView.scrollBy(0,-(int)dy);
                        }

                    }
                }
                lastY = (int)y;
                Log.i(TAG, "dispatchTouchEvent end"+lastY);
            case MotionEvent.ACTION_UP:
//                lastY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent all");
        int action = ev.getAction();
        float y = ev.getY();
        float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = y;
                startX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - startY;
                //横向滑动不拦截
                if (Math.abs(x - startX) > Math.abs(y - startY)) {
                    Log.i(TAG, "onInterceptTouchEvent 横向滑动不拦截");
                    return false;
                }else {
                    ScrollView scrollView;
                    if(viewPager.getCurrentItem()==0){
                        scrollView = mScrollView;
                    }else if(viewPager.getCurrentItem()==1){
                        scrollView = scrollView2;
                    }else {
                        scrollView = scrollView3;
                    }
                    //手指向上滑
                    if(dy<0){
                        if(this.getScrollY() < topHeight) {
                            Log.i(TAG, "onInterceptTouchEvent this="+dy);
                            return true;
                        }else {
                            Log.i(TAG, "onInterceptTouchEvent mScrollView=" + dy);
//                            scrollView.scrollBy(0,-(int)dy);
                            return false;
                        }
                    }
                    //手指向下滑
                    else {
                        if(scrollView.getScrollY()==0){
                            Log.i(TAG, "onInterceptTouchEvent this="+scrollView.getScrollY());
                            if(this.getScrollY()==0){
                                return false;
                            }else {

                            }
//                            this.scrollBy(0,-(int)dy);
                            return true;
                        }else {
                            Log.i(TAG, "onInterceptTouchEvent scrollView="+scrollView.getScrollY());
                            return false;
//                            scrollView.scrollBy(0,-(int)dy);
                        }
                    }
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private float startY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent ");
        mVelocityTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = y;
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - startY;
                startY = y;

                Log.i(TAG, "onTouchEvent "+dy);
                ScrollView scrollView;
                if(viewPager.getCurrentItem()==0){
                    scrollView = mScrollView;
                }else if(viewPager.getCurrentItem()==1){
                    scrollView = scrollView2;
                }else {
                    scrollView = scrollView3;
                }
                //手指向上滑
                if(dy<0){
                    if(this.getScrollY() < topHeight) {
                        Log.i(TAG, "onTouchEvent this="+dy);
                        scrollBy(0, -(int) dy);
                    }else {
                        Log.i(TAG, "onTouchEvent mScrollView="+dy);
                        scrollView.scrollBy(0,-(int)dy);
                    }
                }
                //手指向下滑
                else {
                    if(scrollView.getScrollY()==0){
                        if(this.getScrollY()==0){

                        }else {
                            this.scrollBy(0,-(int)dy);
                        }

                    }else {
                        scrollView.scrollBy(0,-(int)dy);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                mVelocityTracker.clear();
//                Log.i(TAG, "onTouchEvent "+this.getScaleY());
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y >= topView.getMeasuredHeight()) {
            y = topView.getMeasuredHeight();
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        isTopHidden = getScrollY() == topView.getMeasuredHeight();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    private class TabPagerAdapter extends PagerAdapter {
        List<View> mList;

        public TabPagerAdapter(List<View> l) {
            mList = l;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }
    }


}
