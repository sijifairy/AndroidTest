package com.example.lizhe.mytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lz on 4/18/17.
 */

public class CustomDrawView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    public CustomDrawView(Context context) {
        this(context, null);
    }

    public CustomDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaint.setColor(0x20000000);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.air_hockey_surface);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        canvas.drawRect(0, 0, 200, 200, mPaint);
    }
}
