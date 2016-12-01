package com.example.lizhe.mytest;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

public class KInduceButton
        extends Button
{
    private ValueAnimator animator = new ValueAnimator();
    private Paint mPaint = new Paint();
    private float mProcess;
    private float mRadius;
    private RectF mRectF = new RectF();

    public KInduceButton(Context paramContext)
    {
        super(paramContext);
        init(paramContext, null);
    }

    public KInduceButton(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext, paramAttributeSet);
    }

    public KInduceButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext, paramAttributeSet);
    }

    private void init(Context paramContext, AttributeSet paramAttributeSet)
    {
        if (paramAttributeSet == null) {}
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        if (0.0F == this.mProcess) {
            return;
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.GRAY);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAlpha((int)(150.0F * (1.0F - this.mProcess)));
        this.mRectF.set(this.mRadius * this.mProcess, 0.0F, this.mRadius * (2.0F - this.mProcess), getHeight());
        paramCanvas.drawRect(this.mRectF, this.mPaint);
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        this.mRadius = ((paramInt3 - paramInt1) / 2);
        start();
    }

    public void start()
    {
        this.animator = ValueAnimator.ofFloat(new float[] { 1.0F, 0.0F });
        this.animator.setDuration(2000L);
        this.animator.setStartDelay(2000L);
        this.animator.setRepeatCount(-1);
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProcess = ((Float)animation.getAnimatedValue()).floatValue();
                if ((getWidth() == 0) || (getHeight() == 0)) {
                    return;
                }
                postInvalidate();
            }
        });
        this.animator.start();
    }
}