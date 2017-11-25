# react-native-puti-umeng-share
友盟分享登录的react-native android模块，仅集成qq,微信,新浪微博,需要其它的自行参照代码集成。ios模块待完成

- 自动安装:
   ```
       //first
       npm install react-native-puti-umeng-share --save
       //then   android可自行导包，IOS由于静态库创建有问题，则需要手动集成
       react-native link
   ```
- IOS 集成

   - 第一步 前往node_module里的react-native-puti-umeng-share 文件夹下的ios-umeng-lib拷贝到工程目录下,并添加到项目中;
   ![image](https://github.com/puti94/react-native-puti-umeng-share/blob/master/screenshot/QQ20171124-195428.png)
   - 第二步 TARGET -> General -> Linked Frameworks and Libraries 添加 `libsqlite3.tbd` `CoreGraphics.framework` 系统库

   - 第三步 在项目中的info.plist中加入应用白名单，右键info.plist选择source code打开(plist具体设置在Build Setting -> Packaging -> Info.plist File可获取plist路径) 请根据选择的平台对以下配置进行缩减：
   ```
   <key>LSApplicationQueriesSchemes</key>
   <array>
       <!-- 微信 URL Scheme 白名单-->
       <string>wechat</string>
       <string>weixin</string>

       <!-- 新浪微博 URL Scheme 白名单-->
       <string>sinaweibohd</string>
       <string>sinaweibo</string>
       <string>sinaweibosso</string>
       <string>weibosdk</string>
       <string>weibosdk2.5</string>

       <!-- QQ、Qzone、TIM URL Scheme 白名单-->
       <string>mqqapi</string>
       <string>mqq</string>
       <string>mqqOpensdkSSoLogin</string>
       <string>mqqconnect</string>
       <string>mqqopensdkdataline</string>
       <string>mqqopensdkgrouptribeshare</string>
       <string>mqqopensdkfriend</string>
       <string>mqqopensdkapi</string>
       <string>mqqopensdkapiV2</string>
       <string>mqqopensdkapiV3</string>
       <string>mqqopensdkapiV4</string>
       <string>mqzoneopensdk</string>
       <string>wtloginmqq</string>
       <string>wtloginmqq2</string>
       <string>mqqwpa</string>
       <string>mqzone</string>
       <string>mqzonev2</string>
       <string>mqzoneshare</string>
       <string>wtloginqzone</string>
       <string>mqzonewx</string>
       <string>mqzoneopensdkapiV2</string>
       <string>mqzoneopensdkapi19</string>
       <string>mqzoneopensdkapi</string>
       <string>mqqbrowser</string>
       <string>mttbrowser</string>
       <string>tim</string>
       <string>timapi</string>
       <string>timopensdkfriend</string>
       <string>timwpa</string>
       <string>timgamebindinggroup</string>
       <string>timapiwallet</string>
       <string>timOpensdkSSoLogin</string>
       <string>wtlogintim</string>
       <string>timopensdkgrouptribeshare</string>
       <string>timopensdkapiV4</string>
       <string>timgamebindinggroup</string>
       <string>timopensdkdataline</string>
       <string>wtlogintimV1</string>
       <string>timapiV1</string>

   </array>

   ```

   - 第四步 设置 URL Scheme URL Scheme是通过系统找到并跳转对应app的设置，通过向项目中的info.plist文件中加入URL types可使用第三方平台所注册的appkey信息向系统注册你的app，当跳转到第三方应用授权或分享后，可直接跳转回你的app。

  ![image](https://github.com/puti94/react-native-puti-umeng-share/blob/master/screenshot/QQ20171124-200218.png)

   - 第五步 在入口文件AppDelegate.m下设置回调
   ```
   #import <React/RCTLinkingManager.h>

   - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
   {
     return [RCTLinkingManager application:application openURL:url
                         sourceApplication:sourceApplication annotation:annotation];
   }

   - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey, id> *)options
   {
     return [RCTLinkingManager application:application openURL:url options:options];
   }

   ```



- Android配置:
   ```

   //需要配置友盟APPKEY,否则分享不可用
   <meta-data
               android:name="UMENG_APPKEY"
               android:value="友盟APPKEY" />


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
                },res=>{
                if (res){
                   alert('分享成功')
                }else{
                   alert('分享失败')
                }
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
