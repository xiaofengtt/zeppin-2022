//
//  LuckyHomeTitleView.swift
//  lucky
//  首页功能模块标题
//  Created by Farmer Zhu on 2020/8/31.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation


class LuckyHomeTitleView: UIView{
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        
        //标题内容
        let label = UILabel()
        label.text = title
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.boldFont(size: UIFont.fontSizeBiggest() * screenScale)
        label.sizeToFit()
        label.frame = CGRect(x: 12 * screenScale, y: 0, width: label.frame.width, height: frame.height)
        
        //背景
        let bgImageView = UIImageView(frame: CGRect(x: 6 * screenScale, y: frame.height/3, width: label.frame.width + 12 * screenScale, height: frame.height/3*2))
        bgImageView.image = UIImage(named: "image_home_title_bg")
        self.addSubview(bgImageView)
        self.addSubview(label)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
