# react-native-umeng-share
友盟分享登录的react-native android模块，仅集成qq,微信,新浪微博,需要其它的自行参照代码集成。ios模块待完成

- 自动安装:
   ```
       //first
       npm install react-native-puti-umeng-share --save
       //then
       react-native link
   ```
- Android配置:
   ```
   //微信配置
   在包名目录下创建wxapi文件夹，新建一个名为WXEntryActivity的activity继承WXCallbackActivity。
   并配置Android Manifest XML
    <activity
            android:name=".wxapi.WXEntryActivity"
            android:exported="true"
            android:launchMode="singleTop" />

   <!--配置QQ-->
   <activity
               android:name="com.umeng.qq.tencent.AuthActivity"
               android:launchMode="singleTask"
               android:noHistory="true" >

               <intent-filter>
                   <action android:name="android.intent.action.VIEW" />
                   <category android:name="android.intent.category.DEFAULT" />
                   <category android:name="android.intent.category.BROWSABLE" />
                   <data android:scheme="tencentQQID" />
               </intent-filter>
       </activity>

       <activity
               android:name="com.umeng.qq.tencent.AssistActivity"
               android:screenOrientation="portrait"
               android:theme="@android:style/Theme.Translucent.NoTitleBar"
               android:configChanges="orientation|keyboardHidden|screenSize"/>




    <!--配置新浪微博-->
      <activity
                android:name="com.umeng.socialize.media.WBShareCallBackActivity"
                android:configChanges="keyboardHidden|orientation"
                android:theme="@android:style/Theme.Translucent.NoTitleBar"
                android:exported="false"
                android:screenOrientation="portrait" >
            </activity>
     <activity android:name="com.sina.weibo.sdk.web.WeiboSdkWebActivity"
                      android:configChanges="keyboardHidden|orientation"
                      android:exported="false"
                      android:windowSoftInputMode="adjustResize">

            </activity>
     <activity
                android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
                android:launchMode="singleTask"
                android:name="com.sina.weibo.sdk.share.WbShareTransActivity">
                <intent-filter>
                    <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />
                    <category android:name="android.intent.category.DEFAULT" />
                </intent-filter>

          </activity>
   ```


- 使用:
   ```
       import UMShare from 'react-native-puti-umeng-share'

        //是否开启调试
        UMShare.debug(true)
        //设置微信参数
        UMShare.setWeixin(id,secret)
        //设置微信参数
        UMShare.setQQZone(id,key)
        //设置新浪微博参数
        UMShare.setSinaWeibo(key,secret,url)

        //分享。
        UMShare.share({
                    platform: `${UMShare.QQ}&${UMShare.WEIXIN}`,//分享平台,调用分享面板则使用&拼接
                    type: UMeng.UMWeb,  //分享类型
                    title: '我是分享标题',
                    url: 'https://www.baidu.com',
                    desc: '我是分享描述',
                    image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3867393576,1410529218&fm=27&gp=0.jpg'
                }).then((data) => {
                   ToastAndroid.show(data, ToastAndroid.SHORT);
                }).catch(e => {
                   ToastAndroid.show(e, ToastAndroid.SHORT);
                })

         //登录
         UMShare.login(UMShare.WEIXIN)
                 .then((data) => {
                             ToastAndroid.show(data, ToastAndroid.SHORT);
                     }).catch(e => {
                      ToastAndroid.show(e, ToastAndroid.SHORT);
                     })

          UMShare.isInstall(UMShare.WEIXIN)
                 .then((isInstall) => {
                            if (!isInstall) Toast('微信未安装')
                     })

   ```


## Events
| Event Name | Params | Notes | Example |
|---|---|---|---|
| debug | Bool | 是否开启debug模式| UMeng.debug(true)|
| setWeixin |String,String  | 设置微信参数| UMeng.setWeixin('id','secret')|
| setQQZone |String,String   | 设置QQ参数| UMeng.setQQZone('id','key')|
| setSinaWeibo |String,String ,String  | 设置新浪微博参数| UMeng.setSinaWeibo('key','secret','url')|
| share |Object  | 分享| UMeng.share({...})|
| login | String | 登录| UMeng.login(UMeng.QQ).then().catch()|
