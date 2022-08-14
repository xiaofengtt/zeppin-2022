//
//  LuckyTableEmptyView.swift
//  lucky
//  列表无内容视图
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTableEmptyView: UIView{
    init(frame: CGRect, title: String, image: UIImage?) {
        super.init(frame: frame)
        
        //无内容图
        let imageView = UIImageView(frame: CGRect(x: frame.width/3, y: frame.height/4, width: frame.width/3, height: frame.width/3))
        imageView.image = image
        self.addSubview(imageView)
        
        //无内容文本
        let label = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 10 * screenScale, width: frame.width, height: 20 * screenScale))
        label.text = title
        label.textColor = UIColor.fontGray()
        label.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        label.textAlignment = NSTextAlignment.center
        self.addSubview(label)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
