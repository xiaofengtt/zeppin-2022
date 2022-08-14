//
//  DataInit.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reser ved.
//

import Foundation

let UrlBase = "https://api.niutoulicai.com/web/"
let SourceBase = "https://backadmin.niutoulicai.com/"
//let UrlBase = "http://192.168.1.120:8080/NTB/rest/web/"
//let SourceBase = "http://192.168.1.120:8080/NTB/"
let secretKey = "27739700ee0bf2930cd62d72a80def0a"
let systemType = "03"
var screenWidth: CGFloat = UIScreen.main.applicationFrame.size.width
var screenHeight: CGFloat = UIScreen.main.bounds.size.height
var screenScale: CGFloat = UIScreen.main.applicationFrame.size.width / 375

var user: UserModel? = nil
var userAccount:  UserAccountModel? = nil
var phoneNum: String = ""
var userUuid: String? = nil
let webmarket = "appStore"
let version = 1
var isPublish = false

func isIphoneX() -> Bool{
    return UIScreen.main.currentMode!.size.equalTo(CGSize(width: 1125, height: 2436))
}
