//
//  LuckyWithdrawPoundageLabel.swift
//  lucky
//  提现手续费
//  Created by Farmer Zhu on 2020/9/7.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyWithdrawPoundageLabel: UILabel{
    var poundage: Double!
    
    init(frame: CGRect, poundage: Double) {
        super.init(frame: frame)
        self.poundage = poundage
        
        //汇率转换
        var amount: Double = poundage
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            amount = poundage * Double.valueOf(any: rate)
            symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
        }else{
            amount = poundage
        }
        
        self.text = "\(NSLocalizedString("Withdrawal Fee", comment: "")):\(symbol) \(LuckyUtils.currencyFullFormat(amount: amount))"
        self.textColor = UIColor.fontDarkGray()
        self.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //赋值
    func setValue(poundage: Double){
        self.poundage = poundage
        
        //费率转换
        var amount: Double = poundage
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            amount = poundage * Double.valueOf(any: rate)
            symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
        }else{
            amount = poundage
        }
        
        self.text = "\(NSLocalizedString("Withdrawal Fee", comment: "")):\(symbol) \(LuckyUtils.currencyFullFormat(amount: amount))"
    }
}
