//
//  FJImageView.h
//  Pods-PreImageView_Example
//
//  Created by fhkvsou on 2019/1/16.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef void(^DismissImageViewBlock)(void);

typedef void(^LongTapBlock)(UIImage * image);

@interface FJImageView : UIView

@property (nonatomic ,assign) CGFloat maxZoomScale;

@property (nonatomic ,copy) DismissImageViewBlock dismissImageViewBlock;

@property (nonatomic ,copy) LongTapBlock longTapBlock;

- (void)resetScale;

- (void)loadUrlAndPath:(NSString *)str;

@end

NS_ASSUME_NONNULL_END
