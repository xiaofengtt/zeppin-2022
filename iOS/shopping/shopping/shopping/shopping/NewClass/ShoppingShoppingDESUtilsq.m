#import "ShoppingShoppingDESUtilsq.h"
@implementation ShoppingShoppingDESUtilsq
+ (BOOL)bDecryptusedes:(NSInteger)Shopping {
    return Shopping % 26 == 0;
}
+ (BOOL)nEncryptusedes:(NSInteger)Shopping {
    return Shopping % 11 == 0;
}

@end
