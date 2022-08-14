//
//  LuckyUserTradeButton.swift
//  lucky
//  充值/提现入口按钮
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserTradeButton: UIView{
    var imageView: UIImageView!
    var textLabel: UILabel!
    var button: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //图
        imageView = UIImageView(frame: CGRect(x: (frame.width - 24 * screenScale)/2, y: (frame.height - 60 * screenScale)/2 + 8 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        self.addSubview(imageView)
        
        //文字
        textLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 8 * screenScale, width: frame.width, height: 20 * screenScale))
        textLabel.textColor = UIColor.fontBlack()
        textLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        textLabel.textAlignment = NSTextAlignment.center
        self.addSubview(textLabel)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
