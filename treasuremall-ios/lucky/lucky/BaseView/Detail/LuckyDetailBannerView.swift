//
//  LuckyDetailBannerView.swift
//  lucky
//  商品详情顶部滚动图
//  Created by Farmer Zhu on 2020/9/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailBannerView: UIView, UIScrollViewDelegate{
    
    var scrollView: UIScrollView!
    var pageLabel: UILabel!
    
    var imageList: [String]!
    
    let scrollSeconds: Double = 5
    
    init(frame: CGRect, imageList: [String]) {
        super.init(frame: frame)
        self.imageList = imageList
        
        //滚动区
        scrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        scrollView.delegate = self
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.isPagingEnabled = true
        scrollView.bounces = false
        
        //循环加载图片
        for i in 0 ..< imageList.count {
            let imageView = UIImageView(frame: CGRect(x: frame.width * CGFloat(i), y: 0, width: frame.width, height: frame.height))
            imageView.sd_setImage(with: URL(string: imageList[i]), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
            scrollView.addSubview(imageView)
        }
        scrollView.contentSize = CGSize(width: scrollView.frame.width * CGFloat(imageList.count), height: scrollView.frame.height)
        self.addSubview(scrollView)
        
        //页码显示
        pageLabel = UILabel()
        pageLabel.layer.masksToBounds = true
        pageLabel.layer.cornerRadius = 10 * screenScale
        pageLabel.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        pageLabel.text = "1/\(imageList.count)"
        pageLabel.textColor = UIColor.white
        pageLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        pageLabel.textAlignment = NSTextAlignment.center
        pageLabel.sizeToFit()
        pageLabel.frame = CGRect(x: frame.width - pageLabel.frame.width - 28 * screenScale, y: frame.height - 32 * screenScale, width: pageLabel.frame.width + 18 * screenScale, height: 20 * screenScale)
        self.addSubview(pageLabel)
        
        //自动滚动
        Timer.scheduledTimer(timeInterval: scrollSeconds, target: self, selector: #selector(autoScroll), userInfo: nil, repeats: true)
    }
    
    @objc func autoScroll(){
        let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
        if page != imageList.count - 1{
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
        pageLabel.text = "\(page + 1)/\(imageList.count)"
        pageLabel.sizeToFit()
        pageLabel.frame = CGRect(x: frame.width - pageLabel.frame.width - 28 * screenScale, y: frame.height - 32 * screenScale, width: pageLabel.frame.width + 18 * screenScale, height: 20 * screenScale)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
