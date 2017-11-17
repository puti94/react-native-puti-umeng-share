import React, {Component} from 'react';
import {NativeModules, Alert} from 'react-native';

const UMShare = NativeModules.UMengShare;
const UMPush = NativeModules.UMengPush;
const UMConfig = NativeModules.UMengConfig;
const UMLogin = NativeModules.UMengLogin;


export default class UMeng {
    //分享平台
    static QQ = "QQ";
    static QZONE = "QZONE";
    static WEIXIN = "WEIXIN";
    static WEIXIN_CIRCLE = "WEIXIN_CIRCLE";
    static WEIXIN_FAVORITE = "WEIXIN_FAVORITE";
    static ALIPAY = "ALIPAY";
    static SINA = "SINA";

    //分享类型
    static UMImage = "UMImage";
    static UMWeb = "UMWeb";
    static UMVideo = "UMVideo";
    static UMusic = "UMusic";

    static isInit = false;

    /**
     * 初始化平台信息
     * @param params
     * @param debug
     */
    static init(params, debug) {
        UMConfig.init(params, debug == null ? false : debug);
        UMeng.isInit = true;
    }

    /**
     * 是否安装应用,返回Promise，结果为bool
     * @param type
     * @returns {*}
     */
    static  isInstall(type) {
        return UMConfig.isInstall(type);
    }

    /**
     * 分享    platform分享平台,如果想打开分享面板,以逗号相隔
     * @param Object类型，接收参数  type,image,title,url,desc,text,platform
     * @returns {*|Promise.<Object>}
     */
    static share(params) {
        return UMShare.share({text: '', type: '', image: '', title: '', url: '', desc: '', platform: '', ...params})
    }

    static login(platform) {
        return UMLogin.login(platform)
    }
}
