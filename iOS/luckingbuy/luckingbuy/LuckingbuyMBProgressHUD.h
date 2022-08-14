#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <CoreGraphics/CoreGraphics.h>
@class MBBackgroundView;
@protocol MBProgressHUDDelegate;
extern CGFloat const MBProgressMaxOffset;
typedef NS_ENUM(NSInteger, MBProgressHUDMode) {
    MBProgressHUDModeIndeterminate,
    MBProgressHUDModeDeterminate,
    MBProgressHUDModeDeterminateHorizontalBar,
    MBProgressHUDModeAnnularDeterminate,
    MBProgressHUDModeCustomView,
    MBProgressHUDModeText
};
typedef NS_ENUM(NSInteger, MBProgressHUDAnimation) {
    MBProgressHUDAnimationFade,
    MBProgressHUDAnimationZoom,
    MBProgressHUDAnimationZoomOut,
    MBProgressHUDAnimationZoomIn
};
typedef NS_ENUM(NSInteger, MBProgressHUDBackgroundStyle) {
    MBProgressHUDBackgroundStyleSolidColor,
    MBProgressHUDBackgroundStyleBlur
};
typedef void (^MBProgressHUDCompletionBlock)(void);
NS_ASSUME_NONNULL_BEGIN
@interface LuckingbuyMBProgressHUD : UIView
+ (instancetype)showHUDAddedTo:(UIView *)view animated:(BOOL)animated;
+ (BOOL)hideHUDForView:(UIView *)view animated:(BOOL)animated;
+ (nullable LuckingbuyMBProgressHUD *)HUDForView:(UIView *)view;
- (instancetype)initWithView:(UIView *)view;
- (void)showAnimated:(BOOL)animated;
- (void)hideAnimated:(BOOL)animated;
- (void)hideAnimated:(BOOL)animated afterDelay:(NSTimeInterval)delay;
@property (weak, nonatomic) id<MBProgressHUDDelegate> delegate;
@property (copy, nullable) MBProgressHUDCompletionBlock completionBlock;
@property (assign, nonatomic) NSTimeInterval graceTime;
@property (assign, nonatomic) NSTimeInterval minShowTime;
@property (assign, nonatomic) BOOL removeFromSuperViewOnHide;
@property (assign, nonatomic) MBProgressHUDMode mode;
@property (strong, nonatomic, nullable) UIColor *contentColor UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic) MBProgressHUDAnimation animationType UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic) CGPoint offset UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic) CGFloat margin UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic) CGSize minSize UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic, getter = isSquare) BOOL square UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic, getter=areDefaultMotionEffectsEnabled) BOOL defaultMotionEffectsEnabled UI_APPEARANCE_SELECTOR;
@property (assign, nonatomic) float progress;
@property (strong, nonatomic, nullable) NSProgress *progressObject;
@property (strong, nonatomic, readonly) MBBackgroundView *bezelView;
@property (strong, nonatomic, readonly) MBBackgroundView *backgroundView;
@property (strong, nonatomic, nullable) UIView *customView;
@property (strong, nonatomic, readonly) UILabel *label;
@property (strong, nonatomic, readonly) UILabel *detailsLabel;
@property (strong, nonatomic, readonly) UIButton *button;
@end
@protocol MBProgressHUDDelegate <NSObject>
@optional
- (void)hudWasHidden:(LuckingbuyMBProgressHUD *)hud;
@end
@interface MBRoundProgressView : UIView
@property (nonatomic, assign) float progress;
@property (nonatomic, strong) UIColor *progressTintColor;
@property (nonatomic, strong) UIColor *backgroundTintColor;
@property (nonatomic, assign, getter = isAnnular) BOOL annular;
@end
@interface MBBarProgressView : UIView
@property (nonatomic, assign) float progress;
@property (nonatomic, strong) UIColor *lineColor;
@property (nonatomic, strong) UIColor *progressRemainingColor;
@property (nonatomic, strong) UIColor *progressColor;
@end
@interface MBBackgroundView : UIView
@property (nonatomic) MBProgressHUDBackgroundStyle style;
#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 80000 || TARGET_OS_TV
@property (nonatomic) UIBlurEffectStyle blurEffectStyle;
#endif
@property (nonatomic, strong) UIColor *color;
@end
NS_ASSUME_NONNULL_END
