//
//  AlipayController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class AlipayController {
    
    static func pay(code: String){
        UIApplication.shared.openURL(URL(string: code)!)
    }
    
    static func auth(){
        HttpController.getToken(data: { (token) in
            HttpController.get("user/getAuthInfo4Ali", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                let dic = data as! NSDictionary
                let status = dic.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    AlipaySDK.defaultService().auth_V2(withInfo: dic.value(forKey: "data") as! String, fromScheme: "alipay2016071901635869") { (data) in
                        alipayResult(result: data)
                    }
                }else{
                    alipayAuthTimeout()
                }
            }, errors: { (error) in
                alipayAuthTimeout()
            })
        }) { (errors) in
            alipayAuthTimeout()
        }
    }
    
    static func alipayResult(result: [AnyHashable : Any]?){
        if(result != nil){
            let resultStatus = (result! as NSDictionary).object(forKey: "resultStatus") as! String
            if(resultStatus == "9000"){
                let result = (result! as NSDictionary).object(forKey: "result") as! String
                alipayAuthSuccess(data: result)
            }else{
                alipayAuthField()
            }
        }else{
            alipayAuthField()
        }
    }
    
    static func alipayAuthSuccess(data: String){
        let datas = data.components(separatedBy: "&")
        for string in datas{
            if((string.range(of: "auth_code")) != nil){
                let code = string.replacingOccurrences(of: "auth_code=", with: "")
                HttpController.getToken(data: { (token) in
                    HttpController.post("user/bindingUserInfoByAli", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "code": code]), data: { (data) in
                        let dic = data as! NSDictionary
                        let status = dic.object(forKey: "status") as! String
                        if(status == "SUCCESS"){
                            NotificationCenter.default.post(Notification(name: NSNotification.Name(rawValue: "alipayAuthSuccess")))
                        }
                    }, errors: { (error) in
                        alipayAuthTimeout()
                    })
                }, errors: { (error) in
                    alipayAuthTimeout()
                })
            }
        }
    }
    
    static func alipayAuthField(){
        NotificationCenter.default.post(Notification(name: NSNotification.Name(rawValue: "alipayAuthField")))
    }
    
    static func alipayAuthTimeout(){
        NotificationCenter.default.post(Notification(name: NSNotification.Name(rawValue: "alipayAuthTimeout")))
    }
}
