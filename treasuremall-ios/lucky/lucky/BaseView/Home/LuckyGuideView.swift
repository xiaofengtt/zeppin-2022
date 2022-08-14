//
//  LuckyGuideView.swift
//  lucky
//  启动引导页
//  Created by Farmer Zhu on 2021/1/13.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyGuideView: UIView, UIScrollViewDelegate {
    
    var pageControl: UIPageControl!
    var scrollView: UIScrollView!
    var skipButton: UIButton!
    
    var dataList: [LuckyBannerModel] = []
    
    init(dataList: [LuckyBannerModel]) {
        self.dataList = dataList
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        self.backgroundColor = UIColor.white
        self.layer.zPosition = 1
        
        //滑动区
        scrollView = UIScrollView(frame: self.bounds)
        scrollView.delegate = self
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.isPagingEnabled = true
        scrollView.bounces = false
        scrollView.contentSize = CGSize(width: self.frame.width * CGFloat(dataList.count), height: self.frame.height)
        self.addSubview(scrollView)
        
        //跳过按钮
        skipButton = UIButton(frame: CGRect(x: self.frame.width - 72 * screenScale, y: 20 * screenScale + statusBarHeight, width: 56 * screenScale, height: 24 * screenScale))
        skipButton.layer.borderWidth = 1 * screenScale
        skipButton.layer.borderColor = UIColor.mainYellow().cgColor
        skipButton.layer.cornerRadius = skipButton.frame.height/2
        skipButton.setTitle(NSLocalizedString("Skip", comment: ""), for: UIControl.State.normal)
        skipButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        skipButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        skipButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        self.addSubview(skipButton)
        
        //页码显示
        pageControl = UIPageControl()
        if #available(iOS 14, *){
            pageControl.frame = CGRect(x: 0, y: self.frame.height - bottomSafeHeight - 40 * screenScale, width: self.frame.width, height: 20 * screenScale)
            pageControl.backgroundStyle = UIPageControl.BackgroundStyle.minimal
        }else{
            pageControl.center = CGPoint(x: self.frame.width/2, y: self.frame.height - bottomSafeHeight - 30 * screenScale)
        }
        pageControl.numberOfPages = dataList.count
        pageControl.pageIndicatorTintColor = UIColor.backgroundGray()
        pageControl.currentPageIndicatorTintColor = UIColor.mainYellow()
        self.addSubview(pageControl)
        
        for i in 0 ..< dataList.count{
            //循环加载子视图
            let childView = UIView(frame: CGRect(origin: CGPoint(x: self.frame.width * CGFloat(i), y: 0), size: self.frame.size))
            
            let imageHeight = screenWidth * 484 / 375
            let imageView = UIImageView(frame: CGRect(x: 0, y: statusBarHeight + (pageControl.frame.origin.y - statusBarHeight - imageHeight) / 2, width: screenWidth, height: imageHeight))
            imageView.sd_setImage(with: URL(string: dataList[i].imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            childView.addSubview(imageView)
            
            if(i >= dataList.count - 1){
                //最后一页 引导结束按钮
                let closeButton = UIButton(frame: CGRect(x: childView.frame.width/4, y: pageControl.frame.origin.y - 72 * screenScale, width: childView.frame.width/2, height: 48 * screenScale))
                closeButton.layer.cornerRadius = closeButton.frame.height/2
                closeButton.backgroundColor = UIColor.mainYellow()
                closeButton.setTitle("GET STARTED", for: UIControl.State.normal)
                closeButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
                closeButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
                closeButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
                childView.addSubview(closeButton)
            }
            
            scrollView.addSubview(childView)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
        pageControl.currentPage = page
        
        //非最后一页显示skip按钮
        if(page >= dataList.count - 1){
            skipButton.isHidden = true
        }else{
            skipButton.isHidden = false
        }
    }
    
    //关闭
    @objc func close(){
        self.removeFromSuperview()
    }
}
