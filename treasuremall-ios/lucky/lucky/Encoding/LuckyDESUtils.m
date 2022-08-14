#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonCryptor.h>
#import "LuckyDESUtils.h"
#import <CommonCrypto/CommonDigest.h>
@implementation LuckyDESUtils
+(NSString *)decryptUseDES:(NSString *)cipherText
{    
    NSData* cipherData = [self convertHexStrToData:cipherText];
    //秘钥
    NSString* key = @"treasuremallsys_2020";
    unsigned char buffer[1024];
    memset(buffer, 0, sizeof(char));
    size_t numBytesDecrypted = 0;
    NSData *testData = [key dataUsingEncoding: NSUTF8StringEncoding];
    Byte *iv = (Byte *)[testData bytes];
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
    //秘钥
    NSString* key = @"treasuremallsys_2020";
    NSData *textData = [plainText dataUsingEncoding:NSUTF8StringEncoding];
    NSUInteger dataLength = [plainText length];
    unsigned char buffer[1024];
    memset(buffer, 0, sizeof(char));
    NSData *testData = [key dataUsingEncoding: NSUTF8StringEncoding];
    Byte *iv = (Byte *)[testData bytes];
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
        Byte *bytes = (Byte *)[data bytes];
        NSString *string = @"";
        for (NSInteger i = 0; i<data.length; i++) {
            NSString *newHexStr = [NSString stringWithFormat:@"%x", bytes[i] & 0xff]; //16进制数
            newHexStr = [newHexStr uppercaseString];
            if ([newHexStr length] == 1) {
                newHexStr = [NSString stringWithFormat:@"0%@",newHexStr];
            }
            string = [string stringByAppendingString:newHexStr];
        }
        
        ciphertext = [string lowercaseStringWithLocale:[NSLocale currentLocale]];
    }
    return ciphertext;
}

+ (NSData *)convertHexStrToData:(NSString *)str {
    if (!str || [str length] == 0) {
        return nil;
    }
    
    NSMutableData *hexData = [[NSMutableData alloc] initWithCapacity:8];
    NSRange range;
    if ([str length] % 2 == 0) {
        range = NSMakeRange(0, 2);
    } else {
        range = NSMakeRange(0, 1);
    }
    for (NSInteger i = range.location; i < [str length]; i += 2) {
        unsigned int anInt;
        NSString *hexCharStr = [str substringWithRange:range];
        NSScanner *scanner = [[NSScanner alloc] initWithString:hexCharStr];
        
        [scanner scanHexInt:&anInt];
        NSData *entity = [[NSData alloc] initWithBytes:&anInt length:1];
        [hexData appendData:entity];
        
        range.location += range.length;
        range.length = 2;
    }
    
    return hexData;
}
@end
