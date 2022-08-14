//
//  LuckyUserSettingCellView.swift
//  lucky
//  设置页功能Cell
//  Created by Farmer Zhu on 2020/8/21.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserSettingCellView: UIView{
    
    var titleLabel: UILabel!
    var imageView: UIImageView!
    var textLabel: UILabel!
    var button: UIButton!
    var bottomLine: CALayer!
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //标题
        titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: (frame.width - 2 * paddingLeft)/2, height: frame.height))
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(titleLabel)
        
        //进入三角
        let enterIconView = UIImageView(frame: CGRect(x: frame.width - 18 * screenScale, y: (frame.height - 13 * screenScale)/2, width: 8 * screenScale, height: 13 * screenScale))
        enterIconView.image = UIImage(named: "image_enter_gray")
        self.addSubview(enterIconView)
        
        //头像
        imageView = UIImageView(frame: CGRect(x: enterIconView.frame.origin.x - 38 * screenScale, y: (frame.height - 30 * screenScale)/2, width: 30 * screenScale, height: 30 * screenScale))
        imageView.layer.masksToBounds = true
        imageView.layer.cornerRadius = imageView.frame.width/2
        self.addSubview(imageView)
        
        //内容
        textLabel = UILabel(frame: CGRect(x: frame.width/2, y: titleLabel.frame.origin.y, width: enterIconView.frame.origin.x - 8 * screenScale - frame.width/2, height: titleLabel.frame.height))
        textLabel.textColor = titleLabel.textColor
        textLabel.font = titleLabel.font
        textLabel.textAlignment = NSTextAlignment.right
        self.addSubview(textLabel)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
        
        //底部分割
        bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
