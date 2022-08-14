//
//  LuckyFaqsContentView.swift
//  lucky
//  提问 内容Cell
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFaqsContentView: UIView{
    init(frame: CGRect, content: String) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: 16 * screenScale, y: 10 * screenScale, width: frame.width - 32 * screenScale, height: 0))
        contentLabel.numberOfLines = 0
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        let style = NSMutableParagraphStyle()
        style.minimumLineHeight = 18 * screenScale
        style.maximumLineHeight = 18 * screenScale
        style.paragraphSpacing = 4 * screenScale
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: content, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentLabel.attributedText = contentText
        contentLabel.sizeToFit()
        self.addSubview(contentLabel)
        self.frame.size = CGSize(width: frame.width, height: contentLabel.frame.origin.y * 2 + contentLabel.frame.height)
        
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
