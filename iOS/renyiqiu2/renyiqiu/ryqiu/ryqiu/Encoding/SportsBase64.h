#ifndef Base64_h
#define Base64_h
@interface SportsBase64 : NSObject
+(int)char2Int:(char)c;
+(NSData *)decode:(NSString *)data;
+(NSString *)encode:(NSData *)data;
@end
#endif 
