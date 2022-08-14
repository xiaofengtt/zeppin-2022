//
//  AdView.swift
//  score
//
//  Created by Farmer Zhu on 2019/7/1.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class AdView: UIView {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        self.layer.zPosition = 1.1
        
        let bkView = UIImageView(frame: CGRect(origin: CGPoint(x: 0, y: UIApplication.shared.keyWindow!.safeAreaInsets.top), size: CGSize(width: frame.width, height: frame.height - bottomSafeHeight - UIApplication.shared.keyWindow!.safeAreaInsets.top)))
        bkView.image = UIImage(named: "launch")
        self.addSubview(bkView)
        
        let adBackgroundView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: frame.width, height: frame.height - 100 * screenScale - bottomSafeHeight * 2)))
        adBackgroundView.image = UIImage(named: "ad_background")
        self.addSubview(adBackgroundView)
        
        let closeButton = UIButton(frame: CGRect(x: screenWidth * 0.8, y: 50 * screenScale, width: 50 * screenScale, height: 30 * screenScale))
        closeButton.layer.cornerRadius = closeButton.frame.height/2
        closeButton.layer.masksToBounds = true
        closeButton.backgroundColor = UIColor.lightGray.withAlphaComponent(0.7)
        closeButton.setTitle("跳过", for: UIControl.State.normal)
        closeButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        closeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        closeButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        self.addSubview(closeButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func show(){
        UIApplication.shared.keyWindow?.addSubview(self)
        Timer.scheduledTimer(withTimeInterval: 3, repeats: false) { (timer) in
            self.removeFromSuperview()
        }
    }
    
    @objc func close(){
        self.removeFromSuperview()
    }
}
