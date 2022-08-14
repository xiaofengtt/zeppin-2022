//
//  LuckyGoodsTypeButton.swift
//  lucky
//  分类左侧栏 按钮
//  Created by Farmer Zhu on 2020/9/2.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGoodsTypeButton: UIButton{
    
    var data: LuckyGoodsTypeModel!
    
    var selectedLine: CALayer!
    var textLabel: UILabel!
    
    init(frame: CGRect, data: LuckyGoodsTypeModel) {
        super.init(frame: frame)
        self.data = data
        
        //选中提示线
        selectedLine = CALayer()
        selectedLine.frame = CGRect(x: 0, y: (frame.height - 22 * screenScale)/2, width: 4 * screenScale, height: 22 * screenScale)
        selectedLine.isHidden = true
        selectedLine.backgroundColor = UIColor.mainYellow().cgColor
        self.layer.addSublayer(selectedLine)
        
        //名称
        textLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: frame.width - 16 * screenScale, height: frame.height))
        textLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        textLabel.attributedText = NSAttributedString(string: data.name, attributes: [NSAttributedString.Key.paragraphStyle : style])
        textLabel.textColor = UIColor.fontDarkGray()
        textLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        textLabel.textAlignment = NSTextAlignment.center
        textLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        self.addSubview(textLabel)
        
        if(data.name == NSLocalizedString("High Valued", comment: "")){
            //高价值 hot标签
            let hotImageView = UIImageView(frame: CGRect(x: frame.width - 38 * screenScale, y: 0, width: 36 * screenScale, height: 16 * screenScale))
            hotImageView.image = UIImage(named: "image_categories_hot")
            self.addSubview(hotImageView)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    override var isSelected: Bool{
        willSet{
            selectedLine.isHidden = !newValue
            if(newValue){
                //被选中
                self.backgroundColor = UIColor.white
                textLabel.textColor = UIColor.mainYellow()
            }else{
                //未选中
                self.backgroundColor = UIColor.backgroundGray()
                textLabel.textColor = UIColor.fontDarkGray()
            }
        }
    }
}
