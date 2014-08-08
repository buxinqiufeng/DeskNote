
package com.widget.desknote;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingStyleActivity extends Activity implements OnClickListener, OnTouchListener {
    private static final String TAG = "SettingStyleActivity";
    private static final List<Settings.Style> mTemplate = Settings.TEMPLATE_STYLE;
    private Map<View, Integer> mColorMap = new HashMap<View, Integer>();
    PointF mTouchPoint = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_style_activity);

        LinearLayout lo_color = (LinearLayout) findViewById(R.id.layout_color);
        for (int i = 0; i < mTemplate.size(); i++) {
            ImageView iv = new ImageView(this.getApplicationContext());
            mColorMap.put(iv, i);
            iv.setBackgroundResource(mTemplate.get(i).widgetBgResId);
            iv.setClickable(true);
            iv.setOnClickListener(this);
            //iv.setOnTouchListener(this);
            lo_color.addView(iv);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!(v instanceof ImageView)) {
            return false;
        }
        ImageView iv = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mTouchPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                ;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG, "move action, from point(" + mTouchPoint.x + ", " + mTouchPoint.y
                        + ") to (" + event.getX() + ", " + event.getY() + ")");
                Matrix mx = new Matrix();
                mx.set(iv.getImageMatrix());
                mx.postTranslate(event.getX() - mTouchPoint.x, event.getY() - mTouchPoint.y);
                iv.setImageMatrix(mx);

                int x = (int) (event.getX() - mTouchPoint.x);
                int y = (int) (event.getY() - mTouchPoint.y);
                iv.layout(x, y, x+iv.getWidth(), y+iv.getHeight());
                break;
        }

        return true;
    }
}
