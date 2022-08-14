#import <UIKit/UIKit.h>
#import "APayAuthInfo.h"
typedef void(^CompletionBlock)(NSDictionary *resultDic);
typedef enum {
    ALIPAY_TIDFACTOR_IMEI,
    ALIPAY_TIDFACTOR_IMSI,
    ALIPAY_TIDFACTOR_TID,
    ALIPAY_TIDFACTOR_CLIENTKEY,
    ALIPAY_TIDFACTOR_VIMEI,
    ALIPAY_TIDFACTOR_VIMSI,
    ALIPAY_TIDFACTOR_CLIENTID,
    ALIPAY_TIDFACTOR_APDID,
    ALIPAY_TIDFACTOR_MAX
} AlipayTidFactor;
@interface AlipaySDK : NSObject
+ (AlipaySDK *)defaultService;
@property (nonatomic, weak) UIWindow *targetWindow;
- (void)payOrder:(NSString *)orderStr
      fromScheme:(NSString *)schemeStr
        callback:(CompletionBlock)completionBlock;
- (void)payOrder:(NSString *)orderStr
   dynamicLaunch:(BOOL)dynamicLaunch
      fromScheme:(NSString *)schemeStr
        callback:(CompletionBlock)completionBlock;
- (void)processOrderWithPaymentResult:(NSURL *)resultUrl
                      standbyCallback:(CompletionBlock)completionBlock;
- (NSString *)fetchTradeToken;
- (void)auth_V2WithInfo:(NSString *)infoStr
             fromScheme:(NSString *)schemeStr
               callback:(CompletionBlock)completionBlock;
- (void)processAuth_V2Result:(NSURL *)resultUrl
             standbyCallback:(CompletionBlock)completionBlock;
- (void)authWithInfo:(APayAuthInfo *)authInfo
            callback:(CompletionBlock)completionBlock;
- (void)processAuthResult:(NSURL *)resultUrl
          standbyCallback:(CompletionBlock)completionBlock;
- (BOOL)payInterceptorWithUrl:(NSString *)urlStr
                   fromScheme:(NSString *)schemeStr
                     callback:(CompletionBlock)completionBlock;
- (NSString*)queryTidFactor:(AlipayTidFactor)factor;
- (BOOL)isLogined;
- (NSString *)currentVersion;
- (void)setUrl:(NSString *)url;
- (void)fetchSdkConfigWithBlock:(void(^)(BOOL success))block;
@end
