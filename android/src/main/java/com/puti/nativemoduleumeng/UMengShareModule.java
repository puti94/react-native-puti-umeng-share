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
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import java.util.Map;

import static com.puti.nativemoduleumeng.Utils.getPlatform;
import static com.puti.nativemoduleumeng.Utils.getPlatforms;

/**
 * Created by puti on 2017/11/16.
 */

public class UMengShareModule extends ReactContextBaseJavaModule {

    private static final String TAG = "UMengShareModule";


    public UMengShareModule(ReactApplicationContext reactContext) {
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
        return "UMengShare";
    }


    @ReactMethod
    public void share(ReadableMap params, final Promise callback) {
        Log.d(TAG, "share: " + params.toString());
        /**
         * 友盟分享回调
         */
        UMShareListener shareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Log.d(TAG, "onResult: ");
                callback.resolve("onResult");
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Log.d(TAG, "onError: ");
                callback.reject("-1", t);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Log.d(TAG, "onCancel: ");
                callback.resolve("onCancel");
            }
        };
        ShareAction shareAction = new ShareAction(getCurrentActivity());
        shareAction.setCallback(shareListener);
        if (!params.getString("text").isEmpty()) {
            shareAction.withText(params.getString("text"));
        }
        buildShareMessage(params, shareAction);
        if (params.getString("platform").contains("&")) {
            shareAction.setDisplayList(getPlatforms(params.getString("platform"))).open();
        } else {
            shareAction.setPlatform(getPlatform(params.getString("platform"))).share();
        }
    }


    /**
     * 是否是debug模式
     *
     * @param debug
     */
    @ReactMethod
    public void debug(Boolean debug) {
        Config.DEBUG = debug;
    }

    @ReactMethod
    public void setWeixin(String id, String secret) {
        PlatformConfig.setWeixin(id, secret);
    }

    @ReactMethod
    public void setQQZone(String id, String key) {
        PlatformConfig.setQQZone(id, key);
    }

    @ReactMethod
    public void setSinaWeibo(String key, String secret, String url) {
        PlatformConfig.setSinaWeibo(key, secret, url);
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


    /**
     * 构建友盟分享消息参数
     *
     * @param params
     * @param shareAction
     */
    private void buildShareMessage(ReadableMap params, ShareAction shareAction) {
        String type = params.getString("type");
        if (type.isEmpty()) return;
        String image = params.getString("image");
        String title = params.getString("title");
        String url = params.getString("url");
        String desc = params.getString("desc");
        UMImage thumb = new UMImage(getCurrentActivity(), image);
        switch (type) {
            case "UMImage":
                shareAction.withMedia(thumb);
                return;
            case "UMWeb":
                if (!url.isEmpty()) {
                    UMWeb umWeb = new UMWeb(url);
                    umWeb.setTitle(title != null ? title : "");
                    umWeb.setThumb(thumb);
                    umWeb.setDescription(desc);
                    shareAction.withMedia(umWeb);
                }
                return;
            case "UMVideo":
                if (!url.isEmpty()) {
                    UMVideo umVideo = new UMVideo(url);
                    umVideo.setTitle(title != null ? title : "");
                    umVideo.setThumb(thumb);
                    umVideo.setDescription(desc);
                    shareAction.withMedia(umVideo);
                }
                return;
            case "UMusic":
                if (!url.isEmpty()) {
                    UMusic uMusic = new UMusic(url);
                    uMusic.setTitle(title != null ? title : "");
                    uMusic.setThumb(thumb);
                    uMusic.setDescription(desc);
                    shareAction.withMedia(uMusic);
                }
        }
    }
}
