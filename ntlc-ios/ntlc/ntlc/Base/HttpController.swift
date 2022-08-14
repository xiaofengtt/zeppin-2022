//
//  HttpController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation
import AFNetworking

class HttpController {
    
    static let timeout: TimeInterval = 10
    
    static func showTimeout(viewController: UIViewController){
        AlertView(title: "网络连接异常，请稍后再试！").showByTime(time: 2)
    }
    
    static func showLoading(viewController: UIViewController) -> LoadingView{
        let loadingView = LoadingView()
        loadingView.show(animated: true)
        return loadingView
    }
    
    static func hideLoading(loadingView: LoadingView){
        loadingView.hide(animated: true)
    }
    
    static func getToken(data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        self.get("product/getTime", params: NSDictionary(), data: { (adata) in
            let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
            let codeString = secretKey + timestamp + user!.uuid
            data(EncodingUtil.getBase64(systemType + timestamp + user!.uuid + codeString.md5))
        },errors: { (error) in
            errors("网络连接异常，请稍后再试！" as AnyObject)
        })
    }
    
    static func loadSwitch(){
        HttpController.getSwitch(data: { (result) in
            isPublish = result
        }) { (error) in
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                HttpController.loadSwitch()
            }
        }
    }
    
    static func getSwitch(data: @escaping (_ result: Bool) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        self.get("product/getTime", params: NSDictionary(), data: { (adata) in
            let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
            let codeString = secretKey + timestamp + webmarket + String(version)
            let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
            
            HttpController.get("login/getWebmarketSwitch", params: NSDictionary(dictionary: ["token": token, "webmarket": EncodingUtil.getBase64(webmarket)]), data: { (adata) in
                let dataDictionary = adata as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    data(dataDictionary.object(forKey: "data") as! Bool)
                }else{
                    errors("网络连接异常，请稍后再试！" as AnyObject)
                }
            }, errors: { (error) in
                errors("网络连接异常，请稍后再试！" as AnyObject)
            })
        },errors: { (error) in
            errors("网络连接异常，请稍后再试！" as AnyObject)
        })
    }
    
    static func getUserAccount(data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        HttpController.getToken(data: { (token) in
            HttpController.get("financial/get", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (adata) in
                let dataDictionary = adata as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    userAccount = UserAccountModel(data: dataDictionary.object(forKey: "data") as! NSDictionary)
                     data("用户账户信息更新成功")
                }else{
                    errors("网络连接异常，请稍后再试！" as AnyObject)
                }
            }, errors: { (error) in
                errors("网络连接异常，请稍后再试！" as AnyObject)
            })
        }, errors: { (error) in
            errors("网络连接异常，请稍后再试！" as AnyObject)
        })
    }
    
    static func getUser(uuid: String, data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        if(user == nil){
            user = UserModel()
            user?.uuid = uuid
        }
        
        HttpController.getToken(data: { (token) in
            let userParams = NSDictionary(dictionary: ["uuid" : uuid ,"token" : token])
            HttpController.get("user/get", params: userParams, data: { (adata) in
                let dic = adata as! NSDictionary
                let status = dic.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    user = UserModel(data: dic.value(forKey: "data") as! NSDictionary)
                    LocalDataController.writeLocalData("user", data: user!.getDictionary())
                    data("用户信息更新成功")
                }else{
                    user = nil
                    userAccount = nil
                    errors("网络连接异常，请稍后再试！" as AnyObject)
                }
            }, errors: { (error) in
                errors("网络连接异常，请稍后再试！" as AnyObject)
            })
        },errors: { (error) in
            errors("网络连接异常，请稍后再试！" as AnyObject)
        })
    }
    
    static func get(_ url: String, params: NSDictionary, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        //字符串的转码
        var urlString = UrlBase + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        
        let paramDic = NSMutableDictionary(dictionary: params)
        paramDic.setValue(EncodingUtil.getBase64(String(version)), forKey: "version")
        
        //创建管理者对象
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        //设置允许请求的类别
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        //开始请求
        manager.get(urlString, parameters: paramDic, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription as AnyObject)
        })
    }
    
    static func post(_ url: String, params: NSDictionary!, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        //字符串的转码
        var urlString = UrlBase + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        
        let paramDic = NSMutableDictionary(dictionary: params)
        paramDic.setValue(EncodingUtil.getBase64(String(version)), forKey: "version")
        
        //创建管理者对象
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        //设置允许请求的类别
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        //开始请求
        manager.post(urlString, parameters: paramDic, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription as AnyObject)
        })
    }
}
