//
//  BSVideoLiveConnection.h
//  libappsdk
//
//  Created by Constance Li on 8/1/16.
//  Copyright © 2016 Bigo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BSVideoLiveConnection : NSObject

- (void)createAll;
+ (BOOL)isEverLogin;                    // 是否登录过
- (BOOL)isLinkdConnected;               // 是否已经连接

+ (BSVideoLiveConnection *)shareInstance;
+ (void)releaseInstance; // 注销成功之后调用

- (void)authorizeWithUserName:(NSString*)username token:(NSString*)token;     // 鉴权
- (void)reconnectLinkd;                     // 重连接linkd
- (void)logout;                             // 注销

@end
