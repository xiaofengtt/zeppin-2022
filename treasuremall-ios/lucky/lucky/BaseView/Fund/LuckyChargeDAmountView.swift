//
//  LuckyChargeDAmountView.swift
//  lucky
//  充值金币数
//  Created by Farmer Zhu on 2020/8/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyChargeDAmountView: UIView{
    
    var dAmountLabel: UILabel!
    var contentLabel: UILabel!
    var iconView: UIImageView!
    
    var dAmount: Double = 0
    
    override init(frame: CGRect){
        super.init(frame: frame)
        
        //拼接数据
        contentLabel = UILabel()
        contentLabel.text = NSLocalizedString("Get rewards of:", comment: "")
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.right
        contentLabel.sizeToFit()
        
        dAmountLabel = UILabel()
        dAmountLabel.text = "0"
        dAmountLabel.textColor = UIColor.fontRed()
        dAmountLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        dAmountLabel.sizeToFit()
        
        iconView = UIImageView()
        iconView.frame.size = CGSize(width: 18 * screenScale, height: 18 * screenScale)
        if(globalFlagUser){
            iconView.image = UIImage(named: "image_gold_coin")
        }else{
            iconView.image = UIImage(named: "image_gold_dollor")
        }
        
        //调整默认位置
        contentLabel.frame = CGRect(x: (frame.width - (contentLabel.frame.width + 8 * screenScale + dAmountLabel.frame.width + 4 * screenScale + iconView.frame.width))/2, y: 0, width: contentLabel.frame.width, height: frame.height)
        dAmountLabel.frame = CGRect(x: contentLabel.frame.origin.x + contentLabel.frame.width + 8 * screenScale, y: 0, width: dAmountLabel.frame.width, height: frame.height)
        iconView.frame.origin = CGPoint(x: dAmountLabel.frame.origin.x + dAmountLabel.frame.width + 4 * screenScale, y: (frame.height - 18 * screenScale)/2)
        
        self.addSubview(contentLabel)
        self.addSubview(dAmountLabel)
        self.addSubview(iconView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //设置金币数
    func setDAmount(dAmount: Double){
        dAmountLabel.text = LuckyUtils.coinFormat(amount: dAmount)
        dAmountLabel.sizeToFit()
        
        contentLabel.frame = CGRect(x: (frame.width - (contentLabel.frame.width + 8 * screenScale + dAmountLabel.frame.width + 4 * screenScale + iconView.frame.width))/2, y: 0, width: contentLabel.frame.width, height: frame.height)
        dAmountLabel.frame = CGRect(x: contentLabel.frame.origin.x + contentLabel.frame.width + 8 * screenScale, y: 0, width: dAmountLabel.frame.width, height: frame.height)
        iconView.frame.origin = CGPoint(x: dAmountLabel.frame.origin.x + dAmountLabel.frame.width + 4 * screenScale, y: (frame.height - 18 * screenScale)/2)
    }
}
