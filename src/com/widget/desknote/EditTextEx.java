
package com.widget.desknote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextEx extends EditText {
    private static final int padding = 15;
    private Paint mPaint;
    private Path mLinePath = new Path();

    public EditTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        PathEffect effects = new DashPathEffect(new float[] {
                5, 5, 5, 5
        }, 1);
        mPaint.setPathEffect(effects);

        this.setPadding(padding, padding, 0, 0);
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

        final int width = getWidth();
        for (int i = 0; i < lineCount; i++) {
            int lineY = lineHeight * (i + 1) + padding+1;
            mLinePath.moveTo(0, lineY);
            mLinePath.lineTo(width, lineY);
            canvas.drawPath(mLinePath, mPaint);
        }
    }

}
