package com.example.administrator.circleimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class PathCircleView extends ImageView {

    private Paint mPaint;
    private Path mPath;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private Bitmap mBitmap;

    public PathCircleView(Context context) {
        this(context,null);
    }

    public PathCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPath = new Path();

        mRadius = DensityUtils.dip2px(context, 5);

        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.demo_icon_android_logo);



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {

        mPath.lineTo(mWidth,0);
        mPath.lineTo(mWidth,mHeight-mRadius);
        mPath.addArc(mWidth-mRadius*2,mHeight-mRadius*2,mWidth,mHeight,0,90);
        mPath.lineTo(mRadius,mHeight);
        mPath.addArc(0,mHeight-mRadius*2,mRadius*2,mHeight,90,90);
        mPath.lineTo(0,0);
        canvas.drawPath(mPath,mPaint);


        /*super.onDraw(canvas);*/
    }
}
