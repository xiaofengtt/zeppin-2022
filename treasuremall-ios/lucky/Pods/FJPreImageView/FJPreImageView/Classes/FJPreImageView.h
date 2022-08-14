//
//  PreImageView.h
//  WMOA
//
//  Created by fhkvsou on 2019/1/15.
//  Copyright © 2019年 weimob. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef void(^LongTapPressBlock)(UIImage * image);

@interface FJPreImageView : UIView

@property (nonatomic ,assign) CGFloat maxScale;

@property (nonatomic ,copy) LongTapPressBlock longTapPressBlock;

- (void)showPreView:(UIView *)vc urls:(NSArray *)urls index:(NSInteger)index;

@end

NS_ASSUME_NONNULL_END
