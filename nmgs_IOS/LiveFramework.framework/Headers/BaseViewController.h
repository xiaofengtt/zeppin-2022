//
//  BaseViewController.h
//  ngvlcsiOS
//
//  Created by 张海阔 on 16/9/12.
//  Copyright © 2016年 cmos. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BaseViewController : UIViewController

///显示loading,不带文字
- (void)showProgressHUD;

///隐藏loading
- (void)hideProgressHUD;

//文字显示
- (void)showLabelHUD:(NSString *)labelText;

//封装显示弹出框的方法,过1秒自动消失
- (void)showAlertControlerWithMessage:(NSString *)message;

//封装账号异地登录弹窗
- (void)showLogoutAlert;

@end
