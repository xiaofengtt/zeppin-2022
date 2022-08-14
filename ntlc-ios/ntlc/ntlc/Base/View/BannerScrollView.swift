//
//  BannerScrollView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/21.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

public protocol BannerScrollViewDelegate{
    func pushView(_ url:String)
}

class BannerScrollView: UIView, UIScrollViewDelegate{
    
    var buttonDelegate: BannerScrollViewDelegate?
    
    var pageControl: UIPageControl!
    var bannerScrollView: UIScrollView!
    var datas: Array<BannerDataModel>!
    var urlDic = NSMutableDictionary()
    
    let scrollSeconds: Double = 5
    
    
    init(frame: CGRect, datas: Array<BannerDataModel>) {
        super.init(frame: frame)
        self.datas = datas
        self.backgroundColor = UIColor.mainBlue()
        
        bannerScrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        bannerScrollView.delegate = self
        bannerScrollView.showsVerticalScrollIndicator = false
        bannerScrollView.showsHorizontalScrollIndicator = false
        bannerScrollView.isPagingEnabled = true
        bannerScrollView.bounces = false
        let dataCount = datas.count
        for i in 0 ..< dataCount {
//            let data = datas[i]
            
            let childView = UIView(frame: CGRect(origin: CGPoint(x: bannerScrollView.frame.width * CGFloat(i),y: 0), size: bannerScrollView.frame.size))
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: childView.frame.size))
            
//            SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + data.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
//                if result{
//                    background.image = SDImage
//                }
//            }
            
            if(isIphoneX()){
                if(i == 0){
                    background.image = UIImage(named: "product_list_banner_x")
                }else if(i == 1){
                    background.image = UIImage(named: "product_list_banner2_x")
                }else{
                    background.image = UIImage(named: "product_list_banner3_x")
                }
            }else{
                if(i == 0){
                    background.image = UIImage(named: "product_list_banner")
                }else if(i == 1){
                    background.image = UIImage(named: "product_list_banner2")
                }else{
                    background.image = UIImage(named: "product_list_banner3")
                }
            }
            
            childView.addSubview(background)
            
            let button = UIButton(frame: background.frame)
            button.tag = TagController.bannerTags.button + i
            urlDic.setValue("", forKey: "\(button.tag)")
            button.addTarget(self, action: #selector(clickDataButton(_:)), for: UIControlEvents.touchUpInside)
            childView.addSubview(button)
            bannerScrollView.addSubview(childView)
        }
        
        bannerScrollView.contentSize = CGSize(width: bannerScrollView.frame.width * CGFloat(dataCount), height: bannerScrollView.frame.height)
        self.addSubview(bannerScrollView)
        
        pageControl = UIPageControl()
        pageControl.center = CGPoint(x: self.frame.width/2, y: self.frame.height - 42 * screenScale)
        pageControl.numberOfPages = dataCount
        self.addSubview(pageControl)
        Timer.scheduledTimer(timeInterval: scrollSeconds, target: self, selector: #selector(autoScroll), userInfo: nil, repeats: true)
    }
    
    init(){
        super.init(frame: CGRect.zero)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func clickDataButton(_ sender:UIButton){
        let url = urlDic.object(forKey: "\(sender.tag)") as! String
        if(self.buttonDelegate != nil){
            self.buttonDelegate!.pushView(url)
        }
    }
    
    @objc func autoScroll(){
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

class BannerDataModel : NSObject{
    var imageUrl: String
    var action: String
    
    override init() {
        self.imageUrl = ""
        self.action = ""
    }
    
    init(imageUrl: String, action: String) {
        self.imageUrl = imageUrl
        self.action = action
    }
}
