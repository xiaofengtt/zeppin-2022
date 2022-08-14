//
//  LuckyActivityOrderScorelotteryCellView.swift
//  lucky
//  活动 积分转盘 订单Cell
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityOrderScorelotteryCellView: UIView{
    
    var data: LuckyFrontUserScorelotteryHistoryModel!

    var deliverButton: UIButton?

    let paddingLeft: CGFloat = 16 * screenScale
    
    init(frame: CGRect, data: LuckyFrontUserScorelotteryHistoryModel) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        self.data = data
        
        //顶部标题
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 16 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
        titleLabel.text = data.prizeTitle
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
        
        //时间
        let timeLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 6 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height))
        timeLabel.text = LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:ss")
        timeLabel.textColor = UIColor.fontGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(timeLabel)
        
        if(data.status == "normal" && data.prizeType == "entity"){
            //奖品为实物且未兑奖 兑奖按钮
            deliverButton = UIButton(frame: CGRect(x: frame.width - paddingLeft - 80 * screenScale, y: 16 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
            deliverButton!.layer.masksToBounds = true
            deliverButton!.layer.cornerRadius = deliverButton!.frame.height/2
            deliverButton!.layer.borderColor = UIColor.fontRed().cgColor
            deliverButton!.layer.borderWidth = 1
            deliverButton!.setTitle(NSLocalizedString("Deliver", comment: ""), for: UIControl.State.normal)
            deliverButton!.setTitleColor(UIColor.fontRed(), for: UIControl.State.normal)
            deliverButton!.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            self.addSubview(deliverButton!)
        }
        self.frame.size = CGSize(width: frame.width, height: 80 * screenScale)
        
        if(data.status != "normal" && data.prizeType == "entity"){
            //奖品为实物且已兑奖
            if(data.receiveType == "gold"){
                //兑金币 金币数量
                let coinsLabel = UILabel(frame: CGRect(x: paddingLeft, y: timeLabel.frame.origin.y + timeLabel.frame.height + 6 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
                coinsLabel.text = "\(NSLocalizedString("Coins exchanged", comment: "")):"
                coinsLabel.textColor = UIColor.fontGray()
                coinsLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
                coinsLabel.sizeToFit()
                coinsLabel.frame.size = CGSize(width: coinsLabel.frame.width, height: 20 * screenScale)
                self.addSubview(coinsLabel)
                
                let priceLabel = UILabel(frame: CGRect(x: coinsLabel.frame.origin.x + coinsLabel.frame.width + 2 * screenScale, y: coinsLabel.frame.origin.y, width: 0, height: coinsLabel.frame.height))
                priceLabel.text = LuckyUtils.coinFormat(amount: data.dealPrice)
                priceLabel.textColor = UIColor.activityMainColor()
                priceLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
                priceLabel.sizeToFit()
                priceLabel.frame.size = CGSize(width: priceLabel.frame.width, height: coinsLabel.frame.height)
                self.addSubview(priceLabel)
                
                let coinImageView = UIImageView(frame: CGRect(x: priceLabel.frame.origin.x + priceLabel.frame.width + 2 * screenScale, y: coinsLabel.frame.origin.y + (coinsLabel.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
                coinImageView.image = UIImage(named: "image_gold_coin")
                self.addSubview(coinImageView)
                self.frame.size = CGSize(width: frame.width, height: 104 * screenScale)
            }else{
                //兑实物 收货地址
                let addressLabel = UILabel(frame: CGRect(x: paddingLeft, y: timeLabel.frame.origin.y + timeLabel.frame.height + 6 * screenScale, width: frame.width - paddingLeft * 2, height: 40 * screenScale))
                addressLabel.numberOfLines = 2
                addressLabel.textColor = UIColor.fontGray()
                addressLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
                let style = NSMutableParagraphStyle()
                style.maximumLineHeight = 16 * screenScale
                style.minimumLineHeight = 16 * screenScale
                style.lineBreakMode = NSLineBreakMode.byTruncatingTail
                let addressText: NSMutableAttributedString = NSMutableAttributedString(string: "\(data.detailInfo == nil ? "" : data.detailInfo!.address)", attributes: [NSAttributedString.Key.paragraphStyle : style])
                addressLabel.attributedText = addressText
                addressLabel.alignmentTop()
                self.addSubview(addressLabel)
                self.frame.size = CGSize(width: frame.width, height:addressLabel.frame.origin.y + addressLabel.frame.height + 16 * screenScale)
            }
        }
        
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
