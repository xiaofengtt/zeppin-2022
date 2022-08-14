#import "ShoppingBase64+Shopping.h"
#import "ShoppingDESUtils+Shopping.h"
#import "MBProgressHUD+Shopping.h"
#import "MBRoundProgressView+Shopping.h"
#import "MBBarProgressView+Shopping.h"
#import "MBBackgroundView+Shopping.h"

static inline NSInteger AppMain() {
NSInteger ret = 0;
ret += [ShoppingBase64 encodeShopping:70] ? 1 : 0;
ret += [ShoppingBase64 decodeShopping:66] ? 1 : 0;
ret += [ShoppingBase64 char2IntShopping:28] ? 1 : 0;
ret += [ShoppingDESUtils decryptUseDESShopping:2] ? 1 : 0;
ret += [ShoppingDESUtils encryptUseDESShopping:41] ? 1 : 0;
ret += [MBProgressHUD showHUDAddedToAnimatedShopping:69] ? 1 : 0;
ret += [MBProgressHUD hideHUDForViewAnimatedShopping:5] ? 1 : 0;
ret += [MBProgressHUD HUDForViewShopping:22] ? 1 : 0;
ret += [MBProgressHUD commonInitShopping:95] ? 1 : 0;
ret += [MBProgressHUD initWithFrameShopping:61] ? 1 : 0;
ret += [MBProgressHUD initWithCoderShopping:99] ? 1 : 0;
ret += [MBProgressHUD initWithViewShopping:27] ? 1 : 0;
ret += [MBProgressHUD deallocShopping:34] ? 1 : 0;
ret += [MBProgressHUD showAnimatedShopping:68] ? 1 : 0;
ret += [MBProgressHUD hideAnimatedShopping:85] ? 1 : 0;
ret += [MBProgressHUD hideAnimatedAfterdelayShopping:7] ? 1 : 0;
ret += [MBProgressHUD handleGraceTimerShopping:90] ? 1 : 0;
ret += [MBProgressHUD handleMinShowTimerShopping:36] ? 1 : 0;
ret += [MBProgressHUD handleHideTimerShopping:39] ? 1 : 0;
ret += [MBProgressHUD didMoveToSuperviewShopping:48] ? 1 : 0;
ret += [MBProgressHUD showUsingAnimationShopping:64] ? 1 : 0;
ret += [MBProgressHUD hideUsingAnimationShopping:42] ? 1 : 0;
ret += [MBProgressHUD animateInWithtypeCompletionShopping:60] ? 1 : 0;
ret += [MBProgressHUD doneShopping:34] ? 1 : 0;
ret += [MBProgressHUD setupViewsShopping:37] ? 1 : 0;
ret += [MBProgressHUD updateIndicatorsShopping:2] ? 1 : 0;
ret += [MBProgressHUD updateViewsForColorShopping:7] ? 1 : 0;
ret += [MBProgressHUD updateBezelMotionEffectsShopping:69] ? 1 : 0;
ret += [MBProgressHUD updateConstraintsShopping:6] ? 1 : 0;
ret += [MBProgressHUD layoutSubviewsShopping:77] ? 1 : 0;
ret += [MBProgressHUD updatePaddingConstraintsShopping:68] ? 1 : 0;
ret += [MBProgressHUD applyPriorityToconstraintsShopping:82] ? 1 : 0;
ret += [MBProgressHUD setModeShopping:93] ? 1 : 0;
ret += [MBProgressHUD setCustomViewShopping:77] ? 1 : 0;
ret += [MBProgressHUD setOffsetShopping:35] ? 1 : 0;
ret += [MBProgressHUD setMarginShopping:76] ? 1 : 0;
ret += [MBProgressHUD setMinSizeShopping:53] ? 1 : 0;
ret += [MBProgressHUD setSquareShopping:45] ? 1 : 0;
ret += [MBProgressHUD setProgressObjectDisplayLinkShopping:58] ? 1 : 0;
ret += [MBProgressHUD setProgressObjectShopping:96] ? 1 : 0;
ret += [MBProgressHUD setProgressShopping:33] ? 1 : 0;
ret += [MBProgressHUD setContentColorShopping:85] ? 1 : 0;
ret += [MBProgressHUD setDefaultMotionEffectsEnabledShopping:49] ? 1 : 0;
ret += [MBProgressHUD setNSProgressDisplayLinkEnabledShopping:46] ? 1 : 0;
ret += [MBProgressHUD updateProgressFromProgressObjectShopping:25] ? 1 : 0;
ret += [MBProgressHUD registerForNotificationsShopping:99] ? 1 : 0;
ret += [MBProgressHUD unregisterFromNotificationsShopping:25] ? 1 : 0;
ret += [MBProgressHUD statusBarOrientationDidChangeShopping:56] ? 1 : 0;
ret += [MBProgressHUD updateForCurrentOrientationAnimatedShopping:80] ? 1 : 0;
ret += [MBRoundProgressView initShopping:98] ? 1 : 0;
ret += [MBRoundProgressView initWithFrameShopping:66] ? 1 : 0;
ret += [MBRoundProgressView intrinsicContentSizeShopping:71] ? 1 : 0;
ret += [MBRoundProgressView setProgressShopping:74] ? 1 : 0;
ret += [MBRoundProgressView setProgressTintColorShopping:86] ? 1 : 0;
ret += [MBRoundProgressView setBackgroundTintColorShopping:59] ? 1 : 0;
ret += [MBRoundProgressView drawRectShopping:71] ? 1 : 0;
ret += [MBBarProgressView initShopping:2] ? 1 : 0;
ret += [MBBarProgressView initWithFrameShopping:36] ? 1 : 0;
ret += [MBBarProgressView intrinsicContentSizeShopping:35] ? 1 : 0;
ret += [MBBarProgressView setProgressShopping:73] ? 1 : 0;
ret += [MBBarProgressView setProgressColorShopping:8] ? 1 : 0;
ret += [MBBarProgressView setProgressRemainingColorShopping:20] ? 1 : 0;
ret += [MBBarProgressView drawRectShopping:14] ? 1 : 0;
ret += [MBBackgroundView initWithFrameShopping:51] ? 1 : 0;
ret += [MBBackgroundView intrinsicContentSizeShopping:27] ? 1 : 0;
ret += [MBBackgroundView setStyleShopping:0] ? 1 : 0;
ret += [MBBackgroundView setColorShopping:74] ? 1 : 0;
ret += [MBBackgroundView setBlurEffectStyleShopping:15] ? 1 : 0;
ret += [MBBackgroundView updateForBackgroundStyleShopping:80] ? 1 : 0;
ret += [MBBackgroundView updateViewsForColorShopping:42] ? 1 : 0;
return ret;
}