//
//  LuckyChargeAmountCellView.swift
//  lucky
//  充值金额Cell
//  Created by Farmer Zhu on 2020/8/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyChargeAmountCellView: UIView{
    let paddingLeft: CGFloat = 20 * screenScale
    
    var data : LuckyFrontUserRechargeAmountSetModel!
    var button: UIButton!
    var amountLabel: UILabel!
    
    init(frame: CGRect, data: LuckyFrontUserRechargeAmountSetModel?) {
        super.init(frame: frame)
        self.data = data
        
        //图标
        let iconView = UIImageView(frame: CGRect(x: paddingLeft, y: (frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
        if(globalFlagUser){
            iconView.image = UIImage(named: "image_gold_coin")
        }else{
            iconView.image = UIImage(named: "image_gold_dollor")
        }
        self.addSubview(iconView)
        
        //按钮
        button = UIButton(frame: CGRect(x: frame.width - paddingLeft - 92 * screenScale, y: (frame.height - 28 * screenScale)/2, width: 92 * screenScale, height: 28 * screenScale))
        button.backgroundColor = UIColor.fontRed()
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 2 * screenScale
        if(data != nil){
            //有数据 显示金额
            //汇率换算
            var amount: Double = data!.amount
            var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
            
            if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
                amount = data!.amount * Double.valueOf(any: rate)
                symbol = String.valueOf(any:globalCurrencyRate!.currencySymbol[globalCurrencyCode])
            }else{
                amount = data!.amount
            }
            
            if(globalFlagUser){
                //主包 显示换算后金额
                button.setTitle("\(symbol) \(LuckyUtils.currencyFormat(amount: amount))", for: UIControl.State.normal)
            }else{
                button.setTitle(NSLocalizedString("Top Up", comment: ""), for: UIControl.State.normal)
            }
        }else{
            //无数据 自由额度
            button.setTitle(NSLocalizedString("Top Up", comment: ""), for: UIControl.State.normal)
        }
        button.setTitleColor(UIColor.white, for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(button)
        
        //金额
        amountLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 16 * screenScale, y: 0, width: button.frame.origin.x - (iconView.frame.origin.x + iconView.frame.width + 16 * screenScale), height: frame.height))
        if(data != nil){
            //有数据 显示金额
            if(globalFlagUser){
                //主包 金币数
                amountLabel.text = "\(LuckyUtils.coinFormat(amount: data!.dAmount + data!.giveDAmount)) \(NSLocalizedString("coins", comment: ""))"
            }else{
                //马甲 金额
                amountLabel.text = "$ \(LuckyUtils.currencyFormat(amount: data!.amount))"
            }
        }else{
            //无数据 自由额度
            amountLabel.text = NSLocalizedString("Other amount", comment: "")
        }
        amountLabel.textColor = UIColor.fontBlack()
        amountLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(amountLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
