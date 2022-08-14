#ifndef DES_h
#define DES_h
#import "LuckyBase64.h"
@interface LuckyDESUtils : NSObject
+(NSString *)decryptUseDES:(NSString *)cipherText;
+(NSString *) encryptUseDES:(NSString *)plainText;
@end
#endif 
