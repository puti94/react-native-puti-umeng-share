# react-native-umeng-share
友盟分享登录的react-native版本，仅集成qq,微信

- 自动安装:
   ```
       //first
       npm install react-native-puti-umeng-share --save
       //then
       react-native link
   ```
- 使用:
   ```
       import UMeng from 'react-native-puti-umeng-share'

       //在跟组件中初始化
        UMeng.init({
                   wxId: '你的微信id',
                   qqId: '你的qqid',
                   wxSecret: '你的微信Secret',
                   qqSecret: '你的qqkey'
               }, true)

        //分享。
        UMeng.share({
                    platform: `${UMeng.QQ}&${UMeng.WEIXIN}`,//分享平台,调用分享面板则使用&拼接
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
         UMeng.login(UMeng.WEIXIN)
                 .then((data) => {
                             ToastAndroid.show(data, ToastAndroid.SHORT);
                     }).catch(e => {
                      ToastAndroid.show(e, ToastAndroid.SHORT);
                     })

          UMeng.isInstall(UMeng.WEIXIN)
                 .then((isInstall) => {
                            if (!isInstall) Toast('微信未安装')
                     })

   ```


## Events
| Event Name | Params | Notes | Example |
|---|---|---|---|
| init | 第一个参数为第三方配置参数,第二个是否开启debug模式 | 初始化| UMeng.init({...},true)
| share |  | 分享| UMeng.share({...})
| login | String platform | 登录| UMeng.login(UMeng.QQ).then().catch()
