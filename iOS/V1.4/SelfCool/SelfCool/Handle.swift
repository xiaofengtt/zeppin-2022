//
//  Handle.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func NumberToABC(number : Int) ->String{
    let dictionary = NSDictionary(dictionary: ["1" : "A" ,"2" : "B" ,"3" : "C" ,"4" : "D" ,"5" : "E" ,"6" : "F" ,"7" : "G" ,"8" : "H" ,"9" : "I" ,"10" : "J" ,"11" : "K" ,"12" : "L" ,"13" : "M" ,"14" : "N"])
    return dictionary.objectForKey(String(number)) as! String
}

func isTelNumber(num:NSString) -> Bool{
    let mobile = "^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$"
    let  CM = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$"
    let  CU = "^1(3[0-2]|5[256]|8[56])\\d{8}$"
    let  CT = "^1((33|53|8[09])[0-9]|349)\\d{7}$"
    let regextestmobile = NSPredicate(format: "SELF MATCHES %@",mobile)
    let regextestcm = NSPredicate(format: "SELF MATCHES %@",CM )
    let regextestcu = NSPredicate(format: "SELF MATCHES %@" ,CU)
    let regextestct = NSPredicate(format: "SELF MATCHES %@" ,CT)
    
    if ((regextestmobile.evaluateWithObject(num) == true) || (regextestcm.evaluateWithObject(num)  == true) || (regextestct.evaluateWithObject(num) == true) || (regextestcu.evaluateWithObject(num) == true)){
        return true
    }else{
        return false
    }
}