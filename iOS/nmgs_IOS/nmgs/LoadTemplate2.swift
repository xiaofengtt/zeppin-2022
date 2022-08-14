//
//  LoadTemplate2.swift
//  nmgs
//
//  Created by zeppin on 2016/11/9.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

extension BusinessViewController{
    func loadTemplate2(){
        let titleViewHeight: CGFloat = 25
        let borderWidth: CGFloat = 1
        let paddingEdge: CGFloat = 10
        let moduleSpace: CGFloat = 10
        let moduleList = Template.template2ModuleList
        
        let module1 = moduleDic.object(forKey: moduleList[0]) as! ModuleModel
        let module1View = BusinessBannerView(frame: CGRect(x: 0,y: 0,width: baseScrollView.frame.width,height: baseScrollView.frame.width/4*3), module: module1)
        module1View.buttonDelegate = self
        baseScrollView.addSubview(module1View)
        
        let module2 = moduleDic.object(forKey: moduleList[1]) as! ModuleModel
        let module2View = UIView(frame: CGRect(x: 0, y: module1View.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/2))
        let module2row = 2
        let module2num = 2
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
                    
                    let dataImageView = UIImageView(frame: CGRect(x: paddingEdge, y: paddingEdge, width: dataView.frame.height - paddingEdge*2, height: dataView.frame.height - paddingEdge*2))
                    SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + moduleData.imageUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
                        if result{
                            dataImageView.image = SDImage
                        }
                    }
                    dataView.addSubview(dataImageView)
                    
                    let dataLabel = BusinessDataTitleLabel(text: moduleData.title, textColor: UIColor.darkGray)
                    dataLabel.frame = CGRect(x: module2DataViewHeight, y: (module2DataViewHeight - dataLabel.frame.height)/2, width: module2DataViewWidth - module2DataViewHeight - paddingEdge, height: dataLabel.frame.height)
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
        var module3View: UIView
        if(module3.dataList.count > 0){
            let module3Data = module3.dataList[0]
            module3View = BusinessImageModuleDataView(frame: CGRect(x: 0, y: module2View.frame.origin.y + module2View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4), data: module3Data)
            (module3View as! BusinessImageModuleDataView).buttonDelegate = self
        }else{
            module3View = UIView(frame: CGRect(x: 0, y: module2View.frame.origin.y + module2View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: baseScrollView.frame.width/4))
            module3View.backgroundColor = UIColor.white
        }
        baseScrollView.addSubview(module3View)
        
        let module4 = moduleDic.object(forKey: moduleList[3]) as! ModuleModel
        let module4TitleView = UIView(frame: CGRect(x: 0, y: module3View.frame.origin.y + module3View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight))
        module4TitleView.backgroundColor = UIColor.lightGray
        let module4TitleLabel = UILabel()
        module4TitleLabel.font = UIFont.systemFont(ofSize: 14)
        module4TitleLabel.text = module4.name
        module4TitleLabel.sizeToFit()
        module4TitleLabel.center = CGPoint(x: module4TitleView.frame.width/2, y: module4TitleView.frame.height/2)
        module4TitleView.addSubview(module4TitleLabel)
        baseScrollView.addSubview(module4TitleView)
        
        let module4View = UIView(frame: CGRect(x: 0, y: module4TitleView.frame.origin.y + module4TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/36*13))
        module4View.backgroundColor = UIColor.white
        let module4DataCount = module4.dataList.count < 3 ? module4.dataList.count : 3
        for i in 0 ..< module4DataCount{
            let module4Data = module4.dataList[i]
            let module4DataView = BusinessImageModuleDataView(frame: CGRect(x: module4View.frame.width/3 * CGFloat(i), y: 0, width: module4View.frame.width/3, height: module4View.frame.height), data: module4Data)
            module4DataView.buttonDelegate = self
            module4View.addSubview(module4DataView)
        }
        baseScrollView.addSubview(module4View)
        
        let module5 = moduleDic.object(forKey: moduleList[4]) as! ModuleModel
        var module5View: UIView
        if(module5.dataList.count > 0){
            let module5Data = module5.dataList[0]
            module5View = BusinessImageModuleDataView(frame: CGRect(x: paddingEdge, y: module4View.frame.origin.y + module4View.frame.height + moduleSpace, width: baseScrollView.frame.width - paddingEdge*2, height: (baseScrollView.frame.width - paddingEdge*2)/9*2), data: module5Data)
            (module5View as! BusinessImageModuleDataView).buttonDelegate = self
        }else{
            module5View = UIView(frame: CGRect(x: paddingEdge, y: module4View.frame.origin.y + module4View.frame.height + moduleSpace, width: baseScrollView.frame.width - paddingEdge*2, height: (baseScrollView.frame.width - paddingEdge*2)/9*2))
        }
        baseScrollView.addSubview(module5View)
        
        let module6 = moduleDic.object(forKey: moduleList[5]) as! ModuleModel
        let module6TitleView = UIView(frame: CGRect(x: 0, y: module5View.frame.origin.y + module5View.frame.height + moduleSpace, width: baseScrollView.frame.width, height: titleViewHeight))
        module6TitleView.backgroundColor = UIColor.lightGray
        let module6TitleLabel = UILabel()
        module6TitleLabel.font = UIFont.systemFont(ofSize: 14)
        module6TitleLabel.text = module6.name
        module6TitleLabel.sizeToFit()
        module6TitleLabel.center = CGPoint(x: module6TitleView.frame.width/2, y: module6TitleView.frame.height/2)
        module6TitleView.addSubview(module6TitleLabel)
        baseScrollView.addSubview(module6TitleView)
        
        let colorList: [UIColor] = [UIColor(red: 32/255, green: 190/255, blue: 160/255, alpha: 1),
                         UIColor(red: 230/255, green: 72/255, blue: 155/255, alpha: 1),
                         UIColor(red: 50/255, green: 198/255, blue: 29/255, alpha: 1),
                         UIColor(red: 0/255, green: 133/255, blue: 205/255, alpha: 1)]
        let module6View = UIView(frame: CGRect(x: 0, y: module6TitleView.frame.origin.y + module6TitleView.frame.height, width: baseScrollView.frame.width, height: baseScrollView.frame.width/5*2))
        let module6row = 2
        let module6num = 2
        let module6DataViewWidth = module6View.frame.width/2
        let module6DataViewHeight = module6View.frame.height/2
        for r in 0 ..< module6row{
            for n in 0 ..< module6num{
                if(module6.dataList.count > r * module6num + n){
                    let moduleData = module6.dataList[r * module6num + n]
                    let dataView = BusinessLandscapeModuleDataView(frame: CGRect(origin: CGPoint(x: module6DataViewWidth * CGFloat(n),y: module6DataViewHeight * CGFloat(r)), size: CGSize(width: module6DataViewWidth, height: module6DataViewHeight)),data: moduleData)
                    dataView.titleLabel.textColor = colorList[r * module6num + n]
                    dataView.buttonDelegate = self
                    module6View.addSubview(dataView)
                }else{
                    break
                }
            }
        }
        baseScrollView.addSubview(module6View)
        
        baseScrollView.contentSize = CGSize(width: baseScrollView.frame.width, height: module6View.frame.origin.y + module6View.frame.height)
    }
}
