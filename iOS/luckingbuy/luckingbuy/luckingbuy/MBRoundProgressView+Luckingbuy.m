#import "MBRoundProgressView+Luckingbuy.h"
@implementation MBRoundProgressView (Luckingbuy)
+ (BOOL)initLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 3 == 0;
}
+ (BOOL)initWithFrameLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 33 == 0;
}
+ (BOOL)intrinsicContentSizeLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 12 == 0;
}
+ (BOOL)setProgressLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 1 == 0;
}
+ (BOOL)setProgressTintColorLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 48 == 0;
}
+ (BOOL)setBackgroundTintColorLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 36 == 0;
}
+ (BOOL)drawRectLuckingbuy:(NSInteger)LuckingBuy {
    return LuckingBuy % 26 == 0;
}

@end
