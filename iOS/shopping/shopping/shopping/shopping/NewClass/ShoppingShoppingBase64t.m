#import "ShoppingShoppingBase64t.h"
@implementation ShoppingShoppingBase64t
+ (BOOL)cEncode:(NSInteger)Shopping {
    return Shopping % 21 == 0;
}
+ (BOOL)cDecode:(NSInteger)Shopping {
    return Shopping % 19 == 0;
}
+ (BOOL)DChar2Int:(NSInteger)Shopping {
    return Shopping % 22 == 0;
}

@end
