//
//  LuckyProgressBar.swift
//  lucky
//  进度条
//  Created by Farmer Zhu on 2020/9/1.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyProgressBar: UIView{
    
    var progressBar: UIView!
    
    //设置进度
    var rate: Double{
        willSet{
            progressBar.frame.size = CGSize(width: self.frame.width * CGFloat(newValue), height: self.frame.height)
        }
    }
    
    override init(frame: CGRect) {
        self.rate = 0
        super.init(frame: frame)
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 2 * screenScale
        //进度条底色
        self.backgroundColor = UIColor.backgroundGray()
        
        //进度条
        progressBar = UIView(frame: CGRect(x: 0, y: 0, width: 0, height: frame.height))
        progressBar.backgroundColor = UIColor.mainYellow()
        progressBar.layer.masksToBounds = true
        progressBar.layer.cornerRadius = self.layer.cornerRadius
        self.addSubview(progressBar)
    }
    
    required init(coder aDecoder: NSCoder) {
        self.rate = 0
        super.init(coder: aDecoder)!
    }
    
}
