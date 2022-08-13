//
//  WXPay.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/9/30.
//  Copyright © 2015年 zeppin. All rights reserved.
//

import UIKit

class WXpayController{
    let appId = "wx451427d0a7a64238"
    let mchId = "1273234301"
    let partnerId = "U2FsdGVkX18SHzNRQ9Yk7MhsgOibpIHZ"
    
    func getSign(){
        let request = payRequsestHandler()
        request.setId(appId, mch_id: mchId)
        request.setKey(partnerId)
        
        let dict = request.sendPay_demo()
        if dict == nil{
            UIAlertView(title: "支付出现错误", message: request.getDebugifo(), delegate: self, cancelButtonTitle: "确认").show()
        }else{
            let stamp = dict.objectForKey("timestamp") as! String
            
            let req = PayReq()
            req.openID = dict.objectForKey("appid") as! String
            req.partnerId = dict.objectForKey("partnerid") as! String
            req.prepayId = dict.objectForKey("prepayid") as! String
            req.nonceStr = dict.objectForKey("noncestr") as! String
            req.timeStamp = UInt32(stamp)!
            req.package = dict.objectForKey("package") as! String
            req.sign = dict.objectForKey("sign") as! String
            let sd = WXApi.sendReq(req)
            print(sd)
        }
    }

}