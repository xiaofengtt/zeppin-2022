//
//  Singleton.h
//  ngvlcsIOS
//
//  Created by 张海阔 on 2016/10/19.
//  Copyright © 2016年 cmos. All rights reserved.
//

#import "BaseModel.h"

@interface Singleton : BaseModel

+ (Singleton *)mainSingleton;//单例方法

//接口相关
@property (nonatomic, copy) NSString *baseUrl;//基本接口地址
@property (nonatomic, copy) NSString *token;//安全验证码
@property (nonatomic, copy) NSString *serverToken;//登录toten

//用于存储登录用户的资料信息
@property (nonatomic, copy) NSString *userId;//用户id
@property (nonatomic, copy) NSString *createTime;//创建时间 yyyy-MM-dd HH:mm:ss
@property (nonatomic, copy) NSString *isHost;//是否是主播

@property (nonatomic, copy) NSString *nickName;//昵称
@property (nonatomic, copy) NSString *userLevel;//用户等级
@property (nonatomic, copy) NSString *headPic;//用户头像
@property (nonatomic, copy) NSString *userType;//用户类型
@property (nonatomic, copy) NSString *phoneNum;//用户电话号码

@property (nonatomic, copy) NSString *userName;//用户名
@property (nonatomic, copy) NSString *age;//用户年龄
@property (nonatomic, copy) NSString *sex;//性别
@property (nonatomic, copy) NSString *exp;//经验值
@property (nonatomic, copy) NSString *score;//用户积分

@property (nonatomic, copy) NSString *openfireKeyStr;//openfire密码
@property (nonatomic, copy) NSString *phoneNumRegisterLocation;//用户注册地点


@property (nonatomic, assign) NSInteger netState;//网络状况
@property (nonatomic, assign) BOOL isBSVideoLogin;//视频是否鉴权成功
@property (nonatomic, assign) BOOL isInLiveRoom;//记录是否在直播间界面



@property (nonatomic, copy) void(^userLogoutBlock)();//退出登录的block接口
@property (nonatomic, copy) void(^tabBlock)(NSInteger index);



- (void)createAllWithUserName:(NSString *)userName token:(NSString *)token completeBlock:(void(^)())completeBlock;

- (void)BSVideoLiveReconnectLinkd;

- (void)BSVideoLiveCreateAll;

- (void)clearSingletonAllData;

//获取用户信息
- (void)obtainUserInfo;

- (void)logout;

+ (UIImage *)imageFromSdcacheWithUrlStr:(NSString *)urlStr;


@end
