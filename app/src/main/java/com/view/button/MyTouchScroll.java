package com.view.button;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import javax.security.auth.login.LoginException;

/**
 * Created by Administrator on 2015/11/24.
 */
public class MyTouchScroll extends ViewGroup {
    private static String TAG="MyTouchScroll";
    //当前屏幕
    private int curScreen =0;
    //Scroller 对象实例
    private Scroller mScroller=null;
    private Context mContext;

    public MyTouchScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    private void init() {
        mScroller=new Scroller(mContext);
        //初始化三个LinearLayout

        //初始化一个最小距离
        mTouchSlop= ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i(TAG, "computeScroll ");
        //true则表示动画还没有结束
        if(mScroller.computeScrollOffset()){
            Log.i(TAG, mScroller.getCurrX()+"   "+mScroller.getCurrY());
            //产生动画效果，根据当前值每次滚动一点
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            Log.i(TAG, "getleft " + getLeft() + "   getRight" + getRight());
            postInvalidate();
        }
        else {
            Log.i(TAG, "have done the scoller -----");

        }

    }
    //划屏状态
    //什么都没有做
    private static final int TOUCH_STATE_REST=0;
    //开始划屏
    private static final int TOUCH_STATE_SCROLLING=1;
    //默认的是什么都没做的状态
    private int mTouchState =TOUCH_STATE_REST;

    //处理触摸事件
    //最小的滑动速率
    public  static int SNAP_VELOCITY=600;
    //最小滑动距离，超过了，才认为开始滑动
    private int mTouchSlop=0;
    //记住上次触屏的位置
    private  float mLastionMotionX=0;
    //处理触摸的速率
    private VelocityTracker mVelocityTracker=null;
    // 这个感觉没什么作用 不管true还是false 都是会执行onTouchEvent的因为子view里面onTouchEvent返回false了


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent "+mTouchSlop);
        final  int action=ev.getAction();
        //表示开始滑动了，不需要走该Action_move方法（第一次时可能调用）
        //该方法主要用于用户快速松开手指，又快速按下这个行为，此时认为是出于划屏状态
        if((action==MotionEvent.ACTION_MOVE)&&(mTouchSlop!=TOUCH_STATE_REST)){
            return  true;
        }
        final float x= ev.getX();
        final float y=ev.getY();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                final int xDiff=(int)Math.abs(mLastionMotionX-x);
                //超过最小滑动距离，就可以开始滑动了
                if(xDiff>mTouchSlop){
                    mTouchState=TOUCH_STATE_SCROLLING;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent down");
                mLastionMotionX=x;
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
