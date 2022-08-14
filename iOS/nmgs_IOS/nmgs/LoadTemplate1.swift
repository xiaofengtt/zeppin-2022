//
//  LoadTemplate1.swift
//  nmgs
//
//  Created by zeppin on 2016/11/9.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

extension BusinessViewController{
    func loadTemplate1(){
        let titleViewHeight: CGFloat = 25
        let borderWidth: CGFloat = 1
        let paddingEdge: CGFloat = 10
        let moduleSpace : CGFloat = 10
        let moduleList = Template.template1ModuleList
        
        let module1 = moduleDic.object(forKey: moduleList[0]) as! ModuleModel
        let module1View = BusinessBannerView(frame: CGRect(x: 0,y: 0,width: baseScrollView.frame.width,height: baseScrollView.frame.width/12*5), module: module1)
        module1View.buttonDelegate = self
        baseScrollView.addSubview(module1View)
        
        let module2 = moduleDic.object(forKey: moduleList[1]) as! ModuleModel
        let module2View = UIView(frame: CGRect(x: 0,y: module1View.frame.origin.y + module1View.frame.height + moduleSpace/2, width: baseScrollView.frame.width, height: (baseScrollView.frame.width)/2))
        module2View.backgroundColor =  UIColor.white
        let module2row = 2
        let module2num = 4
        let dataViewWidth = (module2View.frame.width - paddingEdge*2) / CGFloat(module2num)
        for r in 0 ..< module2row{
            for n in 0 ..< module2num{
                if(module2.dataList.count > r * module2num + n){
                    let moduleData = module2.dataList[r * module2num + n]
                    
                    let dataView = UIView(frame: CGRect(origin: CGPoint(x: paddingEdge + dataViewWidth * CGFloat(n),y: dataViewWidth * CGFloat(r)), size: CGSize(width: dataViewWidth, height: dataViewWidth)))
                    let dataImageView = UIImageView(frame: CGRect(x: dataView.frame.width/4, y: dataView.frame.width/6, width: dataView.frame.width/2, height: dataView.frame.width/2))
                    SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + moduleData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                        if result{
                            dataImageView.image = SDImage
                        }
                    }
                    dataView.addSubview(dataImageView)
                
                    let dataLabel = UILabel(frame: CGRect(x: 0, y: dataImageView.frame.origin.y + dataImageView.frame.height + paddingEdge/2, width: dataView.frame.width, height: 20))
                    dataLabel.textColor = UIColor.darkGray
                    dataLabel.font = UIFont.systemFont(ofSize: 12)
                    dataLabel.textAlignment = NSTextAlignment.center
                    dataLabel.text = moduleData.title
                    dataView.addSubview(dataLabel)
                
                    let dataButton = createButton(CGRect(origin: CGPoint.zero, size: dataView.frame.size), tag: TagController.businessTags().module2Tag + module2num * r + n, url: moduleData.url)
                    dataView.addSubview(dataButton)
                    module2View.addSubview(dataView)
                }else{
                    break
                }
            }
        }
        baseScrollView.addSubview(module2View)
        
        let module3 = moduleDic.object(forKey: moduleList[2]) as! ModuleModel
        let module3TitleView = BusinessModuleTitleView(frame: CGRect(x: 0, y: module2View.frame.origin.y + module2View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight),name: module3.name)
        module3TitleView.layer.borderColor = UIColor.backgroundGray().cgColor
        module3TitleView.layer.borderWidth = borderWidth
        baseScrollView.addSubview(module3TitleView)
        
        let module3View = UIView(frame: CGRect(x: 0, y: module3TitleView.frame.origin.y + module3TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/2))
        if module3.dataList.count > 0 {
            let module3LeftData = module3.dataList[0]
            let module3LeftView = UIView(frame: CGRect(x: 0, y: 0, width: module3View.frame.width/8*3, height: module3View.frame.height))
            module3LeftView.layer.borderColor = UIColor.backgroundGray().cgColor
            module3LeftView.layer.borderWidth = borderWidth
            module3LeftView.backgroundColor = UIColor.white
            
            let module3LeftViewTitleLabel = BusinessDataTitleLabel(text: module3LeftData.title, textColor: UIColor.black)
            module3LeftViewTitleLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: paddingEdge), size: CGSize(width:  module3LeftView.frame.width - paddingEdge*2, height: module3LeftViewTitleLabel.frame.height))
            module3LeftView.addSubview(module3LeftViewTitleLabel)
            
            let module3LeftViewContentLabel = BusinessDataContentLabel(text: module3LeftData.content)
            module3LeftViewContentLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: module3LeftViewTitleLabel.frame.origin.y + module3LeftViewTitleLabel.frame.height), size: CGSize(width:  module3LeftView.frame.width - paddingEdge*2, height: module3LeftViewContentLabel.frame.height))
            module3LeftView.addSubview(module3LeftViewContentLabel)
            
            let module3LeftViewImageView = UIImageView(frame: CGRect(x: paddingEdge, y: module3LeftView.frame.height -  paddingEdge - (module3LeftView.frame.width - paddingEdge*2), width: module3LeftView.frame.width - paddingEdge*2, height: module3LeftView.frame.width - paddingEdge*2))
            SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + module3LeftData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                if result{
                    module3LeftViewImageView.image = SDImage
                }
            }
            module3LeftView.addSubview(module3LeftViewImageView)
            
            let module3LeftViewDataButton = createButton(CGRect(origin: CGPoint.zero, size: module3LeftView.frame.size), tag: TagController.businessTags().module3Tag + 0, url: module3LeftData.url)
            module3LeftView.addSubview(module3LeftViewDataButton)
            module3View.addSubview(module3LeftView)
            
            if module3.dataList.count > 1 {
                let module3TopData = module3.dataList[1]
                let module3TopView = UIView(frame: CGRect(x: module3LeftView.frame.width, y: 0, width: module3View.frame.width - module3LeftView.frame.width, height: (module3View.frame.height)/2))
                module3TopView.layer.borderColor = UIColor.backgroundGray().cgColor
                module3TopView.layer.borderWidth = borderWidth
                module3TopView.backgroundColor = UIColor.white
                
                let module3TopViewTitleLabel = BusinessDataTitleLabel(text: module3TopData.title, textColor: UIColor.black)
                module3TopViewTitleLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: paddingEdge), size: CGSize(width: module3TopView.frame.width - (module3TopView.frame.height - paddingEdge*2) - paddingEdge*3, height: module3TopViewTitleLabel.frame.height))
                module3TopView.addSubview(module3TopViewTitleLabel)
                
                let module3TopViewContentLabel = BusinessDataContentLabel(text: module3TopData.content)
                module3TopViewContentLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: module3TopViewTitleLabel.frame.origin.y + module3TopViewTitleLabel.frame.height), size: CGSize(width: module3TopView.frame.width - (module3TopView.frame.height - paddingEdge*2) - paddingEdge*3, height: module3TopViewContentLabel.frame.height))
                module3TopView.addSubview(module3TopViewContentLabel)
                
                let module3TopViewImageView = UIImageView(frame: CGRect(x: module3TopView.frame.width - paddingEdge -  (module3TopView.frame.height - paddingEdge*2), y: paddingEdge, width: module3TopView.frame.height - paddingEdge*2, height: module3TopView.frame.height - paddingEdge*2))
                SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + module3TopData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                    if result{
                        module3TopViewImageView.image = SDImage
                    }
                }
                module3TopView.addSubview(module3TopViewImageView)
                
                let module3TopViewDataButton = createButton(CGRect(origin: CGPoint.zero, size: module3TopView.frame.size), tag: TagController.businessTags().module3Tag + 1, url: module3TopData.url)
                module3TopView.addSubview(module3TopViewDataButton)
                module3View.addSubview(module3TopView)
                if module3.dataList.count > 2 {
                    let module3CenterData = module3.dataList[2]
                    let module3CenterView = UIView(frame: CGRect(x: module3LeftView.frame.width, y: module3TopView.frame.height, width: (module3View.frame.width - module3LeftView.frame.width)/2, height: (module3View.frame.height)/2))
                    module3CenterView.layer.borderColor = UIColor.backgroundGray().cgColor
                    module3CenterView.layer.borderWidth = borderWidth
                    module3CenterView.backgroundColor = UIColor.white
                    
                    let module3CenterViewTitleLabel = BusinessDataTitleLabel(text: module3CenterData.title, textColor: UIColor.black)
                    module3CenterViewTitleLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: paddingEdge/2), size: CGSize(width: module3CenterView.frame.width - paddingEdge*2, height: module3CenterViewTitleLabel.frame.height))
                    module3CenterView.addSubview(module3CenterViewTitleLabel)
                    
                    let module3CenterViewContentLabel = BusinessDataContentLabel(text: module3CenterData.content)
                    module3CenterViewContentLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: module3CenterViewTitleLabel.frame.origin.y + module3CenterViewTitleLabel.frame.height), size: CGSize(width:  module3CenterView.frame.width - paddingEdge*2, height: module3CenterViewContentLabel.frame.height))
                    module3CenterView.addSubview(module3CenterViewContentLabel)
                    
                    let module3CenterViewImageViewWidth = module3CenterView.frame.height - (module3CenterViewContentLabel.frame.origin.y + module3CenterViewContentLabel.frame.height + paddingEdge)
                    let module3CenterViewImageView = UIImageView(frame: CGRect(x: module3CenterView.frame.width - paddingEdge - module3CenterViewImageViewWidth, y: module3CenterViewContentLabel.frame.origin.y + module3CenterViewContentLabel.frame.height + paddingEdge/2, width: module3CenterViewImageViewWidth, height: module3CenterViewImageViewWidth))
                    SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + module3CenterData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                        if result{
                            module3CenterViewImageView.image = SDImage
                        }
                    }
                    module3CenterView.addSubview(module3CenterViewImageView)
                    
                    let module3CenterViewDataButton = createButton(CGRect(origin: CGPoint.zero, size: module3CenterView.frame.size), tag: TagController.businessTags().module3Tag + 2, url: module3CenterData.url)
                    module3CenterView.addSubview(module3CenterViewDataButton)
                    module3View.addSubview(module3CenterView)
                    if module3.dataList.count > 3 {
                        let module3RightData = module3.dataList[3]
                        let module3RightView = UIView(frame: CGRect(x: module3View.frame.width - (module3View.frame.width - module3LeftView.frame.width)/2, y: module3TopView.frame.height, width: (module3View.frame.width - module3LeftView.frame.width)/2, height: module3View.frame.height/2))
                        module3RightView.layer.borderColor = UIColor.backgroundGray().cgColor
                        module3RightView.layer.borderWidth = borderWidth
                        module3RightView.backgroundColor = UIColor.white
                        
                        let module3RightViewTitleLabel = BusinessDataTitleLabel(text: module3RightData.title, textColor: UIColor.black)
                        module3RightViewTitleLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: paddingEdge/2), size: CGSize(width: module3RightView.frame.width - paddingEdge*2, height: module3RightViewTitleLabel.frame.height))
                        module3RightView.addSubview(module3RightViewTitleLabel)
                        
                        let module3RightViewContentLabel = BusinessDataContentLabel(text: module3RightData.content)
                        module3RightViewContentLabel.frame = CGRect(origin: CGPoint(x: paddingEdge, y: module3RightViewTitleLabel.frame.origin.y + module3RightViewTitleLabel.frame.height), size: CGSize(width:  module3RightView.frame.width - paddingEdge*2, height: module3RightViewContentLabel.frame.height))
                        module3RightView.addSubview(module3RightViewContentLabel)
                        
                        let module3RightViewImageViewWidth = module3RightView.frame.height - (module3RightViewContentLabel.frame.origin.y + module3RightViewContentLabel.frame.height + paddingEdge)
                        let module3RightViewImageView = UIImageView(frame: CGRect(x: module3RightView.frame.width - paddingEdge - module3RightViewImageViewWidth, y: module3RightViewContentLabel.frame.origin.y + module3RightViewContentLabel.frame.height + paddingEdge/2, width: module3RightViewImageViewWidth, height: module3RightViewImageViewWidth))
                        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + module3RightData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                            if result{
                                module3RightViewImageView.image = SDImage
                            }
                        }
                        module3RightView.addSubview(module3RightViewImageView)
                        
                        let module3RightViewDataButton = createButton(CGRect(origin: CGPoint.zero, size: module3RightView.frame.size), tag: TagController.businessTags().module3Tag + 3, url: module3RightData.url)
                        module3RightView.addSubview(module3RightViewDataButton)
                        module3View.addSubview(module3RightView)
                    }
                }
            }
        }
        baseScrollView.addSubview(module3View)
        
        let module4 = moduleDic.object(forKey: moduleList[3]) as! ModuleModel
        var module4View: UIView
        if(module4.dataList.count > 0){
            let module4Data = module4.dataList[0]
            module4View = BusinessImageModuleDataView(frame: CGRect(x: 0, y: module3View.frame.origin.y + module3View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4), data: module4Data)
            (module4View as! BusinessImageModuleDataView).buttonDelegate = self
        }else{
            module4View = UIView(frame: CGRect(x: 0, y: module3View.frame.origin.y + module3View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4))
            module4View.backgroundColor = UIColor.white
        }
        baseScrollView.addSubview(module4View)
        
        let module5 = moduleDic.object(forKey: moduleList[4]) as! ModuleModel
        let module5TitleView = BusinessModuleTitleView(frame: CGRect(x: 0, y: module4View.frame.origin.y + module4View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight),name: module5.name)
        module5TitleView.layer.borderColor = UIColor.backgroundGray().cgColor
        module5TitleView.layer.borderWidth = borderWidth
        baseScrollView.addSubview(module5TitleView)
        
        let module5View = UIView(frame: CGRect(x: 0, y: module5TitleView.frame.origin.y + module5TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/5*3))
        if(module5.dataList.count>0){
            let topCount = module5.dataList.count < 2 ? module5.dataList.count : 2
            for i in 0 ..< topCount{
                let topData = module5.dataList[i]
                let topChildView = BusinessLandscapeModuleDataView(frame: CGRect(x: module5View.frame.width/2 * CGFloat(i), y: 0, width: (module5View.frame.width)/2, height: (module5View.frame.height)/3),data: topData)
                topChildView.buttonDelegate = self
                module5View.addSubview(topChildView)
            }
            if(module5.dataList.count>2){
                let bottomCount = module5.dataList.count < 6 ? module5.dataList.count : 6
                for i in topCount ..< bottomCount{
                    let bottomData = module5.dataList[i]
                    let bottomChildView = BusinessPortraitModuleDataView(frame: CGRect(x: module5View.frame.width/4 * CGFloat(i - topCount), y: module5View.frame.height/3, width: module5View.frame.width/4, height: module5View.frame.height - module5View.frame.height/3),data: bottomData)
                    bottomChildView.buttonDelegate = self
                    module5View.addSubview(bottomChildView)
                }
            }
        }
        baseScrollView.addSubview(module5View)
        baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: module5View.frame.origin.y + module5View.frame.height)
    }
}
