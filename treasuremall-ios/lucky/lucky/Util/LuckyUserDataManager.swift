//
//  LuckyUserDataManager.swift
//  lucky
//  用户信息管理
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserDataManager{
    //获取用户信息
    static func getUserData(success: @escaping (_ data: LuckyFrontUserModel) -> (), error: @escaping (_ reason: String) -> (), fail: @escaping (_ reason: String) -> ()){
        LuckyHttpManager.getWithToken("front/user/get", params: NSDictionary(), success: { (data) in
            let dataDic = data as! NSDictionary
            let frontUser = LuckyFrontUserModel(data: dataDic)
            success(frontUser)
        }, fail: { (reason) in
            if(reason.contains("500")){
                error(reason)
            }else{
                fail(reason)
            }
        })
    }
    
    //获取用户账户信息
    static func getUserAccount(success: @escaping (_ data: LuckyFrontUserAccountModel) -> (), fail: @escaping (_ reason: String) -> ()){
        LuckyHttpManager.getWithToken("front/userAccount/get", params: NSDictionary(), success: { (data) in
            let dataDic = data as! NSDictionary
            let userAccount = LuckyFrontUserAccountModel(data: dataDic)
            success(userAccount)
        }, fail: { (reason) in
            fail(reason)
        })
    }
    
    //校验登录状态
    static func checkLogin(user: LuckyFrontUserModel, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        LuckyHttpManager.getTime { (timestamp) in
            if("" == timestamp){
                fail(LuckyHttpManager.defaultFailMessage)
            }else{
                //拼装token base64(device+time+des(key+time+mobile+uuid))
                let desString = LuckyEncodingUtil.getDes("\(LuckyHttpManager.appKey)\(timestamp)\(user.mobile)\(user.uuid)")
                let token = LuckyEncodingUtil.getBase64("\(LuckyHttpManager.device)\(timestamp)\(desString)")
                
                LuckyHttpManager.post("front/login/auth", params: ["mobile": LuckyEncodingUtil.getBase64(user.mobile), "uuid": user.uuid, "token": token], timeout: 20, data: { (adata) in
                    let dataDic = adata as! NSDictionary
                    if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                        if(dataDic["data"] != nil){
                            success("SUCCESS")
                        }else{
                            fail(String.valueOf(any: dataDic["message"]))
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }) { (error) in
                    fail(LuckyHttpManager.defaultFailMessage)
                }
            }
        }
    }
    
    //启动时用户校验 三次
    static func auth(user: LuckyFrontUserModel, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        checkLogin(user: user) { (userData) in
            success(userData)
        } fail: { (reason) in
            if(reason == LuckyHttpManager.defaultFailMessage){
                checkLogin(user: user) { (userData) in
                    success(userData)
                } fail: { (reason) in
                    if(reason == LuckyHttpManager.defaultFailMessage){
                        checkLogin(user: user) { (data) in
                            success(data)
                        } fail: { (reason) in
                            fail(reason)
                        }
                    }else{
                        fail(reason)
                    }
                }
            }else{
                fail(reason)
            }
        }
    }
}
