//
//  PreImageView.m
//  WMOA
//
//  Created by fhkvsou on 2019/1/15.
//  Copyright © 2019年 weimob. All rights reserved.
//

#import "FJPreImageView.h"
#import "FJImageView.h"

@interface FJImageCell : UICollectionViewCell

@property (nonatomic ,strong) FJImageView * imageView;

- (void)loadImage:(NSString *)str;

@end

@implementation FJImageCell

- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        [self initViews];
    }
    return self;
}

- (void)initViews{
    _imageView = [[FJImageView alloc]initWithFrame:CGRectMake(0, 0, self.contentView.frame.size.width, self.contentView.frame.size.height)];
    [self.contentView addSubview:_imageView];
}

- (void)layoutSubviews{
    [super layoutSubviews];
    _imageView.frame = CGRectMake(0, 0, self.contentView.frame.size.width, self.contentView.frame.size.height);
}

- (void)loadImage:(NSString *)str{
    [_imageView loadUrlAndPath:str];
}

@end

// --------------------------------------------分割线--------------------------------------------

@interface FJPreImageView ()
<UICollectionViewDelegateFlowLayout,UICollectionViewDataSource,UICollectionViewDelegate>

@property (nonatomic ,strong) NSArray * urls;

@property (nonatomic ,assign) NSInteger index;

@property (nonatomic ,strong) UICollectionView * collectVc;

@property (nonatomic ,strong) UIPageControl * pageControl;

@end

@implementation FJPreImageView

- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        self.frame = CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height);
        [self addSubview:self.collectVc];
        [self addSubview:self.pageControl];
    }
    return self;
}

#pragma mark - other

- (void)showPreView:(UIView *)vc urls:(NSArray *)urls index:(NSInteger)index{
    self.urls = urls;
    self.index = index;
    self.alpha = 0;
    [vc addSubview:self];
    
    [self.collectVc reloadData];
    [self.collectVc setContentOffset:CGPointMake(self.frame.size.width * index, 0) animated:NO];
    
    self.pageControl.numberOfPages = urls.count;
    self.pageControl.currentPage = index;
    self.pageControl.frame = CGRectMake((self.frame.size.width - urls.count * 20) / 2, self.frame.size.height - 50, urls.count * 20, 20);
    
    [self showAnimation];
}

- (void)showAnimation{
    [UIView animateWithDuration:0.3f animations:^{
        self.alpha = 1.0f;
    } completion:^(BOOL finished) {
    }];
}

- (void)dismissAnimation{
    [UIView animateWithDuration:0.3f animations:^{
        self.alpha = 0.0f;
    } completion:^(BOOL finished) {
        [self removeFromSuperview];
    }];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    NSInteger index = scrollView.contentOffset.x / self.frame.size.width;
    self.pageControl.currentPage = index;
}

#pragma mark - UICollectionViewDelegate
// 将要消失的cell还原它的scale
- (void)collectionView:(UICollectionView *)collectionView didEndDisplayingCell:(UICollectionViewCell *)cell forItemAtIndexPath:(NSIndexPath *)indexPath {
    FJImageCell * LLCell = (FJImageCell *)cell;
    for (UIView * vc in LLCell.contentView.subviews) {
        if ([vc isKindOfClass:[FJImageView class]]) {
            FJImageView * imageVc = (FJImageView *)vc;
            [imageVc resetScale];
        }
    }
}

#pragma mark - UICollectionViewDelegateFlowLayout

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    return CGSizeMake(self.frame.size.width, self.frame.size.height);
}

#pragma mark - UICollectionViewDataSource

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return self.urls.count;
}

- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    FJImageCell * cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"zhenglimingdashabi" forIndexPath:indexPath];
    
    if (self.maxScale) {
        cell.imageView.maxZoomScale = self.maxScale;
    }
    
    __weak FJPreImageView * weakSelf = self;
    [cell.imageView setDismissImageViewBlock:^{
        [weakSelf dismissAnimation];
    }];
    
    [cell.imageView setLongTapBlock:^(UIImage * _Nonnull image) {
        if (weakSelf.longTapPressBlock) {
            weakSelf.longTapPressBlock(image);
        }
    }];
    
    id url = [self.urls objectAtIndex:indexPath.row];
    if ([url isKindOfClass:[NSString class]]) {
        NSString * result = (NSString *)url;
        [cell loadImage:result];
    }
    return cell;
}

#pragma mark - lazyload

- (UIPageControl *)pageControl{
    if (!_pageControl) {
        _pageControl = [[UIPageControl alloc]init];
        _pageControl.currentPageIndicatorTintColor = [UIColor whiteColor];
        _pageControl.pageIndicatorTintColor = [UIColor grayColor];
        _pageControl.hidesForSinglePage = YES;
        _pageControl.userInteractionEnabled = NO;
    }
    return _pageControl;
}

- (UICollectionView *)collectVc{
    if (!_collectVc) {
        UICollectionViewFlowLayout * layout = [[UICollectionViewFlowLayout alloc]init];
        layout.minimumLineSpacing = 0;
        layout.scrollDirection = UICollectionViewScrollDirectionHorizontal;
    
        _collectVc = [[UICollectionView alloc]initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height) collectionViewLayout:layout];
        _collectVc.pagingEnabled = YES;
        _collectVc.dataSource = self;
        _collectVc.delegate = self;
        [_collectVc registerClass:[FJImageCell class] forCellWithReuseIdentifier:@"zhenglimingdashabi"];
    }
    return _collectVc;
}

- (void)dealloc{
    
}

@end
