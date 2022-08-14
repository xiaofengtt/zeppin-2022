//
//  LuckyBalanceLabelView.swift
//  lucky
//  提现 余额
//  Created by Farmer Zhu on 2020/9/7.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyBalanceLabelView: UIView{
    
    var balanceLabel: UILabel!
    
    init(frame: CGRect, balance: Double, rate: Double) {
        super.init(frame: frame)
        
        //费率转换
        var amount: Double = balance / rate
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        
        if let currencyRate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            amount = balance / rate * Double.valueOf(any: currencyRate)
            symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
        }else{
            amount = balance/rate
        }
        
        //拼接内容
        let signLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: frame.height))
        signLabel.text = "("
        signLabel.textColor = UIColor.fontBlack()
        signLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        signLabel.sizeToFit()
        signLabel.frame.size = CGSize(width: signLabel.frame.width, height: frame.height)
        self.addSubview( signLabel)
        
        let coinImageView = UIImageView(frame: CGRect(x: signLabel.frame.origin.x + signLabel.frame.width, y: (frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        if(globalFlagUser){
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        self.addSubview(coinImageView)
        
        balanceLabel = UILabel(frame: CGRect(x: coinImageView.frame.origin.x + coinImageView.frame.width + 2 * screenScale, y: 0, width: frame.width - (coinImageView.frame.origin.x + coinImageView.frame.width + 2 * screenScale), height: frame.height))
        balanceLabel.textColor = UIColor.fontDarkGray()
        balanceLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        let textString = "\(LuckyUtils.coinFullFormat(amount: balance))=\(symbol)\(LuckyUtils.currencyFullFormat(amount: amount)))"
        let balanceText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
        balanceText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: textString.count - "\(symbol)\(LuckyUtils.currencyFullFormat(amount: amount))".count - 1, length: "\(symbol)\(LuckyUtils.currencyFullFormat(amount: amount))".count))
        balanceLabel.attributedText = balanceText
        self.addSubview(balanceLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //赋值
    func setValue(balance: Double, rate: Double) {
        var amount: Double = balance / rate
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        
        //费率转换
        if let currencyRate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            amount = balance / rate * Double.valueOf(any: currencyRate)
            symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
        }else{
            amount = balance/rate
        }
        
        let textString = "\(LuckyUtils.coinFullFormat(amount: balance))=\(symbol)\(LuckyUtils.currencyFullFormat(amount: amount)))"
        let balanceText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
        balanceText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: textString.count - LuckyUtils.currencyFullFormat(amount: amount).count - 2, length: LuckyUtils.currencyFullFormat(amount: amount).count + 1))
        balanceLabel.attributedText = balanceText
    }
}
