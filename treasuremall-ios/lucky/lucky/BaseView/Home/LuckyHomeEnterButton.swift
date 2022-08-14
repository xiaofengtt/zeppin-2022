//
//  LuckyHomeEnterButton.swift
//  lucky
//  首页入口按钮
//  Created by Farmer Zhu on 2020/9/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyHomeEnterButton: UIButton{
    
    init(frame: CGRect, title: String, image: UIImage?) {
        super.init(frame: frame)
        
        //图
        let imageView = UIImageView(frame: CGRect(x: frame.width/4, y: (frame.height - (frame.width/2 + 14 * screenScale))/2, width: frame.width/2, height: frame.width/2))
        imageView.image = image
        self.addSubview(imageView)
        
        //模块名
        let titleLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height, width: frame.width, height: 14 * screenScale))
        titleLabel.text = title
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        self.addSubview(titleLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
