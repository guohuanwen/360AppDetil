package com.view.button;

import android.content.Context;
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

import com.bcgtgjyb.test.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class MyScrollView extends LinearLayout {
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

    public MyScrollView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        init();
    }

    private void init() {
        Log.i(TAG, "MyScrollView: ");
        LayoutInflater.from(mContext).inflate(R.layout.my_scroll_view, this);
        topView = findViewById(R.id.topView);
        center = (TabView) findViewById(R.id.tab_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        initViewPager();
        mScroller = new OverScroller(mContext);
        mVelocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(mContext).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(mContext).getScaledMinimumFlingVelocity();
//        initHeight();
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getMeasuredHeight()+center.getMeasuredHeight()));
    }

    private void initViewPager() {
        List<View> list = new ArrayList<View>();
        view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.viewpager, null);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView1);
        scrollView2 = (ScrollView) view.findViewById(R.id.scrollView2);
        scrollView3 = (ScrollView) view.findViewById(R.id.scrollView3);
        int height = viewPager.getMeasuredHeight();

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

    public void initHeight() {
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - topView.getMeasuredHeight();
        viewPager.setLayoutParams(params);
        this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getMeasuredHeight() + topView.getMeasuredHeight()));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged ");
    }

    private int s = 0;
    private int winHeight;
    private int centerHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        centerHeight = center.getMeasuredHeight();

        if(s==0) {
            winHeight = sizeHeight;
            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            params.height = getMeasuredHeight() - topView.getMeasuredHeight();
            this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getMeasuredHeight() + topView.getMeasuredHeight()));
            s=1;
        }

//        mScrollView.setLayoutParams(layoutParams);
//        scrollView2.setLayoutParams(layoutParams);
//        scrollView3.setLayoutParams(layoutParams);
        Log.i(TAG, "onMeasure sizeWidth=" + sizeWidth + "   sizeHeight=" + sizeHeight + "  centerHeight=" + centerHeight + "  winHeight" + winHeight+"  mScrollView"+mScrollView.getMeasuredHeight());

    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, topView.getMeasuredHeight());
        invalidate();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent");
        int action = ev.getAction();
        float y = ev.getY();
        float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                start = y;
                startX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent  " + isTopHidden + "   " + mScrollView);
                float dy = y - start;
                //横向滑动不拦截
                if (Math.abs(x - startX) > Math.abs(y - start)) {
                    return false;
                }
                //当顶部没有完全挡住，或者完全挡住后下滑，拦截触摸事件
                if (!isTopHidden) {
                    return true;
                }
                if (mScrollView.getScrollY() == 0 && isTopHidden && dy > 0 && viewPager.getCurrentItem() == 0) {
                    return true;
                }
                if (scrollView2.getScrollY() == 0 && isTopHidden && dy > 0 && viewPager.getCurrentItem() == 1) {
                    return true;
                }
                if (scrollView3.getScrollY() == 0 && isTopHidden && dy > 0 && viewPager.getCurrentItem() == 2) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private float start = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent ");
        mVelocityTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                viewPager.setVisibility(View.GONE);
                start = y;
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - start;
                //dy>0向下
                scrollBy(0, -(int) dy);
                //否则滑动后停止，动画还会继续
                start = y;
                break;
            case MotionEvent.ACTION_UP:
                viewPager.setVisibility(View.VISIBLE);
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, winHeight - centerHeight));
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
