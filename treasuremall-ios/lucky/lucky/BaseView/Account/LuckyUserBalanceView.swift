//
//  LuckyUserBalanceView.swift
//  lucky
//  用户余额
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserBalanceView: UIView{
    var balanceLabel: UILabel!
    var balanceImageView: UIImageView!
    var button: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //余额值
        balanceLabel = UILabel(frame: CGRect(x: 0, y: (self.frame.height - 60 * screenScale)/2, width: 0, height: 40 * screenScale))
        balanceLabel.textColor = UIColor.fontBlack()
        balanceLabel.font = UIFont.boldFont(size: 24 * screenScale)
        balanceLabel.textAlignment = NSTextAlignment.center
        self.addSubview(balanceLabel)
        
        //图标
        balanceImageView = UIImageView(frame: CGRect(x: 0, y: balanceLabel.frame.origin.y + 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        if(globalFlagUser){
            balanceImageView.image = UIImage(named: "image_balance")
        }else{
            balanceImageView.image = UIImage(named: "image_balance_dollor")
        }
        self.addSubview(balanceImageView)
        
        //标题
        let nameLabel = UILabel(frame: CGRect(x: 0, y: balanceLabel.frame.origin.y + balanceLabel.frame.height, width: frame.width, height: 20 * screenScale))
        nameLabel.text = NSLocalizedString("Balance", comment: "")
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.center
        self.addSubview(nameLabel)
        
        //赋初值
        setBalance(balance: "0")
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //赋余额值
    func setBalance(balance: String){
        balanceLabel.text = balance
        balanceLabel.sizeToFit()
        balanceLabel.frame = CGRect(x: (self.frame.width - (balanceLabel.frame.width + balanceImageView.frame.width + 2 * screenScale))/2, y: balanceLabel.frame.origin.y, width: balanceLabel.frame.width, height: 40 * screenScale)
        
        balanceImageView.frame.origin = CGPoint(x: balanceLabel.frame.origin.x + balanceLabel.frame.width + 2 * screenScale, y: balanceImageView.frame.origin.y)
    }
}
