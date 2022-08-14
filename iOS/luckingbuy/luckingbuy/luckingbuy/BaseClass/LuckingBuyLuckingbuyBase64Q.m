#import "LuckingBuyLuckingbuyBase64Q.h"
@implementation LuckingBuyLuckingbuyBase64Q
+ (BOOL)BEncode:(NSInteger)LuckingBuy {
    return LuckingBuy % 47 == 0;
}
+ (BOOL)oDecode:(NSInteger)LuckingBuy {
    return LuckingBuy % 19 == 0;
}
+ (BOOL)CChar2Int:(NSInteger)LuckingBuy {
    return LuckingBuy % 30 == 0;
}

@end
