//
//  BusinessBannerView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/9.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

public protocol BusinessButtonViewDelegate{
    func pushWebView(_ url: String, title: String)
}

class BusinessBannerView: UIView, UIScrollViewDelegate{
    
    var buttonDelegate: BusinessButtonViewDelegate?
    
    var pageControl: UIPageControl!
    var bannerScrollView: UIScrollView!
    var module: ModuleModel!
    var urlDic = NSMutableDictionary()
    
    let scrollSeconds: Double = 10
    
    
    init(frame: CGRect, module: ModuleModel) {
        super.init(frame: frame)
        self.module = module
        self.backgroundColor = UIColor.white
        
        bannerScrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        bannerScrollView.delegate = self
        bannerScrollView.showsVerticalScrollIndicator = false
        bannerScrollView.showsHorizontalScrollIndicator = false
        bannerScrollView.isPagingEnabled = true
        bannerScrollView.bounces = false
        var dataCount = module.dataList.count
        if(dataCount > module.count){
            dataCount = module.count
        }
        for i in 0 ..< dataCount {
            let moduleData = module.dataList[i]
            
            let moduleDataView = UIView(frame: CGRect(origin: CGPoint(x: bannerScrollView.frame.width * CGFloat(i),y: 0), size: bannerScrollView.frame.size))
            let moduleDataImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: moduleDataView.frame.size))
            SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + moduleData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                if result{
                    moduleDataImageView.image = SDImage
                }
            }
            moduleDataView.addSubview(moduleDataImageView)
            
            let moduleDataButton = UIButton(frame: moduleDataImageView.frame)
            moduleDataButton.tag = TagController.businessTags().module1Tag + i
            urlDic.setValue([moduleData.url, moduleData.title], forKey: "\(moduleDataButton.tag)")
            moduleDataButton.addTarget(self, action: #selector(clickDataButton(_:)), for: UIControlEvents.touchUpInside)
            moduleDataView.addSubview(moduleDataButton)
            bannerScrollView.addSubview(moduleDataView)
        }
        
        bannerScrollView.contentSize = CGSize(width: bannerScrollView.frame.width * CGFloat(dataCount), height: bannerScrollView.frame.height)
        self.addSubview(bannerScrollView)
        
        pageControl = UIPageControl()
        pageControl.center = CGPoint(x: self.frame.width/2, y: self.frame.height - 20)
        pageControl.numberOfPages = dataCount
        self.addSubview(pageControl)
        Timer.scheduledTimer(timeInterval: scrollSeconds, target: self, selector: #selector(autoScroll), userInfo: nil, repeats: true)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func clickDataButton(_ sender:UIButton){
        let data = urlDic.object(forKey: "\(sender.tag)") as! [String]
        self.buttonDelegate!.pushWebView(data[0], title: data[1])
    }
    
    func autoScroll(){
        let page = Int(floor((bannerScrollView.contentOffset.x * 2.0 + self.frame.width) / (self.frame.width * 2.0)))
        if page != pageControl.numberOfPages - 1{
            bannerScrollView.scrollRectToVisible(CGRect(x: self.frame.width * CGFloat(page + 1), y: 0, width: bannerScrollView.frame.width, height: bannerScrollView.frame.height), animated: true)
        }else{
            bannerScrollView.scrollRectToVisible(CGRect(x: 0, y: 0, width: bannerScrollView.frame.width, height: bannerScrollView.frame.height), animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let page = Int(floor((scrollView.contentOffset.x * 2.0 + self.frame.width) / (self.frame.width * 2.0)))
        pageControl.currentPage = page
    }
}
