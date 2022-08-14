//
//  NoMoreView.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class NoMoreView: UIView {
    
    let label: UILabel = UILabel()
    
    init(frame: CGRect, title: String, flagLine: Bool) {
        super.init(frame: frame)
        
        label.text = title
        label.textColor = UIColor.fontDarkGray()
        label.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        label.textAlignment = NSTextAlignment.center
        label.sizeToFit()
        label.frame = CGRect(x: (self.frame.width - label.frame.width)/2 , y: 0, width: label.frame.width, height: self.frame.height)
        self.addSubview(label)
        
        if(flagLine){
            let leftLine = CALayer()
            leftLine.frame = CGRect(x: label.frame.origin.x - 40 * screenScale, y: self.frame.height / 2 - 1 * screenScale, width: 30 * screenScale, height: 1 * screenScale)
            leftLine.backgroundColor = UIColor.fontGray().cgColor
            self.layer.addSublayer(leftLine)
            
            let rightLine = CALayer()
            rightLine.frame = CGRect(x: label.frame.origin.x + label.frame.width + 10 * screenScale, y: leftLine.frame.origin.y, width: leftLine.frame.width, height: leftLine.frame.height)
            rightLine.backgroundColor = leftLine.backgroundColor
            self.layer.addSublayer(rightLine)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
