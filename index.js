import React, {Component} from 'react';
import {NativeModules, Alert} from 'react-native';

const UMShare = NativeModules.UMengShare;


export default class UMShare {
    //分享平台
    static QQ = "QQ";
    static QZONE = "QZONE";
    static WEIXIN = "WEIXIN";
    static WEIXIN_CIRCLE = "WEIXIN_CIRCLE";
    static WEIXIN_FAVORITE = "WEIXIN_FAVORITE";
    static SINA = "SINA";


    //分享类型
    static UMImage = "UMImage";
    static UMWeb = "UMWeb";
    static UMVideo = "UMVideo";
    static UMusic = "UMusic";

    /**
     * 是否是debug模式
     * @param debug
     */
    static debug(debug) {
        UMShare.debug(debug == null ? false : debug);
    }

    /**
     * 设置微信参数
     * @param id
     * @param secret
     */
    static setWeixin(id, secret) {
        UMShare.setWeixin(id, secret);
    }

    /**
     * 设置QQ参数
     * @param id
     * @param key
     */
    static setQQZone(id, key) {
        UMShare.setQQZone(id, key);
    }

    /**
     * 设置新浪微博参数
     * @param key
     * @param secret
     * @param url
     */
    static setSinaWeibo(key, secret, url) {
        UMShare.setSinaWeibo(key, secret, url);
    }

    /**
     * 是否安装应用,返回Promise，结果为bool
     * @param type
     * @returns {*}
     */
    static  isInstall(type) {
        return UMShare.isInstall(type);
    }

    /**
     * 分享    platform分享平台,如果想打开分享面板,以逗号相隔
     * @param Object类型，接收参数  type,image,title,url,desc,text,platform
     * @returns {*|Promise.<Object>}
     */
    static share(params) {
        return UMShare.share({text: '', type: '', image: '', title: '', url: '', desc: '', platform: '',...params})
    }

    static login(platform) {
        return UMShare.login(platform)
    }
}
