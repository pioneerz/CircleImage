package com.example.administrator.circleimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class ClipCircleView extends ImageView {

    private Path mPath;
    private float[] radiusArray = { 0f, 0f, 0f, 0f, 10f, 10f, 10f, 10f};
    private Bitmap mBitmap;
    private Paint mPaint;

    public ClipCircleView(Context context) {
        this(context, null);
    }

    public ClipCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.demo_icon_android_logo);
    }

    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {

        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radiusArray, Path.Direction.CW);
        canvas.clipPath(mPath);

        super.onDraw(canvas);
    }
}
