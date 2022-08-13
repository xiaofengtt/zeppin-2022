//
//  BusinessViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/8.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class BusinessViewController: UIViewController, UIScrollViewDelegate, BusinessButtonViewDelegate{
    
    @IBOutlet weak var baseScrollView: UIScrollView!
    
    var moduleDic: NSDictionary!
    var urlDic: NSMutableDictionary = NSMutableDictionary()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        baseScrollView.delegate = self
        baseScrollView.backgroundColor = UIColor.backgroundGray()
        
        let businessParams = NSDictionary(dictionary: ["id": provinceId, "component": componentId])
        HttpController.getNSDataByParams("provinceTemplateInfo", paramsDictionary: businessParams, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "success" {
                let templateData = dataDictionary.object(forKey: "data") as! NSDictionary
                let template = templateData.object(forKey: "id") as! String
                let moduleListDictionary = templateData.object(forKey: "module") as! NSDictionary
                let thisModuleDic = ModuleModel.createModuleDic(template)
                let keys = thisModuleDic.allKeys
                for i in 0 ..< keys.count{
                    let module = thisModuleDic.object(forKey: keys[i]) as! ModuleModel
                    let moduleDictionary = moduleListDictionary.object(forKey: module.id) as! NSDictionary
                    module.name = moduleDictionary.object(forKey: "name") as! String
                    module.sequence = moduleDictionary.object(forKey: "sequence") as! Int
                    module.count = moduleDictionary.object(forKey: "count") as! Int
                    let moduleDataList = moduleDictionary.object(forKey: "datas") as! NSArray
                    for index in 0 ..< moduleDataList.count{
                        let moduleDataDictionary = moduleDataList[index] as! NSDictionary
                        let moduleData = ModuleDataModel()
                        moduleData.title = moduleDataDictionary.object(forKey: "title") as! String
                        moduleData.content = moduleDataDictionary.object(forKey: "content") as! String
                        moduleData.imageUrl = moduleDataDictionary.object(forKey: "imageUrl") as! String
                        moduleData.url = moduleDataDictionary.object(forKey: "url") as! String
                        moduleData.index = Int(moduleDataDictionary.object(forKey: "index") as! String)!
                        module.dataList.append(moduleData)
                    }
                }
                self.moduleDic = thisModuleDic
                self.loadScrollView(template)
            }
        }, errors: { (error) in
            
        })
    }
    
    func pushWebView(_ url: String, title: String) {
        if url != "http://#"{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "businessWebViewController") as! BusinessWebViewController
            vc.url = url
            vc.titleString = title
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func loadScrollView(_ template:String){
        if(template == Template.template3){
            loadTemplate3()
        }else{
            let alert = UIAlertController(title: "当前APP不支持此模板", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    func createButton(_ frame: CGRect, tag: Int, url: String ,title: String) -> UIButton{
        let button = UIButton(frame: frame)
        button.addTarget(self, action: #selector(clickDataButton), for: UIControlEvents.touchUpInside)
        button.tag = tag
        let data : [String] = [url,title]
        urlDic.setValue(data, forKey: "\(button.tag)")
        return button
    }
    
    func clickDataButton(_ sender:UIButton){
        let data = urlDic.object(forKey: "\(sender.tag)") as! [String]
        self.pushWebView(data[0], title: data[1])
    }
}
