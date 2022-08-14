#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonCryptor.h>
#import "SportsDESUtils.h"
#import <CommonCrypto/CommonDigest.h>
@implementation SportsDESUtils
+(NSString *)decryptUseDES:(NSString *)cipherText
{    
    NSData* cipherData = [SportsBase64 decode:cipherText];
    NSString* key = @"cmossomc";
    unsigned char buffer[1024];
    memset(buffer, 0, sizeof(char));
    size_t numBytesDecrypted = 0;
    Byte iv[] = {0,0,0,0,0,0,0,0};
    CCCryptorStatus cryptStatus = CCCrypt(kCCDecrypt,
                                          kCCAlgorithmDES,
                                           kCCOptionPKCS7Padding|kCCOptionECBMode,
                                          [key UTF8String],
                                          kCCKeySizeDES,
                                          iv,
                                          [cipherData bytes],
                                          [cipherData length],
                                          buffer,
                                          1024,
                                          &numBytesDecrypted);
    NSString* plainText = nil;
    if (cryptStatus == kCCSuccess) {
        NSData* data = [NSData dataWithBytes:buffer length:(NSUInteger)numBytesDecrypted];
        plainText = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    }
    return plainText;
}
+ (NSString *) encryptUseDES:(NSString *)plainText
{
    NSString *ciphertext = nil;
    NSString* key = @"cmossomc";
    NSData *textData = [plainText dataUsingEncoding:NSUTF8StringEncoding];
    NSUInteger dataLength = [plainText length];
    unsigned char buffer[1024];
    memset(buffer, 0, sizeof(char));
    Byte iv[] = {1,2,3,4,5,6,7,8};
    size_t numBytesEncrypted = 0;
    CCCryptorStatus cryptStatus = CCCrypt(kCCEncrypt, kCCAlgorithmDES,
                                           kCCOptionPKCS7Padding|kCCOptionECBMode,
                                          [key UTF8String], kCCKeySizeDES,
                                          iv,
                                          [textData bytes], dataLength,
                                          buffer, 1024,
                                          &numBytesEncrypted);
    if (cryptStatus == kCCSuccess) {
        NSData *data = [NSData dataWithBytes:buffer length:(NSUInteger)numBytesEncrypted];
        ciphertext = [SportsBase64  encode:data];
    }
    return ciphertext;
}
@end
