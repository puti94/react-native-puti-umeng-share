//
//  react_native_umeng_share.m
//  react-native-umeng-share
//
//  Created by puti on 2017/11/19.
//  Copyright © 2017年 puti. All rights reserved.
//

#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(UMengConfig, NSObject)
RCT_EXTERN_METHOD(config:(NSString *)name location:(NSString *)location date:(nonnull NSNumber *)date)

@end


