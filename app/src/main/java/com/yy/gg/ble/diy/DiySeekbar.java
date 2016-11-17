package com.yy.gg.ble.diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ZHLian on 16/11/14.
 */

public class DiySeekbar extends View {
    private Paint mPaint;
    private Rect mSeekBar;

    private ImageView mPointer;
    public DiySeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSeekBar = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Bitmap bitmap = Bitmap.createBitmap()
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,getWidth()/5,getHeight(),mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(getWidth()/5,0,getWidth()/5*4,getHeight(),mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawRect(getWidth()/5*4,0,getWidth(),getHeight(),mPaint);
    }
}
