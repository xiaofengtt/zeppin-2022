//
//  LoadTemplate3.swift
//  nmgs
//
//  Created by zeppin on 2016/11/9.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

extension BusinessViewController{
    func loadTemplate3(){
        let borderWidth: CGFloat = 1
        let paddingEdge: CGFloat = 10
        let moduleList = Template.template3ModuleList
        
        let module1 = moduleDic.object(forKey: moduleList[0]) as! ModuleModel
        let module1View = BusinessBannerView(frame: CGRect(x: 0,y: 0,width: baseScrollView.frame.width,height: baseScrollView.frame.width/12*5), module: module1)
        module1View.buttonDelegate = self
        baseScrollView.addSubview(module1View)
        
        let module2 = moduleDic.object(forKey: moduleList[1]) as! ModuleModel
        let module2View = UIView(frame: CGRect(x: 0, y: module1View.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4*3))
        let module2row = 3
        let module2num = 3
        let module2DataViewWidth = module2View.frame.width / CGFloat(module2num)
        let module2DataViewHeight = module2View.frame.height / CGFloat(module2row)
        for r in 0 ..< module2row{
            for n in 0 ..< module2num{
                if(module2.dataList.count > r * module2num + n){
                    let moduleData = module2.dataList[r * module2num + n]
                    
                    let dataView = UIView(frame: CGRect(origin: CGPoint(x: module2DataViewWidth * CGFloat(n),y: module2DataViewHeight * CGFloat(r)), size: CGSize(width: module2DataViewWidth, height: module2DataViewHeight)))
                    dataView.backgroundColor = UIColor.white
                    dataView.layer.borderColor = UIColor.backgroundGray().cgColor
                    dataView.layer.borderWidth = borderWidth
                    
                    let dataImageView = UIImageView(frame: CGRect(x: dataView.frame.width/3, y: dataView.frame.height/6, width: dataView.frame.width/3, height: dataView.frame.width/3))
                    SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + moduleData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                        if result{
                            dataImageView.image = SDImage
                        }
                    }
                    dataView.addSubview(dataImageView)
                    
                    let dataLabel = UILabel(frame: CGRect(x: 0, y: dataImageView.frame.origin.y + dataImageView.frame.height + paddingEdge/2, width: dataView.frame.width, height: 20))
                    dataLabel.font = UIFont.systemFont(ofSize: 12)
                    dataLabel.textColor = UIColor.darkGray
                    dataLabel.textAlignment = NSTextAlignment.center
                    dataLabel.text = moduleData.title
                    dataView.addSubview(dataLabel)
                    
                    let dataButton = createButton(CGRect(origin: CGPoint.zero, size: dataView.frame.size), tag: TagController.businessTags().module2Tag + module2num * r + n, url: moduleData.url + businessUrl(), title: moduleData.title)
                    dataView.addSubview(dataButton)
                    module2View.addSubview(dataView)
                }else{
                    break
                }
            }
        }
        baseScrollView.addSubview(module2View)
        
        baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: module2View.frame.origin.y + module2View.frame.height)
    }
}
