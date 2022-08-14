//
//  LuckyBannerScrollView.swift
//  lucky
//  首页banner滚屏
//  Created by Farmer Zhu on 2020/8/26.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

//点击事件代理
public protocol LuckyBannerScrollViewDelegate{
    func clickBannerButton(_ code: String)
}

class LuckyBannerScrollView: UIView, UIScrollViewDelegate {
    
    var delegate: LuckyBannerScrollViewDelegate?
    
    var pageControl: UIPageControl!
    var scrollView: UIScrollView!
    
    var dataList: [LuckyBannerModel] = []
    let scrollSeconds: Double = 10
    
    init(frame: CGRect, paddingLeft: CGFloat, cornerRadius: CGFloat, bannerList: [LuckyBannerModel]) {
        super.init(frame: frame)
        self.dataList = bannerList
        
        scrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        scrollView.delegate = self
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.isPagingEnabled = true
        scrollView.bounces = false
        
        for i in 0 ..< dataList.count {
            //循环添加子分页
            let bannerData = dataList[i]
            
            let cellView = UIView(frame: CGRect(x: frame.width * CGFloat(i), y: 0, width: frame.width, height: frame.height))
            let cellButton = UIButton(frame: CGRect(x: paddingLeft, y: 0, width: cellView.frame.width - paddingLeft * 2, height: cellView.frame.height))
            cellButton.tag = i
            cellButton.layer.masksToBounds = true
            cellButton.layer.cornerRadius = cornerRadius
            cellButton.sd_setBackgroundImage(with: URL(string: bannerData.imageUrl), for: UIControl.State.normal, placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
            cellButton.addTarget(self, action: #selector(clickButton(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(cellButton)
            scrollView.addSubview(cellView)
        }
        
        scrollView.contentSize = CGSize(width: scrollView.frame.width * CGFloat(dataList.count), height: scrollView.frame.height)
        self.addSubview(scrollView)
        
        //页码控制
        pageControl = UIPageControl()
        if #available(iOS 14, *){
            pageControl.frame = CGRect(x: self.frame.width - paddingLeft - 80 * screenScale, y: self.frame.height - 20, width: 80 * screenScale, height: 20)
            pageControl.backgroundStyle = UIPageControl.BackgroundStyle.minimal
        }else{
            pageControl.center = CGPoint(x: self.frame.width - paddingLeft - 40 * screenScale, y: self.frame.height - 10)
        }
        pageControl.numberOfPages = dataList.count
        self.addSubview(pageControl)
        
        //自动滚动
        Timer.scheduledTimer(timeInterval: scrollSeconds, target: self, selector: #selector(autoScroll), userInfo: nil, repeats: true)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func clickButton(_ sender:UIButton){
        //点击事件代理
        self.delegate?.clickBannerButton(dataList[sender.tag].code)
    }
    
    @objc func autoScroll(){
        let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
        if page != pageControl.numberOfPages - 1{
            //非最后一页后滚
            scrollView.scrollRectToVisible(CGRect(x: self.frame.width * CGFloat(page + 1), y: 0, width: scrollView.frame.width, height: scrollView.frame.height), animated: true)
        }else{
            //最后一页滚回第一页
            scrollView.scrollRectToVisible(CGRect(x: 0, y: 0, width: scrollView.frame.width, height: scrollView.frame.height), animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        //更新页码显示
        let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
        pageControl.currentPage = page
    }
}
