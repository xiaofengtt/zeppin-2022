//
//  UIButton.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/4.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import UIKit

extension UIButton{
    func showNumber(number: String) {
        let numberLabel = UILabel(frame: CGRect(x: self.frame.width - 10 * screenScale, y: 0, width: 20 * screenScale, height: 10 * screenScale))
        numberLabel.backgroundColor = UIColor.white
        numberLabel.text = number
        numberLabel.textColor = UIColor.mainYellow()
        numberLabel.textAlignment = NSTextAlignment.center
        numberLabel.font = UIFont.boldFont(size: 10 * screenScale)
        self.addSubview(numberLabel)
    }
}
