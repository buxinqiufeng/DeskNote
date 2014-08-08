
package com.widget.desknote;

import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
    public static final String ACTION_SETTING = "com.widget.desknote.setting";
    public static final String ACTION_UPDATE = "com.widget.desknote.update";
    private static final String mAppDir = "com.widgetnote";
    private static final String mNotePath = "note.txt";
    private static final File mApp = new File(Environment.getExternalStorageDirectory(), mAppDir);
    public static final String PREFERENCE_FILE = "setting";
    public static final String PREFERENCE_STYLE = "color";
    private static final List<Settings.Style> mTemplate = Settings.TEMPLATE_STYLE;

    private View mTitle;
    private ScrollView mScroll;
    private EditText mNote;
    private ImageButton mSettingColor;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentSetting = null;
    // private SettingReceiver mSettingReceiver;
    private SharedPreferences mPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mPreference = getSharedPreferences(PREFERENCE_FILE, 0);

        if (!mApp.exists()) {
            mApp.mkdir();
        }

        mFragmentManager = getSupportFragmentManager();
        mContext = this.getApplicationContext();

        mTitle = (View) findViewById(R.id.title);
        mScroll = (ScrollView) findViewById(R.id.scrollview);
        mSettingColor = (ImageButton) findViewById(R.id.btn_color);
        mSettingColor.setOnClickListener(this);
        mNote = (EditText) findViewById(R.id.note);
        String content = getNotesFromFile();
        mNote.setText(content);
        mNote.setSelection(content.length());
        mNote.setBackground(new BitmapDrawable());
        mNote.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideSettingFragment();
            }

        });
        int styleId = mPreference.getInt(PREFERENCE_STYLE, -1);
        if (styleId < 0 || styleId > mTemplate.size()) {
            styleId = 0;
        }
        applyStyle(styleId);

        /*
         * mSettingReceiver = new SettingReceiver(); IntentFilter intentFilter =
         * new IntentFilter(); intentFilter.addAction(ACTION_SETTING);
         * this.registerReceiver(mSettingReceiver, intentFilter);
         */
    }

    @Override
    protected void onStart() {
        super.onStart();

        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        String content = mNote.getText().toString();
        saveNoteToFile(content);
        sendBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        String content = mNote.getText().toString();
        saveNoteToFile(content);
        sendBroadcast();
        // this.unregisterReceiver(mSettingReceiver);
    }

    public static String getNotesFromFile() {
        File mNote = new File(mApp, mNotePath);
        String content = "";
        try {
            InputStream instream = new FileInputStream(mNote);
            if (null != instream) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                boolean isFirstLine = true;
                while ((line = buffreader.readLine()) != null) {
                    if (!isFirstLine) {
                        content += "\n";
                    }
                    isFirstLine = false;
                    content += line;
                }
            }
            instream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }

    private void saveNoteToFile(String content) {
        File mNote = new File(mApp, mNotePath);

        try {
            OutputStream outStream = new FileOutputStream(mNote, false);
            outStream.write(content.getBytes());
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_color:
                if (mCurrentSetting instanceof SettingColorFragment) {
                    hideSettingFragment();
                } else {
                    mCurrentSetting = new SettingColorFragment();
                }
                break;
            default:

        }
        if (null != mCurrentSetting) {
            showSettingFragment(mCurrentSetting);
            // Intent intent = new Intent(mContext, SettingStyleActivity.class);
            // this.startActivity(intent);
        }

    }

    private void showSettingFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //transaction.setCustomAnimations(R.animator.side_left_in, R.animator.side_right_out);
        transaction.replace(R.id.setting, fragment);
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    private void hideSettingFragment() {
        if (null == mCurrentSetting) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(mCurrentSetting);
        transaction.commit();
        mCurrentSetting = null;
    }

    public void onStyleChanged(int styleId) {
        applyStyle(styleId);
        hideSettingFragment();

        Editor editor = mPreference.edit();
        editor.putInt(PREFERENCE_STYLE, styleId);
        // editor.putString("test", "test");
        editor.commit();
    }

    private void applyStyle(int styleId) {
        Settings.Style currentStyle = mTemplate.get(styleId);
        mScroll.setBackgroundResource(currentStyle.mainviewBgResId);
        mNote.setTextColor(currentStyle.mainviewTextColor);
        mTitle.setBackgroundResource(currentStyle.mainviewTitleResId);
    }

    /*
     * public static class SettingReceiver extends BroadcastReceiver {
     * @Override public void onReceive(Context context, Intent intent) { // TODO
     * Auto-generated method stub Log.w("test", "Receiver called"); } }
     */
}
