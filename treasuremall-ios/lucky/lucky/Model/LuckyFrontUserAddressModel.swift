//
//  LuckyFrontUserAddressModel.swift
//  lucky
//  用户地址
//  Created by Farmer Zhu on 2020/8/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFrontUserAddressModel : NSObject{
    var uuid: String
    var frontUser: String
    var area: String
    var areaScode: String
    var areaNameList: Array<String>
    var isDefault: Bool
    
    var internationalInfo: String
    var country: String
    var receiver: String
    var address: String
    var asub: String
    var city: String
    var state: String
    var zipcode: String
    var phone: String
    
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.frontUser = String.valueOf(any: data.value(forKey: "frontUser"))
        self.receiver = String.valueOf(any: data.value(forKey: "receiver"))
        self.area = String.valueOf(any: data.value(forKey: "area"))
        self.areaScode = String.valueOf(any: data.value(forKey: "areaScode"))
        if(data.value(forKey: "areaNameList") != nil && data.value(forKey: "areaNameList").debugDescription != "Optional(<null>)"){
            self.areaNameList = data.value(forKey: "areaNameList") as! Array<String>
        }else{
            self.areaNameList = []
        }
        self.address = String.valueOf(any: data.value(forKey: "address"))
        self.phone = String.valueOf(any: data.value(forKey: "phone"))
        self.isDefault = Bool.valueOf(any: data.value(forKey: "isDefault"))
        
        self.state = String.valueOf(any: data.value(forKey: "state"))
        self.asub = String.valueOf(any: data.value(forKey: "asub"))
        self.internationalInfo = String.valueOf(any: data.value(forKey: "internationalInfo"))
        self.zipcode = String.valueOf(any: data.value(forKey: "zipcode"))
        self.city = String.valueOf(any: data.value(forKey: "city"))
        self.country = String.valueOf(any: data.value(forKey: "country"))
    }
}
