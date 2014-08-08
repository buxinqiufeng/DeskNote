
package com.widget.desknote;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;

public class SettingColorPopup extends PopupWindow {
    private View mParentView;
    private ViewGroup mRoot;
    private int[] mLoc = new int[2];

    public SettingColorPopup(View parent) {
        super(parent);
        this.mParentView = parent;
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup mRoot = (ViewGroup) inflater.inflate(R.layout.setting_color, null);
        this.setContentView(mRoot);
        //layoutCompontent(context, parent);

        int screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth();
        this.setWidth(screenWidth-200);
        this.setHeight(mRoot.getMeasuredHeight());
        this.setBackgroundDrawable(new BitmapDrawable());
        
        this.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    SettingColorPopup.this.dismiss();
                    return true;
                }
                return false;
            }

        });
    }

    public void show() {
        if (!this.isShowing()) {
            //this.showAtLocation(mParentView, Gravity.RIGHT, mLoc[0], mLoc[1]);
            //Log.w("SettingColor", "Location:" + mLoc[0] + ", " + mLoc[1]);
            this.showAsDropDown(mParentView);
        }
    }

    OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            SettingColorPopup.this.dismiss();
        }
    };

    private void layoutCompontent(Context context, View parent) {
        int[] parentLoc = new int[2];
        parent.getLocationInWindow(parentLoc);
        int rootWidth = mRoot.getMeasuredWidth();
        int rootHeight = mRoot.getMeasuredHeight();
        int screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth();
        mLoc[0] = 0;// screenWidth-rootWidth;
        mLoc[1] = parentLoc[1] + 10;
        this.setWidth(screenWidth);
        this.setHeight(100);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        // this.setBackgroundDrawable(context.getResources()
        // .getDrawable(R.drawable.bottom_bar_bg_dark));
        this.setBackgroundDrawable(new BitmapDrawable());
    }
}
