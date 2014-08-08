
package com.widget.desknote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingColorFragment extends Fragment implements OnClickListener {

    private static final List<Settings.Style> mTemplate = Settings.TEMPLATE_STYLE;
    private ImageView mCurrentColor = null;
    private Map<View, Integer> mColorMap = new HashMap<View, Integer>();

    public SettingColorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_color, container, false);

        int imageW = 73;
        int imageH = 66;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageW, imageH);
        int imageNum = mTemplate.size();
        int adjustGap = (container.getWidth() - imageNum * imageW) / ((imageNum + 1) * 2);
        if (adjustGap > 0) {
            lp.setMargins(adjustGap, 10, adjustGap, 10);
        } else {
            lp.setMargins(10, 10, 10, 10);
        }

        SharedPreferences preference = container.getContext().getSharedPreferences(
                MainActivity.PREFERENCE_FILE, 0);
        int styleId = preference.getInt(MainActivity.PREFERENCE_STYLE, -1);
        if (styleId > imageNum || styleId < 0) {
            styleId = 0;
        }
        LinearLayout lo_color = (LinearLayout) view.findViewById(R.id.layout_color);
        for (int i = 0; i < mTemplate.size(); i++) {
            ImageView iv = new ImageView(lo_color.getContext());
            mColorMap.put(iv, i);
            iv.setBackgroundResource(mTemplate.get(i).shortcutBgResId);
            iv.setClickable(true);
            iv.setOnClickListener(this);
            iv.setScaleType(ScaleType.CENTER_CROP);
            iv.setLayoutParams(lp);
            lo_color.addView(iv);
            if(i == styleId) {
                mCurrentColor = iv;
                iv.setScaleType(ScaleType.CENTER_INSIDE);
                iv.setImageResource(R.drawable.color_picker_checkbox);
            }
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        // remove select precious
        if (mCurrentColor instanceof ImageView) {
            mCurrentColor.setImageDrawable(new BitmapDrawable());
        }

        ImageView iv = (ImageView) v;
        mCurrentColor = iv;
        setSelectIconPosition(iv);
    }

    private void setSelectIconPosition(ImageView imageView) {
        // mSelectIcon.set
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.color_picker_checkbox);

        // Intent intent = new Intent();
        // intent.setAction(MainActivity.ACTION_SETTING);
        // this.getActivity().sendBroadcast(intent);
        ((MainActivity) this.getActivity()).onStyleChanged(mColorMap.get(mCurrentColor));
    }

}
