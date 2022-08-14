//
//  LuckySearchKeywordButton.swift
//  lucky
//  搜索关键词 暂无
//  Created by Farmer Zhu on 2020/9/8.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckySearchKeywordButton: UIButton{
    var keyword: String!
    
    init(frame: CGRect, keyword: String, isHot: Bool) {
        super.init(frame: frame)
        self.keyword = keyword
        
        self.backgroundColor = UIColor.backgroundGray()
        self.layer.masksToBounds = true
        self.layer.cornerRadius = frame.height/2
        
        //热门 显示热门标志
        let imageView = UIImageView()
        if(isHot){
            imageView.frame = CGRect(x: 10 * screenScale, y: (frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale)
            imageView.image = UIImage(named: "image_search_hot")
        }
        self.addSubview(imageView)
        
        //关键词
        let label = UILabel()
        label.text = keyword
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        label.sizeToFit()
        
        //按钮尺寸计算
        if(isHot){
            label.frame = CGRect(x: imageView.frame.origin.x + imageView.frame.width + 6 * screenScale, y: 0, width: label.frame.width, height: frame.height)
            self.frame.size = CGSize(width: label.frame.origin.x + label.frame.width + 10 * screenScale, height: frame.height)
        }else{
            label.frame = CGRect(x: 12 * screenScale, y: 0, width: label.frame.width, height: frame.height)
            self.frame.size = CGSize(width: label.frame.origin.x + label.frame.width + 12 * screenScale, height: frame.height)
        }
        self.addSubview(label)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
