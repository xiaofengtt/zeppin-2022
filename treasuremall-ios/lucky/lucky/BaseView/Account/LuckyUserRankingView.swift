//
//  LuckyUserRankingView.swift
//  lucky
//  用户排名
//  Created by Farmer Zhu on 2020/8/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyUserRankingView: UIView{
    
    var leftImageView: UIImageView!
    var label: UILabel!
    var enterImageView: UIImageView!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.mainLightYellow()
        self.layer.masksToBounds = true
        self.layer.cornerRadius = frame.height/2
        
        //图标
        leftImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 2 * screenScale, width: frame.height - 4 * screenScale, height: frame.height - 4 * screenScale))
        leftImageView.image = UIImage(named: "image_ranking")
        self.addSubview(leftImageView)
        
        //内容
        label = UILabel(frame: CGRect(x: leftImageView.frame.origin.x + leftImageView.frame.width + 4 * screenScale, y: 0, width: 100 * screenScale, height: frame.height))
        label.textColor = UIColor.fontRed()
        label.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        self.addSubview(label)
        
        //进入三角
        enterImageView = UIImageView(frame: CGRect(x: label.frame.origin.x + label.frame.width + 6 * screenScale, y: 6 * screenScale, width: (frame.height - 12 * screenScale) * 0.75, height: frame.height - 12 * screenScale))
        enterImageView.image = UIImage(named: "image_enter_yellow")
        self.addSubview(enterImageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //设置内容
    func setText(text: String){
        self.label.text = text
        self.label.sizeToFit()
        self.label.frame.size = CGSize(width: self.label.frame.width, height: self.frame.height)
        
        self.enterImageView.frame.origin = CGPoint(x: self.label.frame.origin.x + self.label.frame.width + 6 * screenScale, y: self.enterImageView.frame.origin.y)
        self.frame.size = CGSize(width: self.enterImageView.frame.origin.x + enterImageView.frame.width + 10 * screenScale, height: self.frame.height)
    }
}
