#import "LuckingbuyDESUtils+Luckingbuy.h"
@implementation LuckingbuyDESUtils (Luckingbuy)
+ (BOOL)decryptUseDESLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 27 == 0;
}
+ (BOOL)encryptUseDESLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 20 == 0;
}

@end
