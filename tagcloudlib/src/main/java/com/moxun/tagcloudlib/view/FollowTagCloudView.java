package com.moxun.tagcloudlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * 重写触摸事件,解决滑动不跟手的问题
 */
public class FollowTagCloudView extends TagCloudView {
    private float mBaseSpeed = .4f;//最低速;
    public FollowTagCloudView(Context context) {
        super(context);
    }

    public FollowTagCloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowTagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        mode = MODE_DECELERATE;
    }
    public void setBaseSpeed(float speed){
        this.mBaseSpeed = speed;
    }
    /**
     * 禁用模式设置,
     * @param mode 该TagCloud 固定模式为 MODE_DECELERATE
     */
    @Deprecated
    @Override
    public void setAutoScrollMode(int mode) {
        //super.setAutoScrollMode(mode);
    }

    private long time = 0;//记录滚动时间,用于 ACTION_UP后计算惯性速度

    @Override
    protected void handleTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                isOnTouch = true;
                time = System.currentTimeMillis();
            case MotionEvent.ACTION_MOVE:
                float dx = e.getX() - downX;
                float dy = e.getY() - downY;
                if (isValidMove(dx, dy)||System.currentTimeMillis()-time>40) {//添加時間判斷防止低速移動時的動畫不連貫
                    mAngleX = (float) (Math.asin(dy / 2 / radius) / Math.PI * 180);
                    mAngleY = (float) (Math.asin(-dx / 2 / radius) / Math.PI * 180);
                    processTouch();
                    downX = e.getX();
                    downY = e.getY();
                    time = System.currentTimeMillis();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int timeLen = (int) (System.currentTimeMillis() - time);
                mAngleX = mAngleX * 50 / timeLen;
                mAngleY = mAngleY * 50 / timeLen;
                if (mAngleX > 10) mAngleX = 10;
                if (mAngleX < -10) mAngleX = -10;
                if (mAngleY > 10) mAngleY = 10;
                if (mAngleY < -10) mAngleY = -10;
                isOnTouch = false;
                break;
        }
    }

    @Override
    public void run() {
        if (!isOnTouch && mode != MODE_DISABLE) {
            if (mode == MODE_DECELERATE) {
                if (mAngleX > mBaseSpeed) {
                    mAngleX -= .02 * mAngleX;
                }
                if (mAngleY > mBaseSpeed) {
                    mAngleY -= 0.02f * mAngleY;
                }
                if (mAngleX < -mBaseSpeed) {
                    mAngleX -= 0.02f * mAngleX;
                }
                if (mAngleY < -mBaseSpeed) {
                    mAngleY -= 0.02f * mAngleY;
                }
            }
            processTouch();
        }

        handler.postDelayed(this, 40);
    }

}
