//
//  LuckyActivityAlertRewardsView.swift
//  lucky
//  活动 抽奖 中奖提示
//  Created by Farmer Zhu on 2020/10/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityAlertRewardsView: UIView{
    
    var enterButton: UIButton!
    var closeButton: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.layer.zPosition = 0.9
        
        //背景
        let bgLayer = CALayer()
        bgLayer.frame = CGRect(origin: CGPoint.zero, size: frame.size)
        bgLayer.backgroundColor = UIColor.black.withAlphaComponent(0.6).cgColor
        self.layer.addSublayer(bgLayer)
        
        //底图
        let imageView = UIImageView(frame: CGRect(x: (frame.width - 325 * screenScale)/2, y: screenHeight * 0.15, width: 325 * screenScale, height: 322 * screenScale))
        imageView.image = UIImage(named: "image_alert_rewards")
        self.addSubview(imageView)
        enterButton = UIButton(frame: imageView.frame)
        self.addSubview(enterButton)
        
        //关闭
        closeButton = UIButton(frame: CGRect(x: (frame.width - 28 * screenScale)/2, y: imageView.frame.origin.y + imageView.frame.height + 20 * screenScale, width: 28 * screenScale, height: 28 * screenScale))
        closeButton.setImage(UIImage(named: "image_alert_close"), for: UIControl.State.normal)
        self.addSubview(closeButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
