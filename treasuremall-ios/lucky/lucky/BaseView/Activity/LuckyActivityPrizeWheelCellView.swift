//
//  LuckyActivityPrizeWheelCellView.swift
//  lucky
//  活动 积分转盘 奖品cell
//  Created by Farmer Zhu on 2020/10/26.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityPrizeWheelCellView: UIView{
    
    var prize: LuckyActivityScorelotteryPrizeModel!
    let singularBgColor = UIColor(red: 255/255, green: 248/255, blue: 232/255, alpha: 1)
    let pluralBgColor = UIColor(red: 255/255, green: 240/255, blue: 207/255, alpha: 1)
    
    init(frame: CGRect, prize: LuckyActivityScorelotteryPrizeModel, isSingular: Bool, angle: CGFloat){
        self.prize = prize
        super.init(frame: frame)
        
        //背景 单双数间隔
        let bgLayer = LuckyActivityPrizeWheelFanshapeView(frame: CGRect(origin: CGPoint.zero, size: frame.size), color: isSingular ? singularBgColor : pluralBgColor, angle: angle)
        self.addSubview(bgLayer)
        
        //奖品名
        let label = UILabel(frame: CGRect(x: 0, y: 25 * screenScale, width: frame.width, height: 36 * screenScale))
        label.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 18 * screenScale
        style.minimumLineHeight = 18 * screenScale
        let labelText: NSMutableAttributedString = NSMutableAttributedString(string: prize.prizeTitle, attributes: [NSAttributedString.Key.paragraphStyle : style])
        label.attributedText = labelText
        label.textColor = UIColor(red: 179/255, green: 71/255, blue: 35/255, alpha: 1)
        label.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        label.textAlignment = NSTextAlignment.center
        label.alignmentTop()
        self.addSubview(label)
        
        //奖品图
        let imageView = UIImageView(frame: CGRect(x: (frame.width - 44 * screenScale)/2, y: 60 * screenScale, width: 44 * screenScale, height: 44 * screenScale))
        if(prize.prizeType == "gold"){
            imageView.image = UIImage(named: "image_activity_wheel_prize_gold")
        }else if(prize.prizeType == "voucher"){
            imageView.image = UIImage(named: "image_activity_wheel_prize_voucher")
        }else{
            imageView.sd_setImage(with: URL(string: prize.prizeCoverUrl), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        }
        self.addSubview(imageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
