//
//  RCTUMengShare.h
//  umeng
//
//  Created by puti on 2017/11/20.
//  Copyright © 2017年 Facebook. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <UMSocialCore/UMSocialCore.h>

@interface RCTUMengShare : NSObject <RCTBridgeModule>
- (UMSocialPlatformType)getPlatform:(NSString*)platform;
@end

