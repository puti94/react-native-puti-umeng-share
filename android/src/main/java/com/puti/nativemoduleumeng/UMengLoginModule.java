package com.puti.nativemoduleumeng;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


/**
 * Created by puti on 2017/11/16.
 */

public class UMengLoginModule extends ReactContextBaseJavaModule {

    private static final String TAG = "UMengLoginModule";

    public UMengLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);

    }

    @Override
    public String getName() {
        return "UMengLogin";
    }


    @ReactMethod
    public void login(final String platform, final Promise callback) {
        Log.d(TAG, "login: " + platform);
        UMShareAPI.get(getCurrentActivity()).getPlatformInfo(getCurrentActivity(), Utils.getPlatform(platform), new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete: " + map.toString());

                callback.resolve(Utils.map2JsonString(map));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError: " + throwable.toString());
                callback.reject("" + i, throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel: ");
                callback.reject("-1", "onCancel");
            }
        });
    }
}
