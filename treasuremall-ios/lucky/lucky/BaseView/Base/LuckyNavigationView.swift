//
//  LuckyNavigationView.swift
//  lucky
//  泛用页头
//  Created by Farmer Zhu on 2020/8/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import UIKit
class LuckyNavigationView: UIView {
    var titleLabel: UILabel!
    var backButton: UIButton!
    var rightButton: UIButton!
    var splitLine: CALayer!
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: statusBarHeight + abs(navigationFrame.height))))
        self.backgroundColor = UIColor.mainYellow()
        self.layer.zPosition = 0.8
        
        //标题
        titleLabel = UILabel(frame: CGRect(x: 70 * screenScale, y: statusBarHeight + 5 * screenScale, width: self.frame.width - 140 * screenScale, height: 30 * screenScale))
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        titleLabel.textColor = UIColor.fontBlack()
        self.addSubview(titleLabel)
        
        //左按钮
        backButton = UIButton(frame: CGRect(x: 0, y: titleLabel.frame.origin.y, width: 50 * screenScale, height: titleLabel.frame.height))
        let backIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_black")
        backButton.addSubview(backIconView)
        self.addSubview(backButton)
        
        //右按钮
        rightButton = UIButton(frame: CGRect(x: navigationFrame.width - backButton.frame.width - 10 * screenScale, y: backButton.frame.origin.y, width: backButton.frame.width, height: backButton.frame.height))
        rightButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        rightButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        rightButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.right
        self.addSubview(rightButton)
        
        //底部分割线
        splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: self.frame.height - 1 * screenScale, width: self.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(splitLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    init() {
        super.init(frame: CGRect.zero)
    }
}
