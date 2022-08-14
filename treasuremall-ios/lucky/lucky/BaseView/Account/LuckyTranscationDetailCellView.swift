//
//  LuckyTranscationDetailCellView.swift
//  lucky
//  交易详情 数据行Cell
//  Created by Farmer Zhu on 2020/9/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTranscationDetailCellView: UIView{
    
    var nameLabel: UILabel!
    var valueLabel: UILabel!
    var button: UIButton!
    
    let lineHeight: CGFloat = 20 * screenScale
    
    init(frame: CGRect, title: String, value: String, isEnter: Bool, singleRow: Bool) {
        super.init(frame: frame)
        //数据名
        nameLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 120 * screenScale, height: lineHeight))
        nameLabel.text = title
        nameLabel.textColor = UIColor.fontGray()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(nameLabel)
        
        //数据值
        var valueLabelWidth = frame.width - 10 * screenScale - (nameLabel.frame.origin.x + nameLabel.frame.width)
        if(isEnter){
            //可点击进入 有进入三角
            let enterImageView = UIImageView(frame: CGRect(x: frame.width - 23 * screenScale, y: (lineHeight - 13 * screenScale)/2, width: 8 * screenScale, height: 13 * screenScale))
            enterImageView.image = UIImage(named: "image_enter_gray")
            self.addSubview(enterImageView)
            valueLabelWidth = enterImageView.frame.origin.x - 5 * screenScale - (nameLabel.frame.origin.x + nameLabel.frame.width)
        }
        
        valueLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: nameLabel.frame.origin.y, width: valueLabelWidth, height: lineHeight))
        if(!singleRow){
            valueLabel.numberOfLines = 0
        }
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = lineHeight
        style.minimumLineHeight = lineHeight
        let valueText: NSMutableAttributedString = NSMutableAttributedString(string: value, attributes: [NSAttributedString.Key.paragraphStyle : style])
        valueLabel.attributedText = valueText
        valueLabel.textColor = UIColor.fontBlack()
        valueLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        valueLabel.textAlignment = NSTextAlignment.right
        valueLabel.sizeToFit()
        let heightResize = valueLabel.frame.height > lineHeight ? valueLabel.frame.height - lineHeight : 0
        valueLabel.frame.size = CGSize(width: valueLabelWidth, height: valueLabel.frame.height)
        self.addSubview(valueLabel)
        self.frame.size = CGSize(width: frame.width, height: frame.height + heightResize)
        
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
