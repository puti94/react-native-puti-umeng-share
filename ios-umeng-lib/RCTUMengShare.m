//
//  RCTUMengShare.m
//  umeng
//
//  Created by puti on 2017/11/20.
//  Copyright © 2017年 Facebook. All rights reserved.
//
#import "RCTUMengShare.h"
#import <UMSocialCore/UMSocialCore.h>
#import <UShareUI/UShareUI.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTBridge.h>


@implementation RCTUMengShare

//- (instancetype)init
//{
//    self = [super init];
//    if (self) {
//        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleOpenURL:) name:@"RCTOpenURLNotification" object:nil];
//    }
//    return self;
//}
//
//- (void)dealloc
//{
//    [[NSNotificationCenter defaultCenter] removeObserver:self];
//}
//
//- (BOOL)handleOpenURL:(NSNotification *)aNotification
//{
//    NSString * aURLString =  [aNotification userInfo][@"url"];
//    NSURL * aURL = [NSURL URLWithString:aURLString];
//    
//    if ([[UMSocialManager defaultManager] handleOpenURL:aURL])
//    {
//        return YES;
//    } else {
//        return NO;
//    }
//}





RCT_EXPORT_MODULE(UMengShare);
RCT_EXPORT_METHOD(debug:(BOOL *)debug) {
    [[UMSocialManager defaultManager] openLog:debug];
}
RCT_EXPORT_METHOD(setAppkey:(NSString *)appkey){
    [[UMSocialManager defaultManager] setUmSocialAppkey:appkey];
}
RCT_EXPORT_METHOD(setWeixin:(NSString *)key secret:(NSString *)secret) {
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_WechatSession appKey:key appSecret:secret redirectURL:nil];
}
RCT_EXPORT_METHOD(setQQZone:(NSString *)key secret:(NSString *)secret){
    
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_QQ appKey:key appSecret:secret redirectURL:nil];
    
}
RCT_EXPORT_METHOD(setSinaWeibo:(NSString *)key secret:(NSString *)secret url:(NSString *)url){
    [[UMSocialManager defaultManager] setPlaform:UMSocialPlatformType_Sina appKey:key  appSecret:secret redirectURL:url];
}
RCT_EXPORT_METHOD(isInstall:(NSString *)platform callback:(RCTResponseSenderBlock)callback){
    dispatch_async(dispatch_get_main_queue(), ^{
        BOOL install = [[UMSocialManager defaultManager] isInstall:[self getPlatform:platform]];
        if (install) {
            callback([[NSArray alloc] initWithObjects:@"true", nil]);
        }else{
            callback([[NSArray alloc] initWithObjects:@"false", nil]);
        }
    });
}
RCT_EXPORT_METHOD(share:(NSDictionary *)params resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject){
  
    NSLog(@"share %@",params[@"platform"]);
    dispatch_async(dispatch_get_main_queue(), ^{
        
        if ([params[@"platform"] rangeOfString:@"&"].location != NSNotFound) {
            NSArray *platformStr = [params[@"platform"] componentsSeparatedByString:@"&"];
            NSMutableArray *platforms = [[NSMutableArray alloc] init];
            for (NSString *s in platformStr) {
                UMSocialPlatformType platform = [self getPlatform:s];
                [platforms addObject:@(platform)];
            }
            [UMSocialUIManager setPreDefinePlatforms:platforms];
            [UMSocialUIManager showShareMenuViewInWindowWithPlatformSelectionBlock:^(UMSocialPlatformType platformType, NSDictionary *userInfo) {
                [[UMSocialManager defaultManager] shareToPlatform:platformType messageObject:[self buildShareMessage:params] currentViewController:nil completion:^(id data, NSError *error) {
                   NSLog(@"分享回调");
                    if (error) {
                        reject(@"-1",@"分享失败",error);
                    }else{
                        if ([data isKindOfClass:[UMSocialShareResponse class]]) {
                            UMSocialShareResponse *resp = data;
                            NSMutableDictionary *res = [[NSMutableDictionary alloc] init];
                            [res setValue:resp.message forKey:@"message"];
                            [res setValue:resp.originalResponse forKey:@"originalResponse"];
                            resolve(res);
                        }else{
                            resolve(data);
                        }
                    }
                }];
            }];
        } else {
            [[UMSocialManager defaultManager] shareToPlatform:[self getPlatform:params[@"platform"]] messageObject:[self buildShareMessage:params] currentViewController:nil completion:^(id data, NSError *error) {
              
              NSLog(@"分享回调");
              
                if (error) {
                    reject(@"-1",@"分享失败",error);
                }else{
                    if ([data isKindOfClass:[UMSocialShareResponse class]]) {
                        UMSocialShareResponse *resp = data;
                        NSMutableDictionary *res = [[NSMutableDictionary alloc] init];
                        [res setValue:resp.message forKey:@"message"];
                        [res setValue:resp.originalResponse forKey:@"originalResponse"];
                        resolve(res);
                    }else{
                        resolve(data);
                    }
                }
            }];
        }
    });
}
RCT_EXPORT_METHOD(login:(NSString *)platform resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject){
    dispatch_async(dispatch_get_main_queue(), ^{
        [[UMSocialManager defaultManager] getUserInfoWithPlatform:[self getPlatform:platform] currentViewController:nil completion:^(id result, NSError *error) {
           NSLog(@"登录回调");
            if (error) {
                reject(@"-1",@"登录失败",error);
            }else{
                UMSocialUserInfoResponse *resp = result;
                NSMutableDictionary *data = [[NSMutableDictionary alloc] init];
                [data setValue:resp.unionId forKey:@"unionId"];
                [data setValue:resp.uid forKey:@"uid"];
                [data setValue:resp.openid forKey:@"openid"];
                [data setValue:resp.accessToken forKey:@"accessToken"];
                [data setValue:resp.refreshToken forKey:@"refreshToken"];
                [data setValue:resp.expiration forKey:@"expiration"];
                [data setValue:resp.name forKey:@"name"];
                [data setValue:resp.iconurl forKey:@"iconurl"];
                [data setValue:resp.unionGender forKey:@"unionGender"];
                [data setValue:resp.originalResponse forKey:@"originalResponse"];
                resolve(data);
            }
        }];
    });
}



- (UMSocialMessageObject *)buildShareMessage:(NSDictionary *)params{
    UMSocialMessageObject *message = [UMSocialMessageObject messageObject];
    message.text = params[@"text"];
    UMShareImageObject *shareImage = [[UMShareImageObject alloc] init];
    shareImage.shareImage = params[@"image"];
    if ([params[@"type"]  isEqual: @"UMImage"]) {
        message.shareObject = shareImage;
    }else if ([params[@"type"]  isEqual: @"UMWeb"]) {
        UMShareWebpageObject *shareWeb = [[UMShareWebpageObject alloc] init];
        shareWeb.descr = params[@"desc"];
        shareWeb.webpageUrl = params[@"url"];
        shareWeb.title = params[@"title"];
        shareWeb.thumbImage = shareImage;
        message.shareObject = shareWeb;
    }else if ([params[@"type"]  isEqual: @"UMVideo"]) {
        UMShareVideoObject *shareVideo = [[UMShareVideoObject alloc] init];
        shareVideo.descr = params[@"desc"];
        shareVideo.videoUrl = params[@"url"];
        shareVideo.title = params[@"title"];
        shareVideo.thumbImage = shareImage;
        message.shareObject = shareVideo;
    }else if ([params[@"type"]  isEqual: @"UMusic"]) {
        UMShareMusicObject *shareMusic = [[UMShareMusicObject alloc] init];
        shareMusic.descr = params[@"desc"];
        shareMusic.musicUrl = params[@"url"];
        shareMusic.title = params[@"title"];
        shareMusic.thumbImage = shareImage;
        message.shareObject = shareMusic;
    }
    return message;
}





- (UMSocialPlatformType)getPlatform:(NSString*)platform{
    if ([platform  isEqual: @"QQ"]) {
        return UMSocialPlatformType_QQ;
    }else if ([platform  isEqual: @"QZONE"]) {
        return UMSocialPlatformType_Qzone;
    }else if ([platform  isEqual: @"WEIXIN"]) {
        return UMSocialPlatformType_WechatSession;
    }else if ([platform  isEqual: @"WEIXIN_CIRCLE"]) {
        return UMSocialPlatformType_WechatTimeLine;
    }else if ([platform  isEqual: @"WEIXIN_FAVORITE"]) {
        return UMSocialPlatformType_WechatFavorite;
    }else {
        return UMSocialPlatformType_Sms;
    }
}


@end

