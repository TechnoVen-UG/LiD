package com.dot.lid.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static com.dot.lid.utils.Constant.LANGUAGE_PREFERENCE_KEY;
import static com.dot.lid.utils.Constant.LANGUAGE__KEY;
import static com.dot.lid.utils.Constant.OPTION_PREFERENCE_KEY;
import static com.dot.lid.utils.Constant.SHOW_ANSWER_STATE;
import static com.dot.lid.utils.Constant.SOUND_STATE;

public class MyApplication extends Application {

    private static final String TAG = "sayed";
    public static String appLink;
    private static MyApplication instance;
    private SharedPreferences soundPreference;
    private SharedPreferences languagePreference;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {


            }
        });
    }

    private void init() {
        soundPreference = this.getApplicationContext().getSharedPreferences(OPTION_PREFERENCE_KEY, MODE_PRIVATE);
        languagePreference = this.getApplicationContext().getSharedPreferences(LANGUAGE_PREFERENCE_KEY, MODE_PRIVATE);
    }

    public void setSoundState(boolean state) {
        SharedPreferences.Editor editor = soundPreference.edit();
        editor.putBoolean(SOUND_STATE, state);
        editor.commit();
    }

    public boolean getSoundSate() {
        return soundPreference.getBoolean(SOUND_STATE, true);
    }

    public boolean getShowAnswerState() {
        return soundPreference.getBoolean(SHOW_ANSWER_STATE, true);
    }

    public void setShowAnswerState(boolean state) {
        SharedPreferences.Editor editor = soundPreference.edit();
        editor.putBoolean(SHOW_ANSWER_STATE, state);
        editor.commit();
    }

    public String getLanguage() {
        return languagePreference.getString(LANGUAGE__KEY, "en");
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = languagePreference.edit();
        editor.putString(LANGUAGE__KEY, language);
        editor.commit();
    }

}
