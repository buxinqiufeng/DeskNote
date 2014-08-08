
package com.widget.desknote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.List;

public class WidgetMain extends AppWidgetProvider {
    RemoteViews mRemoteViews;
    private static final List<Settings.Style> mTemplate = Settings.TEMPLATE_STYLE;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {

        updateNote(context);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.ACTION_UPDATE)) {
            updateNote(context);
        }
        super.onReceive(context, intent);
    }

    private void updateNote(Context context) {
        if (null == mRemoteViews) {
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
        }

        SharedPreferences preference = context.getSharedPreferences(MainActivity.PREFERENCE_FILE, 0);
        
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mRemoteViews.setTextViewText(R.id.noteview, MainActivity.getNotesFromFile());
        mRemoteViews.setOnClickPendingIntent(R.id.noteview, pendingIntent);
        int styleId = preference.getInt(MainActivity.PREFERENCE_STYLE, -1);
        if (styleId < 0 || styleId > mTemplate.size()) {
            styleId = 0;
        }
        mRemoteViews.setInt(R.id.noteview, "setBackgroundResource", mTemplate.get(styleId).widgetBgResId);
        mRemoteViews.setTextColor(R.id.noteview, mTemplate.get(styleId).widgetBgTextColor);
        AppWidgetManager awg = AppWidgetManager.getInstance(context);
        awg.updateAppWidget(new ComponentName(context, WidgetMain.class), mRemoteViews);
    }
}
