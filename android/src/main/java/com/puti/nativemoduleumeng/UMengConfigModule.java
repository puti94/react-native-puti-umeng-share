package com.puti.nativemoduleumeng;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import static com.puti.nativemoduleumeng.Utils.getPlatform;

/**
 * Created by puti on 2017/11/16.
 */

public class UMengConfigModule extends ReactContextBaseJavaModule {

    private static final String TAG = "UMengConfigModule";

    public UMengConfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(new BaseActivityEventListener() {
            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                UMShareAPI.get(getCurrentActivity()).onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    @Override
    public String getName() {
        return "UMengConfig";
    }

    /**
     * 初始化第三方平台参数
     *
     * @param params
     */
    @ReactMethod
    public void init(ReadableMap params, Boolean debug) {
        Log.d(TAG, "init: " + params.toString() + "  DEBUG:" + debug);
        Config.DEBUG = debug;
        if (!params.getString("wxId").isEmpty()) {
            PlatformConfig.setWeixin(params.getString("wxId"), params.getString("wxSecret"));
        }
        if (!params.getString("qqId").isEmpty()) {
            PlatformConfig.setQQZone(params.getString("qqId"), params.getString("qqSecret"));
        }
    }

    /**
     * 是否安装了应用
     *
     * @param type
     * @param promise
     */
    @ReactMethod
    public void isInstall(String type, Promise promise) {
        boolean install = UMShareAPI.get(getCurrentActivity()).isInstall(getCurrentActivity(), getPlatform(type));
        Log.d(TAG, "isInstall: " + type + install);
        promise.resolve(Boolean.valueOf(install));
    }

}
