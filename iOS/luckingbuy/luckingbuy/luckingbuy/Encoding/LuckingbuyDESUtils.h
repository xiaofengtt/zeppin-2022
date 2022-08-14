#ifndef DES_h
#define DES_h
#import "LuckingbuyBase64.h"
@interface LuckingbuyDESUtils : NSObject
+(NSString *)decryptUseDES:(NSString *)cipherText;
+(NSString *) encryptUseDES:(NSString *)plainText;
@end
#endif 
