//
//  LuckyAddNewView.swift
//  lucky
//  添加新地址
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAddNewView: UIView{
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 4 * screenScale
        
        //图
        let imageView = UIImageView(frame: CGRect(x: (frame.width - 80 * screenScale)/2, y: 5 * screenScale, width: 80 * screenScale, height: 80 * screenScale))
        imageView.image = UIImage(named: "image_address_add")
        self.addSubview(imageView)
        
        //文
        let label = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 5 * screenScale, width: frame.width, height: 20 * screenScale))
        label.text = title
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        label.textAlignment = NSTextAlignment.center
        self.addSubview(label)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
