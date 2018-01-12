package com.example.administrator.circleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private float radius;
    private ImageView mBitmapShaperAll;
    private ImageView mBitmapShaperBottom;
    private ImageView mBitmapShaderCircle;
    private ImageView mXFermodeAll;
    private ImageView mXFermodeBottom;
    private ImageView mXFermodeCircle;
    private PathCircleView mPathCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mBitmapShaperAll = (ImageView) findViewById(R.id.image_bitmapshaper_all);
        mBitmapShaperBottom = (ImageView) findViewById(R.id.image_bitmapshaper_bottom);
        mXFermodeAll = (ImageView) findViewById(R.id.image_xfermode_all);
        mXFermodeCircle = (ImageView) findViewById(R.id.image_xfermode_circle);
        mBitmapShaderCircle = (ImageView) findViewById(R.id.image_bitmapshader_circle);
        mPathCircleView = (PathCircleView) findViewById(R.id.image_path_bottom);
        mXFermodeBottom = (ImageView) findViewById(R.id.image_xfermode_bottom);
    }

    private void initData() {
        radius = getResources().getDimension(R.dimen.round_bitmap_radius);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.demo_icon_android_logo);
        int width = (int) getResources().getDimension(R.dimen.round_bitmap_width);
        int height = (int) getResources().getDimension(R.dimen.round_bitmap_height);

        mBitmapShaperAll.setImageBitmap(setBitmapShaderAll(bitmap,width,height));

        mBitmapShaperBottom.setImageBitmap(setBitmapShaderBottom(bitmap,width,height));

        mXFermodeAll.setImageBitmap(setXFermodeAll(bitmap,width,height));

        mXFermodeCircle.setImageBitmap(setXFermodeCircle(bitmap,width,height));

        mBitmapShaderCircle.setImageBitmap(setBitmapShaderCircle(bitmap,width,height));

        mXFermodeBottom.setImageBitmap(setXFermodeBottom(bitmap,width,height));
    }

    private Bitmap setXFermodeBottom(Bitmap bitmap, int width, int height) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        Matrix matrix = getMatrix(bitmap,width,height);

        Bitmap newBt = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(target);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        float[] radius = {0f,0f,0f,0f,10f,10f,10f,10f};
        Path path = new Path();
        path.addRoundRect(new RectF(0,0,width,height),radius,Path.Direction.CW);
        canvas.drawPath(path,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0,0,width,height);
        canvas.drawBitmap(newBt,rect,rect,paint);

        return target;
    }

    private Bitmap setBitmapShaderCircle(Bitmap bitmap, int width, int height) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        Matrix matrix = getMatrix(bitmap,width,height);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(target);

        canvas.drawCircle(width/2, height/2,Math.min(width,height)/2,paint);

        return target;
    }

    private Bitmap setXFermodeCircle(Bitmap bitmap, int width, int height) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        Matrix matrix = getMatrix(bitmap,width,height);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(target);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawCircle(width/2, height/2,Math.min(width,height)/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0,0,width,height);
        canvas.drawBitmap(newBt,rect,rect,paint);

        return target;
    }

    private Bitmap setXFermodeAll(Bitmap bitmap, int width, int height) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 等比例缩放拉伸
        Matrix matrix = getMatrix(bitmap,width,height);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(target);
        canvas.drawARGB(0,0,0,0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rectF = new RectF(0f, 0f, (float) width, (float) height);

        // 在画布上绘制圆角图
        canvas.drawRoundRect(rectF, radius, radius, paint);

        // 设置叠加模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect rect = new Rect(0,0,width,height);
        canvas.drawBitmap(newBt,rect,rect,paint);

        return target;
    }

    private Bitmap setBitmapShaderBottom(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap can not be null");
        }

        Matrix matrix = getMatrix(bitmap,width,height);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        canvas.drawRoundRect(new RectF(0,height-radius * 2,width,height),radius,radius,paint);
        canvas.drawRect(new RectF(0,0,width,height-radius),paint);

        return target;
    }

    private Bitmap setBitmapShaderAll(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap can not be null");
        }

        Matrix matrix = getMatrix(bitmap,width,height);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);

        Bitmap target = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);


        canvas.drawRoundRect(new RectF(0,0,width,height),radius,radius,paint);

        return target;

    }

    private Matrix getMatrix(Bitmap bitmap, int width, int height) {
        float widthScale = width * 1.0f / bitmap.getWidth();
        float heightScale = height * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale,heightScale);

        return matrix;
    }


}
