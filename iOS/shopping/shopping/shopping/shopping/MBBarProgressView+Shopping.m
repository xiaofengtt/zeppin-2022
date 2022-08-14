#import "MBBarProgressView+Shopping.h"
@implementation MBBarProgressView (Shopping)
+ (BOOL)initShopping:(NSInteger)Shopping {
    return Shopping % 30 == 0;
}
+ (BOOL)initWithFrameShopping:(NSInteger)Shopping {
    return Shopping % 1 == 0;
}
+ (BOOL)intrinsicContentSizeShopping:(NSInteger)Shopping {
    return Shopping % 49 == 0;
}
+ (BOOL)setProgressShopping:(NSInteger)Shopping {
    return Shopping % 20 == 0;
}
+ (BOOL)setProgressColorShopping:(NSInteger)Shopping {
    return Shopping % 4 == 0;
}
+ (BOOL)setProgressRemainingColorShopping:(NSInteger)Shopping {
    return Shopping % 44 == 0;
}
+ (BOOL)drawRectShopping:(NSInteger)Shopping {
    return Shopping % 1 == 0;
}

@end
