package com.widget.desknote;

import android.graphics.Color;

import com.widget.desknote.Settings.Style;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    
    public static List<Style> TEMPLATE_STYLE = new ArrayList<Style>();
    static {
        TEMPLATE_STYLE.add(new Style(R.drawable.color_picker_blue,
                R.drawable.edit_blue, R.drawable.edit_title_blue, R.drawable.widget_4x_blue,
                Color.BLACK, Color.BLACK));
        TEMPLATE_STYLE.add(new Style(R.drawable.color_picker_green,
                R.drawable.edit_green, R.drawable.edit_title_green, R.drawable.widget_4x_green,
                Color.BLACK, Color.GRAY));
        TEMPLATE_STYLE.add(new Style(R.drawable.color_picker_red,
                R.drawable.edit_red, R.drawable.edit_title_red, R.drawable.widget_4x_red,
                Color.BLACK, Color.BLACK));
        TEMPLATE_STYLE.add(new Style(R.drawable.color_picker_white,
                R.drawable.edit_white, R.drawable.edit_title_white, R.drawable.widget_4x_white,
                Color.BLACK, Color.GRAY));
        TEMPLATE_STYLE.add(new Style(R.drawable.color_picker_yellow,
                R.drawable.edit_yellow, R.drawable.edit_title_yellow, R.drawable.widget_4x_yellow,
                Color.BLACK, Color.BLACK));
    }
    public static class Style {
        int shortcutBgResId;
        int mainviewBgResId;
        int mainviewTitleResId;
        int widgetBgResId;
        int mainviewTextColor;
        int widgetBgTextColor;
        public Style(int scResId, int mvResId, int mvTResId, int wResId, int mvTC, int wTC) {
            shortcutBgResId = scResId;
            mainviewBgResId = mvResId;
            mainviewTitleResId = mvTResId;
            widgetBgResId = wResId;
            mainviewTextColor = mvTC;
            widgetBgTextColor = wTC;
        }
    }
}
