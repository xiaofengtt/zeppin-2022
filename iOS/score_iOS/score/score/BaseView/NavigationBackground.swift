//
//  NavigationBackground.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/12.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class NavigationBackground: UIView {
    
    let titleLabel: UILabel! = UILabel()
    let logoImageView: UIImageView! = UIImageView()
    
    init(navigationController: UINavigationController) {
        let navigationFrame = navigationController.navigationBar.frame
        super.init(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: navigationFrame.width, height: navigationFrame.origin.y + navigationFrame.height)))
        self.backgroundColor = UIColor.mainYellow()
        self.layer.zPosition = 0.75
        
        titleLabel.frame = CGRect(x: 0, y: navigationFrame.origin.y + 5 * screenScale, width: self.frame.width, height: 30 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
        titleLabel.textColor = UIColor.fontBlack()
        self.addSubview(titleLabel)
        
        logoImageView.frame = CGRect(x: screenWidth/2 - 43 * screenScale, y: navigationFrame.origin.y + 15 * screenScale, width: 86 * screenScale, height: 20 * screenScale)
        logoImageView.image = UIImage(named: "common_logo")
        self.addSubview(logoImageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    init() {
        super.init(frame: CGRect.zero)
    }
}
