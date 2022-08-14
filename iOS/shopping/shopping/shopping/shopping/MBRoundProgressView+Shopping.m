#import "MBRoundProgressView+Shopping.h"
@implementation MBRoundProgressView (Shopping)
+ (BOOL)initShopping:(NSInteger)Shopping {
    return Shopping % 23 == 0;
}
+ (BOOL)initWithFrameShopping:(NSInteger)Shopping {
    return Shopping % 39 == 0;
}
+ (BOOL)intrinsicContentSizeShopping:(NSInteger)Shopping {
    return Shopping % 38 == 0;
}
+ (BOOL)setProgressShopping:(NSInteger)Shopping {
    return Shopping % 9 == 0;
}
+ (BOOL)setProgressTintColorShopping:(NSInteger)Shopping {
    return Shopping % 5 == 0;
}
+ (BOOL)setBackgroundTintColorShopping:(NSInteger)Shopping {
    return Shopping % 18 == 0;
}
+ (BOOL)drawRectShopping:(NSInteger)Shopping {
    return Shopping % 11 == 0;
}

@end
