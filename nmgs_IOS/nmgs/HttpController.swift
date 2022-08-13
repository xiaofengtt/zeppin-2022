//
//  HttpController1.swift
//  nmgs
//
//  Created by zeppin on 2016/10/27.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class HttpController {
    
    static let portBase = "/front/web/"
    static let timeout: TimeInterval = 10
    static let recieveTypeToUrl = NSDictionary(dictionary: [
        "sendCode" : "ssoUser!execute?uid=s0007" ,
        "messageLogin" : "ssoUser!execute?uid=s0008",
        "passwordLogin" : "ssoUser!execute?uid=s0001" ,
        "verifyUser" : "ssoUser!execute?uid=s0002" ,
        "categoryList" : "webInterface!execute?uid=i0001" ,
        "publishList" : "webInterface!execute?uid=i0002" ,
        "videoInfo" : "webInterface!execute?uid=i0003" ,
        "provinceTemplateInfo" : "webInterface!execute?uid=i0005" ,
        "checkComponent" : "webInterface!execute?uid=i0008" ,
        "totalPublishList" : "webInterface!execute?uid=i0009"
    ])
    
    static func showTimeout(viewController: UIViewController){
        let alert = UIAlertController(title: "网络连接失败", message: "请检查当前网络环境", preferredStyle: UIAlertControllerStyle.alert)
        let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
        alert.addAction(acCancel)
        viewController.present(alert, animated: true, completion: nil)
    }
    
    static func getNSDataByParams(_ recieveType: String, paramsDictionary: NSDictionary!, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        //字符串的转码
        var urlString = UrlBase + portBase
        urlString += (recieveTypeToUrl.object(forKey: recieveType) as! String )
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        //创建管理者对象
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        manager.securityPolicy.allowInvalidCertificates = true
        manager.securityPolicy.validatesDomainName = false
        //设置允许请求的类别
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        //开始请求
        if paramsDictionary == nil {
            data("请求发送失败" as AnyObject)
        }
        else{
            manager.post(urlString, parameters: paramsDictionary, progress: { (downloadProgress: Progress) -> Void in
                }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
                    data(responseObject! as AnyObject)
                }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
                    errors(error.localizedDescription as AnyObject)
            })
        }
    }
}
