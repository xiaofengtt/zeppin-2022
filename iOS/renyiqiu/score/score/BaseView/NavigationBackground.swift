//
//  NavigationBackground.swift
//  ntlc
//
//  Created by farmer on 2017/12/12.
//  Copyright © 2017年 farmer. All rights reserved.
//

import UIKit

class NavigationBackground: UIView {
    
    let titleLabel: UILabel! = UILabel()
    let backButton: UIButton! = UIButton()
    let splitLine: CALayer! = CALayer()
    
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: abs(navigationFrame.origin.y))))
        self.backgroundColor = UIColor.white
        self.layer.zPosition = 0.75
        self.layer.shadowPath = CGPath(rect: CGRect.init(x: 4, y: 4, width: self.frame.width - 8, height: self.frame.height - 8), transform: nil)
        self.layer.shadowColor = UIColor.mainRed().cgColor
        self.layer.shadowOpacity = 0.3
        self.layer.shadowOffset = CGSize(width: 0, height: 2)
        self.layer.shadowRadius = 4
        
        titleLabel.frame = CGRect(x: 0, y: abs(navigationFrame.origin.y) - navigationFrame.height + 5 * screenScale, width: self.frame.width, height: 30 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
        titleLabel.textColor = UIColor.fontBlack()
        self.addSubview(titleLabel)
        
        backButton.frame = CGRect(x: 0, y: titleLabel.frame.origin.y, width: 100 * screenScale, height: titleLabel.frame.height)
        let backIconView = UIImageView(frame: CGRect(x: 5 * screenScale, y: 0, width: 30 * screenScale, height: 30 * screenScale))
        backIconView.image = UIImage(named: "common_back")
        backButton.addSubview(backIconView)
        self.addSubview(backButton)
        
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
