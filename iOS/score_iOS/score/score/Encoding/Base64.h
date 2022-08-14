//
//  Base64.h
//  nmgs
//
//  Created by zeppin on 2016/11/1.
//  Copyright © 2016年 zeppin. All rights reserved.
//

#ifndef Base64_h
#define Base64_h

@interface Base64 : NSObject
+(int)char2Int:(char)c;
+(NSData *)decode:(NSString *)data;
+(NSString *)encode:(NSData *)data;
@end

#endif /* Base64_h */
