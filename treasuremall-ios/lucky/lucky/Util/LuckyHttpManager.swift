//
//  LuckyHttpManager.swift
//  lucky
//  HTTP请求管理
//  Created by Farmer Zhu on 2019/8/5.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import AFNetworking


class LuckyHttpManager{
    
//    static let basePath: String = "http://192.168.1.120:28080/"
    static let basePath: String = "https://api.happyxmall.com/"
    
    static let device: String = "01"
    static let appKey = "8279a48ec7c8a6303bde3f979ce77a9d"
    static let defaultTimeout: TimeInterval = 30
    static let defaultFailMessage: String = NSLocalizedString("Connect Failed", comment: "")
    
    //获取时间管理 减少请求次数
    static var getTimeLocal: Int?
    static var getTimeServer: Int?
    
    //展示公用loading
    static func showLoading(viewController: UIViewController) -> MBProgressHUD{
        let hud = MBProgressHUD.showAdded(to: viewController.view , animated: true)
        hud.layer.zPosition = 0.9
        hud.backgroundColor = UIColor.black.withAlphaComponent(0.2)
        hud.mode = MBProgressHUDMode.customView
        
        let loadingView = LuckyLoadingView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: LuckyLoadingView.viewWidth, height: LuckyLoadingView.viewHeight)))
        hud.customView = loadingView
        hud.removeFromSuperViewOnHide = true
        hud.label.text = NSLocalizedString("Loading", comment: "")
        hud.label.textColor = UIColor.fontGray()
        hud.minShowTime = 0.5
        return hud
    }
    
    //隐藏公用loading
    static func hideLoading(loadingView: MBProgressHUD){
        loadingView.hide(animated: true)
    }
    
    //超时提示
    static func showTimeout(viewController: UIViewController){
        LuckyAlertView(title: defaultFailMessage).showByTime(time: 2)
    }
    
    //拼装用户通用token
    static func getToken(user: LuckyFrontUserModel, data: @escaping (_ data: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                data("")
            }else{
                let desString = LuckyEncodingUtil.getDes("\(appKey)\(timestamp)\(user.mobile)")
                data(LuckyEncodingUtil.getBase64("01\(timestamp)\(user.uuid)\(desString)"))
            }
        }
    }
    
    //获取服务器时间
    static func getTime(result: @escaping (_ timestamp: String) -> ()){
        let now = Date().timestamp
        if(getTimeLocal != nil && now > getTimeLocal! && now - getTimeLocal! < 10000){
            //10秒内不重复获取
            result("\(getTimeServer!)")
        }else{
            get("front/login/getTime", params: NSDictionary(), timeout: defaultTimeout, data: { (datas) in
                let dataDic = datas as! NSDictionary
                getTimeServer = Int.valueOf(any: dataDic["data"])
                getTimeLocal = Date().timestamp
                result("\(Int.valueOf(any: dataDic["data"]))")
            }) { (error) in
                result("")
            }
        }
    }
    
    //带用户token的get方法
    static func getWithToken(_ url: String, params: NSDictionary, success: @escaping (_ data: AnyObject) -> (), fail: @escaping (_ reason: String) -> ()){
        getToken(user: globalUserData!) { (token) in
            let paramsDic: NSMutableDictionary = NSMutableDictionary(dictionary: params)
            paramsDic["token"] = token
            get(url, params: paramsDic, timeout: defaultTimeout, data: { (adata) in
                let dataDic = adata as! NSDictionary
                if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                    if(dataDic["data"] != nil){
                        success(dataDic["data"] as AnyObject)
                    }else{
                        success(NSDictionary() as AnyObject)
                    }
                }else{
                    fail(String.valueOf(any: dataDic["message"]))
                }
            }) { (error) in
                fail(error)
            }
        }
    
    }
    
    //不带用户token的get方法
    static func getWithoutToken(_ url: String, params: NSDictionary, success: @escaping (_ data: AnyObject) -> (), fail: @escaping (_ reason: String) -> ()){
        get(url, params: params, timeout: defaultTimeout, data: { (adata) in
            let dataDic = adata as! NSDictionary
            if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                if(dataDic["data"] != nil){
                    success(dataDic["data"] as AnyObject)
                }else{
                    success(NSDictionary() as AnyObject)
                }
            }else{
                fail(String.valueOf(any: dataDic["message"]))
            }
        }) { (error) in
            fail(error)
        }
    }
    
    //带用户token的post方法
    static func postWithToken(_ url: String, params: NSDictionary, success: @escaping (_ data: AnyObject) -> (), fail: @escaping (_ reason: String) -> ()){
        getToken(user: globalUserData!) { (token) in
            let paramsDic: NSMutableDictionary = NSMutableDictionary(dictionary: params)
            paramsDic["token"] = token
            post(url, params: paramsDic, timeout: defaultTimeout, data: { (adata) in
                let dataDic = adata as! NSDictionary
                if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                    if(dataDic["data"] != nil){
                        success(dataDic["data"] as AnyObject)
                    }else{
                        success(NSDictionary() as AnyObject)
                    }
                }else{
                    fail(String.valueOf(any: dataDic["message"]))
                }
            }) { (error) in
                fail(error)
            }
        }
    
    }
    
    //不带用户token的post方法
    static func postWithoutToken(_ url: String, params: NSDictionary, success: @escaping (_ data: AnyObject) -> (), fail: @escaping (_ reason: String) -> ()){
        post(url, params: params, timeout: defaultTimeout, data: { (adata) in
            let dataDic = adata as! NSDictionary
            if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                if(dataDic["data"] != nil){
                    success(dataDic["data"] as AnyObject)
                }else{
                    success(NSDictionary() as AnyObject)
                }
            }else{
                fail(String.valueOf(any: dataDic["message"]))
            }
        }) { (error) in
            fail(error)
        }
    }
    
    //get方法
    static func get(_ url: String, params: NSDictionary, timeout: TimeInterval, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: String) -> ()){
        var urlString = basePath + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let paramDic = NSMutableDictionary(dictionary: params)
        //全局通用参数
        paramDic["version"] = globalVersion
        paramDic["channel"] = "appStore"
        paramDic["countryCode"] = globalCountryCode
        
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        
        //允许不安全的https
        let policy = AFSecurityPolicy(pinningMode: AFSSLPinningMode.none)
        policy.allowInvalidCertificates = true
        policy.validatesDomainName = false
        manager.securityPolicy = policy
        
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        manager.get(urlString, parameters: paramDic, headers: nil, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription)
        })
    }
    
    //post方法
    static func post(_ url: String, params: NSDictionary, timeout: TimeInterval, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: String) -> ()){
        var urlString = basePath + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let paramDic = NSMutableDictionary(dictionary: params)
        //全局通用参数
        paramDic["version"] = globalVersion
        paramDic["channel"] = "appStore"
        paramDic["countryCode"] = globalCountryCode
        
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        
        //允许不安全的https
        let policy = AFSecurityPolicy(pinningMode: AFSSLPinningMode.none)
        policy.allowInvalidCertificates = true
        policy.validatesDomainName = false
        manager.securityPolicy = policy
        
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        manager.post(urlString, parameters: paramDic, headers: nil, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription)
        })
    }
    
    //通过验证码登录或注册
    static func loginByCode(mobile: String, country: String, code: String, success: @escaping (_ user: LuckyFrontUserModel) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //封装token base64(device+time+des(key+time+mobile+code))
                let desString = LuckyEncodingUtil.getDes("\(appKey)\(timestamp)\(mobile)\(code)")
                let token = LuckyEncodingUtil.getBase64("\(device)\(timestamp)\(desString)")
                
                post("front/login/loginByCode", params: ["mobile": LuckyEncodingUtil.getBase64(mobile), "token": token, "channelId": appChannel, "country" : country], timeout: defaultTimeout, data: { (data) in
                    let dataDic = data as! NSDictionary
                    if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                        if(dataDic["data"] != nil){
                            if(globalDeviceToken != nil){
                                LuckyRemotePushManager.uploadToken(deviceToken: globalDeviceToken!)
                            }
                            success(LuckyFrontUserModel(data: dataDic["data"] as! NSDictionary))
                        }else{
                            fail(defaultFailMessage)
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }) { (error) in
                    fail(defaultFailMessage)
                }
            }
        }
    }
    
    //密码登录
    static func loginByPassword(mobile: String, password: String, success: @escaping (_ user: LuckyFrontUserModel) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //封装token base64(device+time+des(key+time+mobile+des(pwd)))
                let pwdDes = LuckyEncodingUtil.getDes(password)
                let desString = LuckyEncodingUtil.getDes("\(appKey)\(timestamp)\(mobile)\(pwdDes)")
                let token = LuckyEncodingUtil.getBase64("\(device)\(timestamp)\(desString)")
                
                post("front/login/loginByPwd", params: ["mobile": LuckyEncodingUtil.getBase64(mobile), "token": token], timeout: defaultTimeout, data: { (data) in
                    let dataDic = data as! NSDictionary
                    if("SUCCESS" == dataDic["status"] as! String){
                        if(dataDic["data"] != nil){
                            if(globalDeviceToken != nil){
                                LuckyRemotePushManager.uploadToken(deviceToken: globalDeviceToken!)
                            }
                            success(LuckyFrontUserModel(data: dataDic["data"] as! NSDictionary))
                        }else{
                            fail(defaultFailMessage)
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }) { (error) in
                    fail(defaultFailMessage)
                }
            }
        }
    }
    
    //苹果三方登录
    static func loginByApple(user: String, identityToken: String, nickname: String, success: @escaping (_ user: LuckyFrontUserModel) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //拼装token base64(device+time+des(key+time+mobile+des(user)))
                let desBase = LuckyHttpManager.appKey + timestamp + LuckyEncodingUtil.getDes(user)
                let token = LuckyEncodingUtil.getBase64(LuckyHttpManager.device + timestamp + LuckyEncodingUtil.getDes(desBase))
                
                LuckyHttpManager.postWithoutToken("front/login/loginByApple", params: ["token": token, "identityToken": identityToken, "nickname": nickname, "channelId": appChannel]) { (data) in
                    if(globalDeviceToken != nil){
                        LuckyRemotePushManager.uploadToken(deviceToken: globalDeviceToken!)
                    }
                    success(LuckyFrontUserModel(data: data as! NSDictionary))
                } fail: { (reason) in
                    fail(reason)
                }
            }
        }
    }
    
    //非死不可三方登录
    static func loginByFacebook(userID: String, token: String, nickname: String, imageUrl: String, success: @escaping (_ user: LuckyFrontUserModel) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //拼装token base64(device+time+des(key+time+des(userID)))
                let desBase = LuckyHttpManager.appKey + timestamp + LuckyEncodingUtil.getDes(userID)
                let sysToken = LuckyEncodingUtil.getBase64(LuckyHttpManager.device + timestamp + LuckyEncodingUtil.getDes(desBase))
                
                LuckyHttpManager.postWithoutToken("front/login/loginByFacebook", params: ["userID": userID, "token": sysToken, "accessToken": token, "nickname": nickname, "image": imageUrl, "channelId": appChannel]) { (data) in
                    if(globalDeviceToken != nil){
                        LuckyRemotePushManager.uploadToken(deviceToken: globalDeviceToken!)
                    }
                    success(LuckyFrontUserModel(data: data as! NSDictionary))
                } fail: { (reason) in
                    fail(reason)
                }
            }
        }
    }
    
    //重置密码
    static func resetPassword(mobile: String, code: String, password: String, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //拼装token base64(time+des(key+time+mobile+des(password)+code))
                let passwordDes = LuckyEncodingUtil.getDes(password)
                let desString = LuckyEncodingUtil.getDes("\(appKey)\(timestamp)\(mobile)\(passwordDes)\(code)")
                let token = LuckyEncodingUtil.getBase64("\(timestamp)\(desString)")
                
                post("front/login/resetPwd", params: ["mobile": LuckyEncodingUtil.getBase64(mobile), "token": token, "pwd": LuckyEncodingUtil.getBase64(passwordDes)], timeout: defaultTimeout, data: { (data) in
                    let dataDic = data as! NSDictionary
                    if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                        if(dataDic["data"] != nil){
                            success(String.valueOf(any: dataDic["data"]))
                        }else{
                            success("")
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }) { (error) in
                    fail(defaultFailMessage)
                }
            }
        }
    }
    
    //重置密码 校验原密码
    static func resetPasswordCheck(mobile: String, code: String, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        getTime { (timestamp) in
            if("" == timestamp){
                fail(defaultFailMessage)
            }else{
                //拼装token base64(time+des(key+time+mobile+code))
                let desString = LuckyEncodingUtil.getDes("\(appKey)\(timestamp)\(mobile)\(code)")
                let token = LuckyEncodingUtil.getBase64("\(timestamp)\(desString)")
                
                post("front/login/resetCheck", params: ["mobile": LuckyEncodingUtil.getBase64(mobile), "token": token], timeout: defaultTimeout, data: { (data) in
                    let dataDic = data as! NSDictionary
                    if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                        if(dataDic["data"] != nil){
                            success(String.valueOf(any: dataDic["data"]))
                        }else{
                            success("")
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }) { (error) in
                    fail(defaultFailMessage)
                }
            }
        }
    }
}
