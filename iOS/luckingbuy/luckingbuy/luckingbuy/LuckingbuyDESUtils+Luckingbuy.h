#import "LuckingbuyBase64.h"
#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonCryptor.h>
#import "LuckingbuyDESUtils.h"
#import <CommonCrypto/CommonDigest.h>

@interface LuckingbuyDESUtils (Luckingbuy)
+ (BOOL)decryptUseDESLuckingbuy:(NSInteger)LuckingBuy;
+ (BOOL)encryptUseDESLuckingbuy:(NSInteger)LuckingBuy;

@end
