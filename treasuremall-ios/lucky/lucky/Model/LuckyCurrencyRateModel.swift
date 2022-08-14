//
//  LuckyCurrencyRateModel.swift
//  lucky
//  货币符号及汇率
//  Created by Farmer Zhu on 2020/12/30.
//  Copyright © 2020 shopping. All rights reserved.
//


import Foundation

class LuckyCurrencyRateModel : NSObject{
    //币种汇率
    var exchangeRate: NSDictionary
    //币种符号
    var currencySymbol: NSDictionary
    
    init(data: Any?) {
        if let exchangeRateArray = data as? Array<Dictionary<String, Any>>{
            let exchangeRateDic = NSMutableDictionary()
            let currencySymbolDic = NSMutableDictionary()
            for er in exchangeRateArray{
                exchangeRateDic[er["currencyCode"]!] = er["realRate"]
                currencySymbolDic[er["currencyCode"]!] = er["symbol"]
            }
            
            self.exchangeRate = exchangeRateDic
            self.currencySymbol = currencySymbolDic
        }else{
            self.exchangeRate = NSDictionary()
            self.currencySymbol = NSDictionary()
        }
    }
}
