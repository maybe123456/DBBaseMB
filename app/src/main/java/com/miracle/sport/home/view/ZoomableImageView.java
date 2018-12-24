package com.miracle.sport.home.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by 廖华凯 on 2017/7/31.
 */

public class ZoomableImageView extends android.support.v7.widget.AppCompatImageView {
    private Matrix mMatrix;
    private GestureDetectorCompat mGestureDetector;
    private ViewGroup mParent;
    private float scaleFactor=1.f;
    private MotionEvent previousMotion;
//    private float mScreenWidth;
//    private float mScreenHeight;
    private WindowManager mWindowManager;
    private boolean isProcessing=false;
    private float mDensity;
    private float mScaledWidth;
    private float mScaledHeight;
    private float mBitmapWidth;
    private float mBitmapHeight;
    private Rect mPostZoomBounds;
    private Rect mPreScrollBounds;
    private float mWidth;
    private float mHeight;
    private Bitmap mBitmap;


    public ZoomableImageView(Context context) {
        this(context,null);
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix=new Matrix();

        //this step is vital
        setScaleType(ScaleType.MATRIX);

        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
//        mScreenWidth = pxToDp(metrics.widthPixels);
//        mScreenHeight = pxToDp(metrics.heightPixels);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewParent parent = getParent();
                if(parent!=null&&(parent instanceof ViewPager)){
                    mParent= (ViewGroup) parent;
                    mParent.requestDisallowInterceptTouchEvent(true);
                    showLog(mParent.toString());
                }else {
                    showLog("No Parent is found! ");
                }

//                centerFitImage(mBitmap);
            }
        });

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                previousMotion=motionEvent;
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                mMatrix.preTranslate(-v,-v1);
                invalidate();
//                showLog("get x : "+getX()+" get y : "+getY());
//                showLog("get trans x : "+getTranslationX()+" get trans y : "+getTranslationY());
//                showLog("get left : "+getLeft()+" get top : "+getTop()+" get right : "+getRight()+" get bottom : "+getBottom());
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return true;
            }
        });

        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                previousMotion =motionEvent;
                zoom(motionEvent.getRawX(),motionEvent.getRawY(),ZoomDirection.IN);
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return true;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mParent.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private float pxToDp(float px){
        return px/mDensity;
    }

    private float dpToPx(float dp){
        return dp*mDensity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth= pxToDp(MeasureSpec.getSize(widthMeasureSpec));
        mHeight= pxToDp(MeasureSpec.getSize(heightMeasureSpec));
//        showLog("Width = "+mWidth+" Height = "+mHeight);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        BitmapDrawable bd= (BitmapDrawable) drawable;
        mBitmap = bd.getBitmap();
//        if(mBitmap !=null){
//            centerFitImage(mBitmap);
//        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                centerFitImage(mBitmap);
            }
        });
    }


    private void centerFitImage(Bitmap bitmap) {
        mBitmapWidth = pxToDp(bitmap.getWidth());
        mBitmapHeight = pxToDp(bitmap.getHeight());
        showLog("bitmap bitmapWidth "+ mBitmapWidth +" bitmap bitmapHeight "+ mBitmapHeight);
        showLog("view width = "+mWidth+" view height = "+mHeight);
        float iw = mWidth / mBitmapWidth;
        float ih = mHeight / mBitmapHeight;
        showLog("iw = "+iw+" ih = "+ih);
        float scaleFactor=iw<ih?iw:ih;
        showLog("centerFitImage scaleFactor = "+scaleFactor);
        mMatrix.postScale(scaleFactor,scaleFactor);
        invalidate();
        mScaledWidth = mBitmapWidth * scaleFactor;
        mScaledHeight = mBitmapHeight * scaleFactor;
        showLog("scaled bitmapWidth = "+ mScaledWidth +" scale bitmapHeight = "+ mScaledHeight);
        float tranX=0;
        float tranY=0;
        showLog("mHeight = "+mHeight+" mWidth = "+mWidth);
        if(mBitmapWidth > mBitmapHeight){
            tranY=dpToPx((mHeight- mScaledHeight)/2);
        }else {
            tranX=dpToPx((mWidth- mScaledWidth)/2);
        }
        showLog("tranX = "+tranX+" tranY = "+tranY);
        mMatrix.postTranslate(tranX,tranY);
        invalidate();
    }


    public void zoom(float px, float py, ZoomDirection direction){
        if(isProcessing){
            return;
        }

        float newFactor=0;
        switch (direction){
            case IN:
                newFactor=scaleFactor+0.1f;
                break;
            case OUT:
                newFactor=scaleFactor-0.1f;
                break;

        }
        newFactor=Math.max(0,Math.min(newFactor,10));
        animateZoom(newFactor,px,py);
    }

    private void animateZoom(final float newFactor, final float px, final float py) {
        ValueAnimator animator=ValueAnimator.ofFloat(scaleFactor,newFactor);
        animator.setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor= (float) animation.getAnimatedValue();
                mPreScrollBounds=null;
                mMatrix.postScale(factor,factor,px,py);
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isProcessing=true;

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isProcessing=false;
                showLog("new zoom bound :"+mPostZoomBounds.toString());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isProcessing=false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isProcessing=true;

            }
        });

        animator.start();
    }

    public void zoom(ZoomDirection direction){
        if(previousMotion !=null){
            zoom(previousMotion.getX(), previousMotion.getY(),direction);
        }else {
            zoom(0,0,direction);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setMatrix(mMatrix);
        super.onDraw(canvas);
        if(mPostZoomBounds ==null){
            mPostZoomBounds =canvas.getClipBounds();
        }else {
            mPreScrollBounds = canvas.getClipBounds();
            showLog("rect : "+canvas.getClipBounds().toString());
        }

//        Bitmap drawingCache = getDrawingCache();
//        if(drawingCache!=null){
//            showLog("drawing cache width : "+drawingCache.getWidth()+" drawing cache height : "+drawingCache.getHeight());
//        }
//        getDrawingRect(mDrawingRect);
//        showLog("drawing rect : "+mDrawingRect.toString());
    }

    public enum ZoomDirection {
        IN,OUT
    }
}