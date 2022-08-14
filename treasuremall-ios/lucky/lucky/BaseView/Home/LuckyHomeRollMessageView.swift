//
//  LuckyHomeRollMessageView.swift
//  lucky
//  首页滚动信息(单条)
//  Created by Farmer Zhu on 2020/9/1.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyHomeRollMessageView: UIView{

    let font: UIFont = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
    let normalTextColor: UIColor = UIColor.fontBlack()
    let importTextColor: UIColor = UIColor.fontRed()
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        
        //拼装信息
        let conLabel = UILabel()
        conLabel.text = NSLocalizedString("Congratulations to", comment: "")
        conLabel.textColor = normalTextColor
        conLabel.font = font
        conLabel.sizeToFit()
        conLabel.frame = CGRect(x: 0, y: 0, width: conLabel.frame.width, height: frame.height)
        self.addSubview(conLabel)
        
        let nameLabel = UILabel()
        nameLabel.text = data.nickname
        nameLabel.textColor = importTextColor
        nameLabel.font = font
        nameLabel.sizeToFit()
        nameLabel.frame = CGRect(x: conLabel.frame.origin.x + conLabel.frame.width + 4 * screenScale, y: 0, width: nameLabel.frame.width, height: frame.height)
        self.addSubview(nameLabel)
        
        let forLabel = UILabel()
        forLabel.text = NSLocalizedString("for buying", comment: "")
        forLabel.textColor = normalTextColor
        forLabel.font = font
        forLabel.sizeToFit()
        forLabel.frame = CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width + 4 * screenScale, y: 0, width: forLabel.frame.width, height: frame.height)
        self.addSubview(forLabel)
        
        let goodsLabel = UILabel()
        goodsLabel.text = data.title
        goodsLabel.textColor = importTextColor
        goodsLabel.font = font
        goodsLabel.sizeToFit()
        goodsLabel.frame = CGRect(x: forLabel.frame.origin.x + forLabel.frame.width + 4 * screenScale, y: 0, width: goodsLabel.frame.width, height: frame.height)
        self.addSubview(goodsLabel)
        
        let worthLabel = UILabel()
        worthLabel.text = NSLocalizedString("for", comment: "")
        worthLabel.textColor = normalTextColor
        worthLabel.font = font
        worthLabel.sizeToFit()
        worthLabel.frame = CGRect(x: goodsLabel.frame.origin.x + goodsLabel.frame.width + 4 * screenScale, y: 0, width: worthLabel.frame.width, height: frame.height)
        self.addSubview(worthLabel)
        
        let moneyLabel = UILabel()
        if(globalFlagUser){
            moneyLabel.text = "\(LuckyUtils.coinFormat(amount: data.dPrice)) \(NSLocalizedString("coins", comment: ""))."
        }else{
            moneyLabel.text = "\(LuckyUtils.coinFormat(amount: data.price)) \(NSLocalizedString("dollars", comment: ""))."
        }
        moneyLabel.textColor = importTextColor
        moneyLabel.font = font
        moneyLabel.sizeToFit()
        moneyLabel.frame = CGRect(x: worthLabel.frame.origin.x + worthLabel.frame.width + 4 * screenScale, y: 0, width: moneyLabel.frame.width, height: frame.height)
        self.addSubview(moneyLabel)
        self.frame.size = CGSize(width: moneyLabel.frame.origin.x + moneyLabel.frame.width + 20 * screenScale, height: frame.height)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
