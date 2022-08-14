//
//  LuckyActivityTitleView.swift
//  lucky
//  活动 标题
//  Created by Farmer Zhu on 2020/10/22.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityTitleView: UIView{
    
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        
        //图 点
        let imageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: (frame.height - 13 * screenScale)/2, width: 12 * screenScale, height: 13 * screenScale))
        imageView.image = UIImage(named: "image_activity_rewards_point")
        self.addSubview(imageView)
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: 0, width: frame.width - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale), height: frame.height))
        titleLabel.text = title
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.boldFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
