//
//  BusinessDataLabel.swift
//  nmgs
//
//  Created by zeppin on 2016/11/10.
//  Copy © 2016年 zeppin. All s reserved.
//

import UIKit

class BusinessDataTitleLabel: UILabel{
    init(text: String, textColor: UIColor) {
        super.init(frame: CGRect())
        self.font = UIFont.systemFont(ofSize: 14)
        self.textAlignment = NSTextAlignment.left
        self.textColor = textColor
        self.text = text
        self.sizeToFit()
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}

class BusinessDataContentLabel: UILabel{
    init(text: String) {
        super.init(frame: CGRect())
        self.font = UIFont.systemFont(ofSize: 12)
        self.textAlignment = NSTextAlignment.left
        self.textColor = UIColor.darkGray
        self.text = text
        self.sizeToFit()
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
