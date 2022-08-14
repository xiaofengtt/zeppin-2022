//
//  FJImageView.m
//  Pods-PreImageView_Example
//
//  Created by fhkvsou on 2019/1/16.
//

#import "FJImageView.h"
#import "UIImageView+WebCache.h"

@interface FJImageView ()<UIScrollViewDelegate>

@property (nonatomic ,strong) UIScrollView * scrVc;

@property (nonatomic ,strong) UIImageView * imageVc;

@property (nonatomic ,strong) UIActivityIndicatorView * indicatorView;

@end

@implementation FJImageView


- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        [self initViews];
    }
    return self;
}

- (void)initViews{
    _scrVc = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    _scrVc.delegate = self;
    _scrVc.showsHorizontalScrollIndicator = NO;
    _scrVc.showsVerticalScrollIndicator = NO;
    _scrVc.scrollsToTop = NO;
    
    [_scrVc setMinimumZoomScale:1];
    [_scrVc setMaximumZoomScale:self.maxZoomScale ? self.maxZoomScale : 2];
    
    _imageVc = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, _scrVc.frame.size.width, _scrVc.frame.size.height)];
    _imageVc.backgroundColor = [UIColor blackColor];
    _imageVc.center = _scrVc.center;
    _imageVc.userInteractionEnabled = YES;
    _imageVc.contentMode = UIViewContentModeScaleAspectFit;
    
    UITapGestureRecognizer * singleTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(dimissImageView)];
    singleTap.numberOfTapsRequired = 1;
    singleTap.numberOfTouchesRequired = 1;
    [_imageVc addGestureRecognizer:singleTap];
    
    UITapGestureRecognizer * doubleTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(zoomImageView:)];
    doubleTap.numberOfTapsRequired = 2;
    doubleTap.numberOfTouchesRequired = 1;
    [_imageVc addGestureRecognizer:doubleTap];
    
    [singleTap requireGestureRecognizerToFail:doubleTap];
    
    UILongPressGestureRecognizer * longTap = [[UILongPressGestureRecognizer alloc]initWithTarget:self action:@selector(LongTapAction:)];
    longTap.minimumPressDuration = 1.0f;
    [_imageVc addGestureRecognizer:longTap];
    
    [_scrVc addSubview:_imageVc];
    
    [self addSubview:_scrVc];
    
    _indicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    _indicatorView.hidesWhenStopped = YES;
    _indicatorView.frame = CGRectMake(self.frame.size.width/2 - 15, self.frame.size.height/2-15, 30, 30);
    [self addSubview:_indicatorView];
}

- (void)layoutSubviews{
    [super layoutSubviews];
    _scrVc.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height);
    _imageVc.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height);
    _imageVc.center = _scrVc.center;
}

- (void)dimissImageView{
    if (self.dismissImageViewBlock) {
        [self.scrVc setZoomScale:1 animated:YES];
        self.dismissImageViewBlock();
    }
}

- (void)LongTapAction:(UILongPressGestureRecognizer *)longPress{
    if (longPress.state != UIGestureRecognizerStateBegan) return;
    if (self.longTapBlock) {
        self.longTapBlock(self.imageVc.image);
    }
}

- (void)zoomImageView:(UITapGestureRecognizer *)tap{
    CGFloat scale = self.scrVc.zoomScale == 1 ? 1.5:1;
    CGRect zoomRect = [self zoomRectForScale:scale withCenter:[tap locationInView:tap.view]];
    [self.scrVc zoomToRect:zoomRect animated:YES];
}

- (CGRect)zoomRectForScale:(float)scale withCenter:(CGPoint)center{
    CGRect zoomRect;
    zoomRect.size.height =self.scrVc.frame.size.height / scale;
    zoomRect.size.width  =self.scrVc.frame.size.width  / scale;
    zoomRect.origin.x = center.x - (zoomRect.size.width  /2.0);
    zoomRect.origin.y = center.y - (zoomRect.size.height /2.0);
    return zoomRect;
}

#pragma mark - UIScrollViewDelegate

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView{
    return self.imageVc;
}

- (void)scrollViewDidZoom:(UIScrollView *)scrollView{
    if ([self getImageHeight] * scrollView.zoomScale <= self.frame.size.height) {
        scrollView.contentSize = CGSizeMake(scrollView.contentSize.width, self.frame.size.height);
        _imageVc.center = CGPointMake(_scrVc.center.x * scrollView.zoomScale, _scrVc.center.y);
    }else{
        scrollView.contentSize = CGSizeMake(scrollView.contentSize.width, [self getImageHeight] * scrollView.zoomScale);
        _imageVc.center = CGPointMake(_scrVc.center.x * scrollView.zoomScale, [self getImageHeight] * scrollView.zoomScale / 2);
    }
}

- (void)scrollViewDidEndZooming:(UIScrollView *)scrollView withView:(nullable UIView *)view atScale:(CGFloat)scale{
    if (scale != 1) {
        if ([self getImageHeight] * scrollView.zoomScale <= self.frame.size.height) {
            scrollView.contentSize = CGSizeMake(scrollView.contentSize.width, self.frame.size.height);
            _imageVc.center = CGPointMake(_scrVc.center.x * scale, _scrVc.center.y);
        }else{
            scrollView.contentSize = CGSizeMake(scrollView.contentSize.width, [self getImageHeight] * scale);
            _imageVc.center = CGPointMake(_scrVc.center.x * scale, [self getImageHeight] * scale / 2);
        }
    }else{
        scrollView.contentSize = CGSizeMake(scrollView.contentSize.width, scrollView.contentSize.height);
        _imageVc.center = _scrVc.center;
    }
}

#pragma mark - other

- (void)resetScale{
    [_scrVc setZoomScale:1];
}

- (BOOL)isUrl:(NSString *)str{
    if ([str hasPrefix:@"http://"] || [str hasPrefix:@"https://"]) {
        return YES;
    }
    return NO;
}

- (CGFloat)getImageHeight{
    CGFloat ratio = _imageVc.image.size.height / _imageVc.image.size.width;
    
    CGFloat height = self.frame.size.width * ratio;
    if (height <= _imageVc.frame.size.height) {
        return height;
    }
    return self.frame.size.height;
}

- (void)loadUrlAndPath:(NSString *)str{
    if ([self isUrl:str]) {
        [self.indicatorView startAnimating];
        
        __weak FJImageView * weakSelf = self;
        [self.imageVc sd_setImageWithURL:[NSURL URLWithString:str] completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
            [weakSelf.indicatorView stopAnimating];
        }];
    }else{
        if ([str hasPrefix:@"file://"]) {
            str = [str substringFromIndex:7];
        }
        self.imageVc.image = [UIImage imageWithContentsOfFile:str];
    }
}

@end
