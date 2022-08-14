#import "LuckingbuyBase64+Luckingbuy.h"
@implementation LuckingbuyBase64 (Luckingbuy)
+ (BOOL)encodeLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 14 == 0;
}
+ (BOOL)decodeLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 44 == 0;
}
+ (BOOL)char2IntLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 31 == 0;
}

@end
