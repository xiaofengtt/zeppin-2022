//
//  FilterChildView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/22.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class FilterChildView: UIView {
    
    var button: UIButton = UIButton()
    var titleLabel: UILabel = UILabel()
    var tagImage: UIImageView = UIImageView()
    
    var title: String!
    var selected: Bool = false
    let fontBlue = UIColor(red: 0/255, green: 105/255, blue: 229/255, alpha: 1)
    let backgroundBlue = UIColor(red: 229/255, green: 244/255, blue: 255/255, alpha: 1)
    let backgroundGray = UIColor(red: 245/255, green: 245/255, blue: 245/255, alpha: 1)
    
    init(frame: CGRect, title: String){
        super.init(frame: frame)
        self.title = title
        self.backgroundColor = backgroundGray
        self.layer.cornerRadius = 3 * screenScale
        
        titleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        titleLabel.text = title
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.lightFont(size: UIFont.smallSize())
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        self.addSubview(titleLabel)
        
        tagImage = UIImageView(frame: CGRect(x: frame.width - 18 * screenScale, y: frame.height - 15 * screenScale, width: 18 * screenScale, height: 15 * screenScale))
        tagImage.image = UIImage(named: "product_filter_tag")
        tagImage.isHidden = true
        self.addSubview(tagImage)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func select(){
        if(self.selected){
            self.backgroundColor = backgroundGray
            titleLabel.textColor = UIColor.fontBlack()
            tagImage.isHidden = true
        }else{
            self.backgroundColor = backgroundBlue
            titleLabel.textColor = fontBlue
            tagImage.isHidden = false
        }
        self.selected = !selected
    }
}
