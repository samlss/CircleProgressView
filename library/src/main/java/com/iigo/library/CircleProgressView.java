package com.iigo.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description A circle progress view.
 */
public class CircleProgressView extends View {
    private final static int DEFAULT_SIZE = 150;
    private final static int DEFAULT_PROGRESS_COLOR = Color.WHITE;
    private final static int DEFAULT_CIRCLE_COLOR = Color.parseColor("#2968F7");

    private int mCircleColor   = DEFAULT_CIRCLE_COLOR;
    private int mProgressColor = DEFAULT_PROGRESS_COLOR;
    private int mProgress            = 0;
    private int mProgressTextColor   = DEFAULT_PROGRESS_COLOR;
    private float mProgressTextSize  = 25;

    private float mCircleRadius;
    private float mStokeWidth;

    private float mCenterX;
    private float mCenterY;

    private Rect textBoundRect;
    private Paint mCirclePaint;
    private Paint mTextPaint;

    private Path mProgressPath;
    private Paint mProgressPaint;
    private Paint mCornerPaint;
    private Path mProgressDstPath;
    private PathMeasure mProgressPathMeasure;

    private float[] mCalculatePos = new float[2];

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttrs(attrs);
        init();
    }

    private void parseAttrs(AttributeSet attrs){
        if (attrs == null){
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mProgressColor    = typedArray.getColor(R.styleable.CircleProgressView_progressColor, DEFAULT_PROGRESS_COLOR);
        mProgress = typedArray.getInt(R.styleable.CircleProgressView_progress, 0);
        mProgressTextColor   = typedArray.getColor(R.styleable.CircleProgressView_progressTextColor, DEFAULT_PROGRESS_COLOR);
        mProgressTextSize    = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_progressTextSize, 25);
        mCircleColor         = typedArray.getColor(R.styleable.CircleProgressView_circleColor, DEFAULT_CIRCLE_COLOR);
        typedArray.recycle();

        checkProgress();
    }

    private void init(){
        mProgressPath    = new Path();
        mProgressDstPath = new Path();
        mProgressPathMeasure = new PathMeasure();

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleColor);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(mProgressColor);

        mCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCornerPaint.setStyle(Paint.Style.FILL);
        mCornerPaint.setColor(mProgressColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
        textBoundRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth = DEFAULT_SIZE;
        int mHeight = DEFAULT_SIZE;

        boolean isWidthWrap  = getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean isHeightWrap = getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT;

        if (!isWidthWrap && !isHeightWrap){
            return;
        }

        setMeasuredDimension(isWidthWrap ? mWidth : widthSize, isHeightWrap ? mHeight : heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mProgressPath.reset();

        mCenterX = w / 2;
        mCenterY = h / 2;

        int minSize = Math.min(w, h);
        Log.e("TAG", "w: "+w);
        Log.e("TAG", "h: "+h);
        Log.e("TAG", "min size: "+minSize);

        mCircleRadius = minSize / 2;

        mStokeWidth = minSize / 20f;

        mProgressPaint.setStrokeWidth(mStokeWidth);

        float radius = (minSize - mStokeWidth * 2.5f) / 2;
        mProgressPath.addCircle(mCenterX, mCenterY, radius, Path.Direction.CW);
        mProgressPathMeasure.setPath(mProgressPath, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mCirclePaint);

        mProgressDstPath.reset();
        mProgressPaint.setPathEffect(new CornerPathEffect(mStokeWidth));

        float distance = mProgress * 1.0f / 100 * mProgressPathMeasure.getLength();

        mProgressPathMeasure.getSegment(0, distance, mProgressDstPath, true);
        canvas.drawPath(mProgressDstPath, mProgressPaint);

        mProgressPathMeasure.getPosTan(0, mCalculatePos, null);
        canvas.drawCircle(mCalculatePos[0], mCalculatePos[1], mStokeWidth / 2, mCornerPaint);

        mProgressPathMeasure.getPosTan(distance, mCalculatePos, null);
        canvas.drawCircle(mCalculatePos[0], mCalculatePos[1], mStokeWidth / 2, mCornerPaint);

        drawProgress(canvas);
    }

    /**
     * Now draw the progress.
     *
     * @param canvas The {@link Canvas} of this View.
     * */
    private void drawProgress(Canvas canvas){
        String text = mProgress + "%";

        mTextPaint.getTextBounds(text, 0, text.length(), textBoundRect);
        canvas.drawText(text, mCenterX - textBoundRect.width() / 2, mCenterY + textBoundRect.height() / 2, mTextPaint);
    }

    /**
     * Check if the progress is between 0-100.
     * */
    private void checkProgress(){
        if (mProgress < 0){
            mProgress = 0;
        }else if (mProgress > 100){
            mProgress = 100;
        }
    }

    /**
     * Set the progress,the range is 0-100.
     * */
    public void setProgress(int progress) {
        this.mProgress = progress;
        checkProgress();
        invalidate();
    }

    /**
     * Get current progress.
     * */
    public int getProgress() {
        return mProgress;
    }

    /**
     * Set the moving progress color
     * */
    public void setProgressColor(int progressColor) {
        this.mProgressColor = progressColor;
        mProgressPaint.setColor(mProgressColor);
        mCornerPaint.setColor(mProgressColor);
        invalidate();
    }

    /**
     * Get the moving progress color
     * */
    public int getProgressColor() {
        return mProgressColor;
    }

    /**
     * Set the color of progress text.
     * */
    public void setProgressTextColor(int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        mTextPaint.setColor(mProgressTextColor);
        invalidate();
    }

    /**
     * Get the color of progress text.
     * */
    public int getProgressTextColor() {
        return mProgressTextColor;
    }

    /**
     * Set the progress 'TextSize' in pixels.
     * */
    public void setProgressTextSize(float progressTextSizeInPixels) {
        this.mProgressTextSize = progressTextSizeInPixels;
        mTextPaint.setTextSize(progressTextSizeInPixels);
        invalidate();
    }


    /**
     * Get the progress 'TextSize' in pixels.
     * */
    public float getProgressTextSize() {
        return mProgressTextSize;
    }
}
