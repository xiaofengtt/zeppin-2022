//
//  LuckyAddressListCellView.swift
//  lucky
//  地址列表Cell
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAddressListCellView: UIView{
    var data: LuckyFrontUserAddressModel!
    
    var editButton: UIButton!
    var seletedImageView: UIImageView!
    
    init(frame: CGRect, data: LuckyFrontUserAddressModel) {
        super.init(frame: frame)
        self.data = data
        
        //可编辑图标
        let editImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: (frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        editImageView.image = UIImage(named: "image_address_edit")
        self.addSubview(editImageView)
        
        //编辑按钮
        editButton = UIButton(frame: CGRect(x: 0, y: 0, width: 38 * screenScale, height: frame.height))
        self.addSubview(editButton)
        
        //选中状态图
        seletedImageView = UIImageView(frame: CGRect(x: frame.width - 26 * screenScale, y: (frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        seletedImageView.isHidden = true
        seletedImageView.image = UIImage(named: "image_selected")
        self.addSubview(seletedImageView)
        
        //名称
        let nameLabel = UILabel(frame: CGRect(x: editButton.frame.origin.x + editButton.frame.width, y: 20 * screenScale, width: (frame.width - 40 * screenScale - (editButton.frame.origin.x + editButton.frame.width))/2 , height: 20 * screenScale))
        nameLabel.text = data.receiver
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(nameLabel)
        
        //手机号
        let mobileLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: nameLabel.frame.origin.y, width: nameLabel.frame.width, height: nameLabel.frame.height))
        mobileLabel.text = data.phone
        mobileLabel.textColor = nameLabel.textColor
        mobileLabel.font = nameLabel.font
        mobileLabel.textAlignment = NSTextAlignment.right
        self.addSubview(mobileLabel)
        
        //详细地址
        let contentLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height + 4 * screenScale, width: frame.width - 40 * screenScale - nameLabel.frame.origin.x, height: 50 * screenScale))
        contentLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        var areaString = ""
        for str in data.areaNameList{
            areaString = areaString + str + ", "
        }
        let contentStr = "\(data.address),\(data.asub == "" ? "" : data.asub + ",")\(data.city),\(data.state),\(data.country),\(data.zipcode)"
        contentLabel.attributedText = NSAttributedString(string: contentStr, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        contentLabel.alignmentTop()
        self.addSubview(contentLabel)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
