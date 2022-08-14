//
//  DES.h
//  nmgs
//
//  Created by zeppin on 2016/11/1.
//  Copyright © 2016年 zeppin. All rights reserved.
//

#ifndef DES_h
#define DES_h

#import "Base64.h"
@interface DESUtils : NSObject
+(NSString *)decryptUseDES:(NSString *)cipherText;
+(NSString *) encryptUseDES:(NSString *)plainText;


@end

#endif /* DES_h */
