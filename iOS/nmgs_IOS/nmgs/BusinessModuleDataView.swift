//
//  BusinessModuleDataView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/10.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class BusinessImageModuleDataView : UIView{
    var buttonDelegate : BusinessButtonViewDelegate?
    var data : ModuleDataModel!
    
    init(frame: CGRect, data: ModuleDataModel) {
        super.init(frame: frame)
        self.data = data
        
        let imageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + data.imageUrl),  options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
            if result{
                imageView.image = SDImage
            }
        }
        self.addSubview(imageView)
        
        let dataButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        dataButton.addTarget(self, action: #selector(clickDataButton(_:)), for: UIControlEvents.touchUpInside)
        self.addSubview(dataButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func clickDataButton(_ sender:UIButton){
        let url = data.url
        self.buttonDelegate!.pushWebView(url)
    }
}

class BusinessLandscapeModuleDataView: UIView{
    
    var buttonDelegate : BusinessButtonViewDelegate?
    var data : ModuleDataModel!
    
    var titleLabel : BusinessDataTitleLabel!
    
    let paddingEdge :CGFloat = 10
    let spaceLineWidth : CGFloat = 2
    let borderWidth: CGFloat = 1
    
    init(frame: CGRect, data: ModuleDataModel) {
        super.init(frame: frame)
        self.data = data
        self.backgroundColor = UIColor.white
        self.layer.borderColor = UIColor.backgroundGray().cgColor
        self.layer.borderWidth = borderWidth
        
        self.titleLabel = BusinessDataTitleLabel(text: data.title, textColor: UIColor.black)
        titleLabel.frame = CGRect(x: paddingEdge, y: paddingEdge, width: self.frame.width - paddingEdge*2 - (frame.height - paddingEdge*2), height: titleLabel.frame.height)
        self.addSubview(titleLabel)
        
        let contentLabel = BusinessDataContentLabel(text: data.content)
        contentLabel.frame = CGRect(x: paddingEdge, y: titleLabel.frame.origin.y + titleLabel.frame.height + spaceLineWidth, width: titleLabel.frame.width, height: contentLabel.frame.height)
        self.addSubview(contentLabel)
        
        let imageView = UIImageView()
        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + data.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
            if result{
                if(SDImage?.size.height > 0){
                    let rate = (SDImage?.size.width)!/(SDImage?.size.height)!
                    if(rate < 1){
                        imageView.frame = CGRect(x: self.frame.width - self.paddingEdge - ((self.frame.height - self.paddingEdge*2) * rate), y: self.paddingEdge, width: (self.frame.height - self.paddingEdge*2) * rate, height: self.frame.height - self.paddingEdge*2)
                    }else{
                        imageView.frame = CGRect(x: self.frame.width - self.paddingEdge - (self.frame.height - self.paddingEdge*2), y: self.paddingEdge, width: self.frame.height - self.paddingEdge*2, height: self.frame.height - self.paddingEdge*2)
                    }
                    imageView.image = SDImage
                }
            }
        }
        self.addSubview(imageView)
        
        let dataButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        dataButton.addTarget(self, action: #selector(clickDataButton(_:)), for: UIControlEvents.touchUpInside)
        self.addSubview(dataButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func clickDataButton(_ sender:UIButton){
        let url = data.url
        self.buttonDelegate!.pushWebView(url)
    }
}

class BusinessPortraitModuleDataView: UIView{
    
    var buttonDelegate : BusinessButtonViewDelegate?
    var data : ModuleDataModel!
    
    var titleLabel : BusinessDataTitleLabel!
    
    let paddingEdge :CGFloat = 10
    let spaceLineWidth : CGFloat = 2
    let borderWidth: CGFloat = 1
    
    init(frame: CGRect, data: ModuleDataModel) {
        super.init(frame: frame)
        self.data = data
        self.backgroundColor = UIColor.white
        self.layer.borderColor = UIColor.backgroundGray().cgColor
        self.layer.borderWidth = borderWidth
        
        self.titleLabel = BusinessDataTitleLabel(text: data.title, textColor: UIColor.black)
        titleLabel.frame = CGRect(x: paddingEdge, y: paddingEdge, width: self.frame.width - paddingEdge*2, height: titleLabel.frame.height)
        self.addSubview(titleLabel)
        
        let contentLabel = BusinessDataContentLabel(text: data.content)
        contentLabel.frame = CGRect(x: paddingEdge, y: titleLabel.frame.origin.y + titleLabel.frame.height + spaceLineWidth, width: titleLabel.frame.width, height: contentLabel.frame.height)
        self.addSubview(contentLabel)
        
        let imageView = UIImageView()
        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + data.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
            if result{
                if(SDImage?.size.height > 0){
                    let rate = (SDImage?.size.width)!/(SDImage?.size.height)!
                    let imageHeight = self.frame.height - (contentLabel.frame.origin.y + contentLabel.frame.height) - 2*self.paddingEdge
                    if(rate < 1){
                        imageView.frame = CGRect(x: (self.frame.width - imageHeight * rate)/2, y: contentLabel.frame.origin.y + contentLabel.frame.height + self.paddingEdge, width: imageHeight * rate, height: imageHeight)
                    }else{
                        imageView.frame = CGRect(x: (self.frame.width - imageHeight)/2, y: contentLabel.frame.origin.y + contentLabel.frame.height + self.paddingEdge, width: imageHeight, height: imageHeight)
                    }
                    imageView.image = SDImage
                }
            }
        }
        self.addSubview(imageView)
        
        let dataButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        dataButton.addTarget(self, action: #selector(clickDataButton(_:)), for: UIControlEvents.touchUpInside)
        self.addSubview(dataButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func clickDataButton(_ sender:UIButton){
        let url = data.url
        self.buttonDelegate!.pushWebView(url)
    }
}
