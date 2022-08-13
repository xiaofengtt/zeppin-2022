	//
//  Alipay.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/9/25.
//  Copyright © 2015年 zeppin. All rights reserved.
//

import UIKit

class AlipayOrder : NSObject{
    var partner : String
    var seller : String
    var tradeNO : String
    var productName : String
    var productDescription : String
    var amount : String
    var notifyURL : String
    var service : String
    var paymentType : String
    var inputCharset : String
    var itBPay : String
    var showUrl : String
    var rsaDate : String?
    var appID : String?
    
    override init() {
        self.partner = ""
        self.seller = ""
        self.tradeNO = ""
        self.productName = ""
        self.productDescription = ""
        self.amount = ""
        self.notifyURL = ""
        self.service = ""
        self.paymentType = ""
        self.inputCharset = ""
        self.itBPay = ""
        self.showUrl = ""
    }
    
    func toString() -> String{
        var string = ""
        string.appendContentsOf("partner=\"\(self.partner)\"")
        string.appendContentsOf("&seller_id=\"\(self.seller)\"")
        string.appendContentsOf("&out_trade_no=\"\(self.tradeNO)\"")
        string.appendContentsOf("&subject=\"\(self.productName)\"")
        string.appendContentsOf("&body=\"\(self.productDescription)\"")
        string.appendContentsOf("&total_fee=\"\(self.amount)\"")
        string.appendContentsOf("&notify_url=\"\(self.notifyURL)\"")
        string.appendContentsOf("&service=\"\(self.service)\"")
        string.appendContentsOf("&payment_type=\"\(self.paymentType)\"")
        string.appendContentsOf("&_input_charset=\"\(self.inputCharset)\"")
        string.appendContentsOf("&it_b_pay=\"\(self.itBPay)\"")
        string.appendContentsOf("&show_url=\"\(self.showUrl)\"")
        if(rsaDate != nil){
            string.appendContentsOf("&sign_date=\"\(self.rsaDate)\"")
        }
        if(appID != nil){
            string.appendContentsOf("&app_id=\"\(self.appID)\"")
        }
        return string
    }
}

class AlipayController{
    let partner = "2088021828014590"
    let seller = "rongjingfeng@zeppin.cn"
    let privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPYjArC1ZAJyF7UkMVxEcgxzJ0ejazJMc1yecV3sqCs7fqogaFClspelUEb+e15ym4vbLEPuMDji5Jdl5OXMzVvW6JIncI/2Xp9tNZWZNxuWWuXvoiLuHMcvhpKpxM6qdMi/pIhQqqQtXcJc6PcMYTcGMoOc22029V/QAR8RjF1xAgMBAAECgYBIFjThgB6MQNIEsVbriPhGASvN5WSt1Ios5BKbyYXvM3uDY/5pMD4//6ClSj7jiHlZ2pT6SDZDUuBUHvmM/BBgdGT+uENakz6CP1oS1eUgaiinsxdEGFd5ait+ht5kqIH4SSSgabHCqiEDHwQqQA7JlfGlzeGVIg4VWhKZ5dJMYQJBAPw3K9Ck+QHRolWeGMgcMnq5WNV4SMymb9aUsUagWX7uGG8p/0FB3qevXx76X4EykpgKt/GvaUjau0TEv0+EPn0CQQD51H08cHu9Id5dSgwrL+ZRC/yAGjs7ot1yimzrXABZIcsUy2Sr1ERdAZIAuO6AT8cEnNlOTDmoi85XbwDsM8kFAkBJLZzJ2cPh0jg+jTN1hDDlSLfMoCzHLBdQ9C2HZ2jwGhb+0fmcrobyskBwFYb2Tn0YHiwGtLVgjQ4+wrMbWCxlAkEAj0xxlTRT1XVSzaHGfxMXgY9lgrkJFrjhWmzJ8uovjPCUQtYzZVf46nwXGfD5ZIHd/uhUwNN6ExI2BfK2zcUaXQJADneNqBMs3w5J0wp9drVZ+G8/zJypJh3CtiK16xFbX6vMVXq2b+nBAWj3TbMurRe+SmEs+Ygzh0IHczEQyOLGcg=="
    
    func pay(){
        let Orders = AlipayOrder()
        Orders.partner = partner
        Orders.seller = seller
        Orders.productName = "商品名称"
        Orders.productDescription = "商品描述"
        Orders.amount = NSString(format: "%.2f",0.01) as String
        Orders.tradeNO = "123456789012345"
        Orders.notifyURL = "http://api.zixueku.cn"
        Orders.service = "mobile.securitypay.pay"
        Orders.paymentType = "1"
        Orders.inputCharset = "utf-8"
        Orders.itBPay = "30m"
        Orders.showUrl = "m.alipay.com"
        let appScheme = "zixueku"
        let orderSpec : String = Orders.toString()
        let signer = CreateRSADataSigner(privateKey)
        let signedString = signer.signString(orderSpec)
        let orderString = "\(orderSpec)&sign=\"\(signedString)\"&sign_type=\"RSA\""
        
        AlipaySDK.defaultService().payOrder(orderString, fromScheme: appScheme, callback: { (resultDic) -> Void in
            if let Alipayjson = resultDic as? NSDictionary{
                let resultStatus = Alipayjson.valueForKey("resultStatus") as! String
                print(resultStatus)
//                if resultStatus == "9000"{
//                    self.AlipayinfoFunc("\(self.tradeNo)", trade_no: "\(self.tradeNo)", trade_status: resultStatus)
//                }else if resultStatus == "8000" {
//                    self.infoNotice("正在处理中")
//                    self.navigationController?.popViewControllerAnimated(true)
//                }else if resultStatus == "4000" {
//                    self.infoNotice("订单支付失败")
//                    self.navigationController?.popViewControllerAnimated(true)
//                }else if resultStatus == "6001" {
//                    self.infoNotice("用户中途取消")
//                    self.navigationController?.popViewControllerAnimated(true)
//                }else if resultStatus == "6002" {
//                    self.infoNotice("网络连接出错")
//                    self.navigationController?.popViewControllerAnimated(true)
//                }
            }
        })
    }
}