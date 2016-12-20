package com.chen.whereyouare.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chen.whereyouare.R;

/**
 * Created by ChenHui on 2016/11/7.
 */

public class CircleProgressView extends View {
    public CircleProgressView(Context context) {
        super(context);
    }

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环的画笔2
    private Paint mRingPaint2;
    // 画圆环的底色画笔
    private Paint mRingBgPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环颜色
    private int mRingColor2;
    // 圆环底色颜色
    private int mRingBgColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float mProgress;
    // 当前进度
    private float mSecondProgress;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        mRadius = context.getResources().getDimensionPixelSize(R.dimen.w10) * 3;
        mStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.w10) / 8 * 3;
        mCircleColor = Color.BLACK;
        mRingColor = 0xFFC11616;
        mRingColor2 = 0x55745F5F;
        mRingBgColor = Color.WHITE;
        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);
        mRingPaint2 = new Paint();
        mRingPaint2.setAntiAlias(true);
        mRingPaint2.setColor(mRingColor2);
        mRingPaint2.setStyle(Paint.Style.STROKE);
        mRingPaint2.setStrokeWidth(mStrokeWidth);
        mRingBgPaint = new Paint();
        mRingBgPaint.setAntiAlias(true);
        mRingBgPaint.setColor(mRingBgColor);
        mRingBgPaint.setStyle(Paint.Style.STROKE);
        mRingBgPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setARGB(255, 255, 255, 255);
        mTextPaint.setTextSize(mRadius / 2);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

//        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);//内圆

        RectF oval = new RectF();
        oval.left = (mXCenter - mRingRadius);
        oval.top = (mYCenter - mRingRadius);
        oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingBgPaint);//一整圈的圆
        canvas.drawArc(oval, -90, ((float) mSecondProgress / mTotalProgress) * 360, false, mRingPaint2); //第二进度
        canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //第一进度
//        文本
//            String txt = mProgress + "%";
//            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
//            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
    }

    public void setProgress(float progress, float secondProgress) {
        mProgress = progress;
        mSecondProgress = secondProgress;
//      invalidate();
        postInvalidate();
    }

}
