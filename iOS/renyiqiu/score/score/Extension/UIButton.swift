//
//  UIButton.swift
//  ryqiu
//
//  Created by worker on 2019/6/4.
//  Copyright © 2019 worker. All rights reserved.
//

import UIKit

extension UIButton{
    func showNumber(number: String) {
        let numberLabel = UILabel(frame: CGRect(x: self.frame.width - 10 * screenScale, y: 0, width: 20 * screenScale, height: 10 * screenScale))
        numberLabel.backgroundColor = UIColor.white
        numberLabel.text = number
        numberLabel.textColor = UIColor.mainRed()
        numberLabel.textAlignment = NSTextAlignment.center
        numberLabel.font = UIFont.boldFont(size: 10 * screenScale)
        self.addSubview(numberLabel)
    }
}
