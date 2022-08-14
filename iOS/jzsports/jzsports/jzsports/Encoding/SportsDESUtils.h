#ifndef DES_h
#define DES_h
#import "SportsBase64.h"
@interface SportsDESUtils : NSObject
+(NSString *)decryptUseDES:(NSString *)cipherText;
+(NSString *) encryptUseDES:(NSString *)plainText;
@end
#endif 
