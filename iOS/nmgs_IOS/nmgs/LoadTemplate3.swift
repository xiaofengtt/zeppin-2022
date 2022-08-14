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
        let titleViewHeight: CGFloat = 25
        let borderWidth: CGFloat = 1
        let paddingEdge: CGFloat = 10
        let moduleSpace: CGFloat = 10
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
                    
                    let dataButton = createButton(CGRect(origin: CGPoint.zero, size: dataView.frame.size), tag: TagController.businessTags().module2Tag + module2num * r + n, url: moduleData.url + businessUrl())
                    dataView.addSubview(dataButton)
                    module2View.addSubview(dataView)
                }else{
                    break
                }
            }
        }
        baseScrollView.addSubview(module2View)
        
        baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: module2View.frame.origin.y + module2View.frame.height)
//        let module3 = moduleDic.object(forKey: moduleList[2]) as! ModuleModel
//        var module3View: UIView
//        if(module3.dataList.count > 0){
//            let module3Data = module3.dataList[0]
//            module3View = BusinessImageModuleDataView(frame: CGRect(x: 0, y: module2View.frame.origin.y + module2View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4), data: module3Data)
//            (module3View as! BusinessImageModuleDataView).buttonDelegate = self
//        }else{
//            module3View = UIView(frame: CGRect(x: 0, y: module2View.frame.origin.y + module2View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4))
//            module3View.backgroundColor = UIColor.white
//        }
//        baseScrollView.addSubview(module3View)
//        
//        let module4 = moduleDic.object(forKey: moduleList[3]) as! ModuleModel
//        let module4TitleView = BusinessModuleTitleView(frame: CGRect(x: 0, y: module3View.frame.origin.y + module3View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight),name: module4.name)
//        module4TitleView.layer.borderColor = UIColor.backgroundGray().cgColor
//        module4TitleView.layer.borderWidth = borderWidth
//        baseScrollView.addSubview(module4TitleView)
//        
//        let colorList: [UIColor] = [UIColor(red: 32/255, green: 190/255, blue: 160/255, alpha: 1),
//                                    UIColor(red: 230/255, green: 72/255, blue: 155/255, alpha: 1),
//                                    UIColor(red: 50/255, green: 198/255, blue: 29/255, alpha: 1),
//                                    UIColor(red: 0/255, green: 133/255, blue: 205/255, alpha: 1)]
//        let module4View = UIView(frame: CGRect(x: 0, y: module4TitleView.frame.origin.y + module4TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/5*2))
//        let module4row = 2
//        let module4num = 2
//        let module4DataViewWidth = module4View.frame.width/2
//        let module4DataViewHeight = module4View.frame.height/2
//        for r in 0 ..< module4row{
//            for n in 0 ..< module4num{
//                if(module4.dataList.count > r * module4num + n){
//                    let moduleData = module4.dataList[r * module4num + n]
//                    let dataView = BusinessLandscapeModuleDataView(frame: CGRect(origin: CGPoint(x: module4DataViewWidth * CGFloat(n),y: module4DataViewHeight * CGFloat(r)), size: CGSize(width: module4DataViewWidth, height: module4DataViewHeight)),data: moduleData)
//                    dataView.titleLabel.textColor = colorList[r * module4num + n]
//                    dataView.buttonDelegate = self
//                    module4View.addSubview(dataView)
//                }else{
//                    break
//                }
//            }
//        }
//        baseScrollView.addSubview(module4View)
//        
//        let module5 = moduleDic.object(forKey: moduleList[4]) as! ModuleModel
//        let module5TitleView = BusinessModuleTitleView(frame: CGRect(x: 0, y: module4View.frame.origin.y + module4View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight),name: module5.name)
//        module5TitleView.layer.borderColor = UIColor.backgroundGray().cgColor
//        module5TitleView.layer.borderWidth = borderWidth
//        baseScrollView.addSubview(module5TitleView)
//        
//        let module5View = UIView(frame: CGRect(x: 0, y: module5TitleView.frame.origin.y + module5TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/5))
//        if(module5.dataList.count>0){
//            let topCount = module5.dataList.count < 2 ? module5.dataList.count : 2
//            for i in 0 ..< topCount{
//                let topData = module5.dataList[i] 
//                let topChildView = BusinessLandscapeModuleDataView(frame: CGRect(x: module5View.frame.width/2 * CGFloat(i), y: 0, width: (module5View.frame.width)/2, height: module5View.frame.height),data: topData)
//                topChildView.buttonDelegate = self
//                module5View.addSubview(topChildView)
//            }
//        }
//        baseScrollView.addSubview(module5View)
//        
//        baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: module5View.frame.origin.y + module5View.frame.height)
    }
}
