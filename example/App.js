/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    Button,
    ToastAndroid,
} from 'react-native';

import UMShare from 'react-native-puti-umeng-share'


export default class App extends Component<{}> {

    componentDidMount() {

        // UMShare.debug(true);
        UMShare.setWeixin('', '');
        UMShare.setQQZone('', '');
        UMShare.setSinaWeibo('', '', 'https://api.weibo.com/oauth2/default.html');
    }

    render() {
        return (
            <View style={styles.container}>
                <Button title="测试友盟分享" onPress={() => {
                    UMShare.share({
                        platform: `${UMShare.WEIXIN}&${UMShare.QQ}`,
                        type: UMShare.UMVideo,
                        title: '我是分享标题',
                        url: 'http://www.cqiqb.com/statics/uploads/video/chuangqi.mp4',
                        desc: '我是分享描述',
                        image: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3867393576,1410529218&fm=27&gp=0.jpg'
                    }, (res) => {
                        alert(res)
                    })
                }}/>
                <Button title="测试友盟安装应用" onPress={() => {
                    UMShare.isInstall(UMShare.WEIXIN, res => {
                        alert(res);
                    });

                    // ToastAndroid.show(, ToastAndroid.SHORT)
                }}/>
                <Button title="测试友盟登录" onPress={async () => {
                    try {
                        let re = await UMShare.login(UMShare.WEIXIN);
                        console.log('登录回调', re);
                    } catch (e) {
                        console.error("登录回调", e);
                    }
                }}/>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
