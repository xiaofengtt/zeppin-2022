//
//  LuckyChargeCapitalAccountCellButton.swift
//  lucky
//  充值渠道 Cell按钮
//  Created by Farmer Zhu on 2020/8/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyChargeCapitalAccountCellButton: UIButton{
    
    var data: LuckyCapitalAccountModel!
    
    var selectedImageView: UIImageView!
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyCapitalAccountModel) {
        super.init(frame: frame)
        self.data = data
        
        //渠道图标
        let iconView = UIImageView(frame: CGRect(x: paddingLeft, y: (frame.height - 48 * screenScale)/2, width: 48 * screenScale, height: 48 * screenScale))
        iconView.sd_setImage(with: URL(string: data.logoUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        iconView.layer.masksToBounds = true
        iconView.layer.cornerRadius = 10 * screenScale
        self.addSubview(iconView)
        
        //选中图标
        selectedImageView = UIImageView(frame: CGRect(x: frame.width - 34 * screenScale, y: (frame.height - 22 * screenScale)/2, width: 22 * screenScale, height: 22 * screenScale))
        selectedImageView.image = UIImage(named: "image_unselected")
        self.addSubview(selectedImageView)
        
        //名称
        let titleLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 12 * screenScale, y: 0, width: selectedImageView.frame.origin.x - 10 * screenScale - (iconView.frame.origin.x + iconView.frame.width + 12 * screenScale), height: self.frame.height))
        titleLabel.text = data.name
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
        
        //详情 限额
//        let contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 6 * screenScale, width: titleLabel.frame.width, height: 20 * screenScale))
//        contentLabel.text = "$\(LuckyUtils.coinFormat(amount: data.min))~\(LuckyUtils.coinFormat(amount: data.max)) \(NSLocalizedString("payment limit per transaction", comment: ""))"
//        contentLabel.textColor = UIColor.fontGray()
//        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
//        self.addSubview(contentLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //选中状态 改变选中图标
    override var isSelected: Bool{
        willSet{
            if(newValue){
                selectedImageView.image = UIImage(named: "image_selected")
                
            }else{
                selectedImageView.image = UIImage(named: "image_unselected")
            }
        }
    }
}
