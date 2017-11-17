package com.puti.nativemoduleumeng;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by puti on 2017/11/17.
 */

public class Utils {


    /**
     * 通过字符串获取相应的类型
     *
     * @param platform
     * @return
     */
    public static SHARE_MEDIA getPlatform(String platform) {
        switch (platform) {
            case "QQ":
                return SHARE_MEDIA.QQ;
            case "QZONE":
                return SHARE_MEDIA.QZONE;
            case "WEIXIN":
                return SHARE_MEDIA.WEIXIN;
            case "WEIXIN_CIRCLE":
                return SHARE_MEDIA.WEIXIN_CIRCLE;
            case "WEIXIN_FAVORITE":
                return SHARE_MEDIA.WEIXIN_FAVORITE;
            case "ALIPAY":
                return SHARE_MEDIA.ALIPAY;
            case "SINA":
                return SHARE_MEDIA.SINA;
            case "MORE":
                return SHARE_MEDIA.MORE;
            default:
                return SHARE_MEDIA.MORE;
        }
    }

    public static SHARE_MEDIA[] getPlatforms(String platform) {
        String[] split = platform.split("&");
        SHARE_MEDIA[] array = new SHARE_MEDIA[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = getPlatform(split[i]);
        }
        return array;
    }

    /**
     * map转json字符串
     *
     * @param map
     * @return
     */
    public static String map2JsonString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("\"");
            sb.append(entry.getKey());
            sb.append("\"");
            sb.append(":");
            sb.append("\"");
            sb.append(entry.getValue());
            sb.append("\"");
            sb.append(",");
        }
        String s = sb.toString();
        int i = s.lastIndexOf(",");
        if (i == s.length() - 1) {
            s = s.substring(0, s.length() - 1);
        }
        return (s + "}");
    }

}
