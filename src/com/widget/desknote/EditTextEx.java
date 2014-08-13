
package com.widget.desknote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextEx extends EditText {
    private static final int padding = 15;
    private Paint mPaint;
    private float mViewWidth = 0;
    private float mLineLeft = 0;
    private float mLineRight = 0;

    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        PathEffect effects = new DashPathEffect(new float[] {
                4, 5
        }, 1);
        mPaint.setStrokeWidth((float) 0.5);
        mPaint.setPathEffect(effects);
        this.setLineSpacing(8, 1f);

        this.setPadding(padding, padding, 0, 0);
        mViewWidth = this.getWidth();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 画底线
        // canvas.drawLine(0,this.getHeight()-1, this.getWidth()-1,
        // this.getHeight()-1, mPaint);
        int lineCount = this.getLineCount();
        int lineHeight = this.getLineHeight();

        for (int i = 0; i < lineCount; i++) {
            float lineY = lineHeight * (i + 1) + padding;
            // mLinePath.moveTo(0, lineY);
            // mLinePath.lineTo(width, lineY);
            // canvas.drawPath(mLinePath, mPaint);
            canvas.drawLine(mLineLeft, lineY, mLineRight, lineY, mPaint);
        }
    }
    
    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mLineLeft = l + padding;
        mLineRight = r-padding;
    }
    
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        mLineRight = mLineRight + w - oldw;
    }

}
