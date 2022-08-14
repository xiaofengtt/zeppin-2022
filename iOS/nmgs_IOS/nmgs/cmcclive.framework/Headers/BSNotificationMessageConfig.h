//
//  BSNotificationMessageConfig.h
//  libappsdk
//
//  Created by Constance Li on 8/3/16.
//  Copyright © 2016 Bigo Inc. All rights reserved.
//



// Login
#define Msg_Login_Login_Prepare         @"Msg_Login_Login_Prepare"          // 登录成功通知发送前的数据准备
#define Msg_Login_Login_Success         @"Msg_Login_Login_Success"          // 登录成功
#define Msg_Login_Login_Fail            @"Msg_Login_Login_Fail"             // 登录失败

// Logout
#define Msg_Logout_Logout_Success       @"Msg_Logout_Logout_Success"        // 注销成功
#define Msg_Logout_Logout_Fail          @"Msg_Logout_Logout_Fail"           // 注销失败
#define Msg_Logout_Linkd_Abnormal       @"Msg_Logout_Linkd_Abnormal"        // 重连异常
#define Msg_Logout_Linkd_Disconnect     @"Msg_Logout_Linkd_Disconnect"      // 连接断开
#define Msg_Logout_Linkd_Kickout        @"Msg_Logout_Linkd_Kickout"         // 账号在别处登录，强制下线
#define Msg_Logout_Linkd_CookieKick     @"Msg_Logout_Linkd_CookieKick"      // Cookie过期，强制下线
#define Msg_Logout_Linkd_UnbindKick     @"Msg_Logout_Linkd_UnbindKick"      // 手机号被解绑，强制下线
#define Msg_Logout_Linkd_BlackListKick  @"Msg_Logout_Linkd_BlackListKick"   // 被加入黑名单，强制下线
