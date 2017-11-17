package com.puti.nativemoduleumeng;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
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

    /**
     * 友盟授权回调
     */
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调，可以用来处理等待框，或相关的文字提示
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };


    public UMengShareModule(ReactApplicationContext reactContext) {
        super(reactContext);
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
        SHARE_MEDIA platform = getPlatform(params.getString("platform"));
        if (platform == SHARE_MEDIA.MORE) {
            shareAction.setDisplayList(getPlatforms(params.getString("platform"))).open();
        } else {
            shareAction.setPlatform(platform).share();
        }
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
