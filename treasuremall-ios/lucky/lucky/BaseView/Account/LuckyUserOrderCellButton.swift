//
//  LuckyUserOrderCellButton.swift
//  lucky
//  用户订单入口按钮
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserOrderCellButton: UIView{
    var imageView: UIImageView!
    var textLabel: UILabel!
    var button: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //内容
        textLabel = UILabel(frame: CGRect(x: 0, y: frame.height - 34 * screenScale, width: frame.width, height: 20 * screenScale))
        textLabel.textColor = UIColor.fontBlack()
        textLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        textLabel.textAlignment = NSTextAlignment.center
        self.addSubview(textLabel)
        
        //图
        imageView = UIImageView(frame: CGRect(x: (frame.width - 30 * screenScale)/2, y: 10 * screenScale + (textLabel.frame.origin.y - 44 * screenScale)/2 , width: 30 * screenScale, height: 30 * screenScale))
        self.addSubview(imageView)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
