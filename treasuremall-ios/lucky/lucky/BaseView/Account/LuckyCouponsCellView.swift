//
//  LuckyCouponsCellView.swift
//  lucky
//  代金券Cell
//  Created by Farmer Zhu on 2020/8/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCouponsCellView: UIView{
    
    var selectedImageView: UIImageView?
    
    var data: LuckyFrontUserVoucherModel!
    
    //选中时显示选中标线
    var isSelected = false{
        willSet{
            if(selectedImageView != nil){
                if newValue{
                    selectedImageView?.image = UIImage(named: "image_selected")
                }else{
                    selectedImageView?.image = UIImage(named: "image_unselected")
                }
            }
        }
    }
    
    let paddingLeft: CGFloat = 10 * screenScale
    let paddingTop: CGFloat = 8 * screenScale
    
    init(frame: CGRect, coupon: LuckyFrontUserVoucherModel, flagSelect: Bool) {
        super.init(frame: frame)
        self.data = coupon
        
        //主框体
        let mainView = UIView(frame: CGRect(x: paddingLeft, y: paddingTop, width: frame.width - paddingLeft*2, height: frame.height - paddingTop*2))
        //背景
        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: mainView.frame.size))
        if("normal" == coupon.status || "unstart" == coupon.status){
            //可用,未到使用时间 可用图
            bgImageView.image = UIImage(named: "image_coupon_enable")
        }else{
            //已使用 已过期 不可用图
            bgImageView.image = UIImage(named: "image_coupon_disable")
        }
        mainView.addSubview(bgImageView)
        
        //代金额
        let amountLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: frame.width*0.3, height: 30 * screenScale))
        amountLabel.text = LuckyUtils.coinFullFormat(amount: coupon.dAmount)
        amountLabel.textColor = UIColor.white
        amountLabel.font = UIFont.boldFont(size: 30 * screenScale)
        amountLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(amountLabel)
        
        if(globalFlagUser){
            //主包代替金币
            let coinLabel = UILabel()
            coinLabel.text = NSLocalizedString("Coins", comment: "")
            coinLabel.textColor = UIColor.white
            coinLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
            coinLabel.sizeToFit()
            coinLabel.frame = CGRect(x: (amountLabel.frame.width - (coinLabel.frame.width + 24 * screenScale))/2, y: amountLabel.frame.origin.y + amountLabel.frame.height + 10 * screenScale, width: coinLabel.frame.width, height: 18 * screenScale)
            mainView.addSubview(coinLabel)
            
            let coinImageView = UIImageView(frame: CGRect(x: coinLabel.frame.origin.x + coinLabel.frame.width + 4 * screenScale, y: coinLabel.frame.origin.y, width: 20 * screenScale, height: 18 * screenScale))
            if("normal" == coupon.status || "unstart" == coupon.status){
                coinImageView.image = UIImage(named: "image_coupon_coin_enable")
            }else{
                coinImageView.image = UIImage(named: "image_coupon_coin_disable")
            }
            mainView.addSubview(coinImageView)
        }else{
            //马甲代替美元
            let coinImageView = UIImageView(frame: CGRect(x: (amountLabel.frame.width - 18 * screenScale)/2, y: amountLabel.frame.origin.y + amountLabel.frame.height + 4 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
            coinImageView.image = UIImage(named: "image_gold_dollor")
            mainView.addSubview(coinImageView)
        }
        if(flagSelect){
            //选中状态
            selectedImageView = UIImageView(frame: CGRect(x: mainView.frame.width - paddingLeft - 22 * screenScale, y: (mainView.frame.height - 22 * screenScale)/2, width: 22 * screenScale, height: 22 * screenScale))
            selectedImageView?.image = UIImage(named: "image_unselected")
            mainView.addSubview(selectedImageView!)
        }else{
            //未选中状态
            let rightView = UIView(frame: CGRect(x: mainView.frame.width - 36 * screenScale, y: 0, width: 36 * screenScale, height: mainView.frame.height))
            let rightLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: rightView.frame.height, height: rightView.frame.height)))
            if("normal" == coupon.status){
                //可用
                rightLabel.text = NSLocalizedString("Use Now", comment: "")
                rightLabel.textColor = UIColor.fontRed()
            }else if("unstart" == coupon.status){
                //未到可用时间
                rightLabel.text = NSLocalizedString("Unavailable", comment: "")
                rightLabel.textColor = UIColor.fontRed()
            }else if("used" == coupon.status){
                //已使用
                rightLabel.text = NSLocalizedString("Used", comment: "")
                rightLabel.textColor = UIColor.fontGray()
            }else{
                //过期
                rightLabel.text = NSLocalizedString("Expired", comment: "")
                rightLabel.textColor = UIColor.fontGray()
            }
            rightLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
            rightLabel.textAlignment = NSTextAlignment.center
            rightLabel.transform = CGAffineTransform(rotationAngle: 3.1415926/2)
            rightLabel.frame.size = rightView.frame.size
            rightView.addSubview(rightLabel)
            let rightSpaceLine = LuckyDottedLine(frame: CGRect(x: 0, y: 0, width: 1, height: rightView.frame.height), color: UIColor.init(red: 205/255, green: 205/255, blue: 205/255, alpha: 1), direction: LuckyDottedLine.LuckyDottedLineDirection.vertical)
            rightView.addSubview(rightSpaceLine)
            mainView.addSubview(rightView)
        }
        //代金券名
        let nameLabel = UILabel(frame: CGRect(x: amountLabel.frame.origin.x + amountLabel.frame.width + 16 * screenScale, y: 8 * screenScale, width: mainView.frame.width - 36 * screenScale - (amountLabel.frame.width + 16 * screenScale), height: 40 * screenScale))
        nameLabel.numberOfLines = 2
        nameLabel.text = coupon.title
        if("normal" == coupon.status || "unstart" == coupon.status){
            nameLabel.textColor = UIColor.fontBlack()
        }else{
            nameLabel.textColor = UIColor.fontGray()
        }
        nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(nameLabel)
        
        //金额限制
        let limitLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height + 6 * screenScale, width: nameLabel.frame.width, height: 16 * screenScale))
        if(globalFlagUser){
            limitLabel.text = "\(NSLocalizedString("over", comment: "")) \(LuckyUtils.coinFullFormat(amount: coupon.payMin)) \(NSLocalizedString("coins", comment: ""))"
        }else{
            limitLabel.text = "\(NSLocalizedString("over", comment: "")) $\(LuckyUtils.coinFullFormat(amount: coupon.payMin))"
        }
        limitLabel.textColor = UIColor.fontGray()
        limitLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        mainView.addSubview(limitLabel)
        
        //时间限制
        let timeLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: limitLabel.frame.origin.y + limitLabel.frame.height , width: nameLabel.frame.width, height: 36 * screenScale))
        timeLabel.numberOfLines = 2
        if(coupon.endtime != 0){
            timeLabel.text = "\(LuckyUtils.timestampFormat(timestamp: coupon.starttime, format: "MMM dd,yyyy HH:mm:ss"))-\(LuckyUtils.timestampFormat(timestamp: coupon.endtime, format: "MMM dd,yyyy HH:mm:ss"))"
        }else{
            timeLabel.text = NSLocalizedString("Coupon is valid forever", comment: "")
        }
        timeLabel.textColor = limitLabel.textColor
        timeLabel.font = limitLabel.font
        timeLabel.lineBreakMode = NSLineBreakMode.byWordWrapping
        timeLabel.alignmentTop()
        mainView.addSubview(timeLabel)
        
        self.addSubview(mainView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
