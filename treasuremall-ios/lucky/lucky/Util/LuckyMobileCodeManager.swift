//
//  LuckyMobileCodeManager.swift
//  lucky
//  手机验证码管理
//  Created by Farmer Zhu on 2020/8/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMobileCodeManager{
    
    //验证码类型
    enum MobileCodeType: String{
        case CODE = "code"
        case PASSWORD = "resetpwd"
    }
    
    //通过号码发短信
    static func codeByMobile(mobile: String, codeType: MobileCodeType, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        LuckyHttpManager.getTime { (timestamp) in
            if("" == timestamp){
                fail(LuckyHttpManager.defaultFailMessage)
            }else{
                //封装token
                let encodeStr = "\(LuckyHttpManager.appKey)\(timestamp)\(mobile)\(codeType.rawValue)"
                let desString = LuckyEncodingUtil.getDes(encodeStr)
                let token = LuckyEncodingUtil.getBase64("\(LuckyHttpManager.device)000000\(timestamp)\(desString)")
                
                LuckyHttpManager.get("front/sms/sendCode", params: ["mobile": LuckyEncodingUtil.getBase64(mobile), "codeType": LuckyEncodingUtil.getBase64(codeType.rawValue), "token": token], timeout: LuckyHttpManager.defaultTimeout, data: { (data) in
                    let dataDic = data as! NSDictionary
                    if("SUCCESS" == dataDic["status"] as! String){
                        if(dataDic["data"] != nil){
                            success(String.valueOf(any: dataDic["data"]))
                        }else{
                            fail(LuckyHttpManager.defaultFailMessage)
                        }
                    }else{
                        fail(dataDic["message"] as! String)
                    }
                }) { (error) in
                    fail(LuckyHttpManager.defaultFailMessage)
                }
            }
        }
    }
    
    //通过用户信息发短信
    static func codeByUser(success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        LuckyHttpManager.getToken(user: globalUserData!) { (token) in
            LuckyHttpManager.get("front/sms/sendCodeForUser", params: ["token": token], timeout: LuckyHttpManager.defaultTimeout, data: { (data) in
                let dataDic = data as! NSDictionary
                if("SUCCESS" == dataDic["status"] as! String){
                    if(dataDic["data"] != nil){
                        success(String.valueOf(any: dataDic["data"]))
                    }else{
                        fail(LuckyHttpManager.defaultFailMessage)
                    }
                }else{
                    fail(dataDic["message"] as! String)
                }
            }) { (error) in
                fail(LuckyHttpManager.defaultFailMessage)
            }
        }
    }
}

