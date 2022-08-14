//
//  LuckyUserFunctionCellView.swift
//  lucky
//  用户功能Cell
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserFunctionCellView: UIView{
    var imageView: UIImageView!
    var textLabel: UILabel!
    var button: UIButton!
    var bottomLine: CALayer!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //进入三角
        let enterIconView = UIImageView(frame: CGRect(x: frame.width - 20 * screenScale, y: (frame.height - 13 * screenScale)/2, width: 8 * screenScale, height: 13 * screenScale))
        enterIconView.image = UIImage(named: "image_enter_gray")
        self.addSubview(enterIconView)
        
        //图
        imageView = UIImageView(frame: CGRect(x: 12 * screenScale, y: (frame.height - 18 * screenScale)/2 , width: 18 * screenScale, height: 18 * screenScale))
        self.addSubview(imageView)
        
        //功能名
        textLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: 0, width: frame.width, height: frame.height))
        textLabel.textColor = UIColor.fontBlack()
        textLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(textLabel)
        
        //底部分割线
        bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 10 * screenScale, y: frame.height - 1, width: frame.width - 20 * screenScale, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
