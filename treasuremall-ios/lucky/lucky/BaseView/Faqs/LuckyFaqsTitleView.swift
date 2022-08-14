//
//  LuckyFaqsTitleView.swift
//  lucky
//  提问 标题Cell
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFaqsTitleView: UIView{
    init(frame: CGRect, title: String, image: UIImage?) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 36 * screenScale, y: 0, width: frame.width - 48 * screenScale, height: 0))
        titleLabel.numberOfLines = 0
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.boldFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let titleText: NSMutableAttributedString = NSMutableAttributedString(string: title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        titleLabel.attributedText = titleText
        titleLabel.sizeToFit()
        if(titleLabel.frame.height < 40 * screenScale){
            //1行 设定高度
            titleLabel.frame = CGRect(x: titleLabel.frame.origin.x, y: 0, width: frame.width - 48 * screenScale, height: 40 * screenScale)
            self.frame.size = CGSize(width: frame.width, height: titleLabel.frame.height)
        }else{
            //多行 动态高度
            titleLabel.frame = CGRect(x: titleLabel.frame.origin.x, y: 6 * screenScale, width: frame.width - 48 * screenScale, height: titleLabel.frame.height)
            self.frame.size = CGSize(width: frame.width, height: titleLabel.frame.height + 12 * screenScale)
        }
        self.addSubview(titleLabel)
        
        //图标
        let imageView = UIImageView(frame: CGRect(x: 12 * screenScale, y: (self.frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
        imageView.image = image
        self.addSubview(imageView)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
