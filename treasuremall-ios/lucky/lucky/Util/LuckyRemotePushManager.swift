//
//  LuckyRemotePushManager.swift
//  lucky
//  远程推送管理器
//  Created by Farmer Zhu on 2021/1/13.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation
import UserNotifications

class LuckyRemotePushManager{
    
    //上传推送token
    static func uploadToken(deviceToken: String){
        //判断本机id
        if(globalDeviceUuid != nil){
            LuckyHttpManager.getTime { (time) in
                //封装token base64(device+time+des(key+ime+user))
                var desBase = LuckyHttpManager.appKey + time
                if let userData = LuckyLocalDataManager.getLocationData(){
                    desBase = desBase + userData.uuid
                }
                let desString = LuckyEncodingUtil.getDes(desBase)
                let token = LuckyEncodingUtil.getBase64(LuckyHttpManager.device + time + desString)
                LuckyHttpManager.postWithoutToken("front/login/checkin", params: ["token": token, "deviceToken": deviceToken, "uniqueToken": globalDeviceUuid!]) { (data) in
                } fail: { (reason) in
                }
            }
            return
        }
    }
    
    //检查推送开关状态
    static func checkStatus(){
        UNUserNotificationCenter.current().getNotificationSettings { (settings) in
            if(settings.authorizationStatus != UNAuthorizationStatus.authorized){
                //如果未开启展示引导页
                DispatchQueue.main.async {
                    let settingView = LuckyPushSettingView()
                    UIApplication.shared.keyWindow?.addSubview(settingView)
                }
            }
        }
    }
    
    //跳转设置
    static func toSetting(){
        if let url = URL(string: UIApplication.openSettingsURLString){
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }
}
