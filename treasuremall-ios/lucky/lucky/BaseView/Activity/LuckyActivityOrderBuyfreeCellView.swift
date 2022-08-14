//
//  LuckyActivityOrderBuyfreeCellView.swift
//  lucky
//  活动 零元购订单Cell
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation


class LuckyActivityOrderBuyfreeCellView: UIView{
    
    var data: LuckyFrontUserBuyfreeOrderModel!

    var deliverButton: UIButton?

    let paddingLeft: CGFloat = 16 * screenScale
    
    init(frame: CGRect, data: LuckyFrontUserBuyfreeOrderModel) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        self.data = data
        
        //顶部标题
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 12 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
        let titleString = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum)"
        let titleText = NSMutableAttributedString(string: "\(titleString) | \(LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:mm"))")
        titleText.addAttributes([NSAttributedString.Key.foregroundColor : UIColor.activityMainColor()], range: NSRange(location: 0, length: titleString.count))
        titleLabel.attributedText = titleText
        self.addSubview(titleLabel)
        
        //状态标签
        let statusBgView = UIView(frame: CGRect(x: frame.width - 70 * screenScale, y: 10 * screenScale, width: 100 * screenScale, height: 26 * screenScale))
        statusBgView.layer.masksToBounds = true
        statusBgView.layer.cornerRadius = statusBgView.frame.height/2

        let statusLabel = UILabel(frame: CGRect(x: statusBgView.frame.origin.x, y: statusBgView.frame.origin.y, width: 70 * screenScale, height: statusBgView.frame.height))
        statusLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.center
        
        if(data.status == "normal"){
            statusBgView.backgroundColor = UIColor.mainLightYellow()
            statusLabel.text = NSLocalizedString("Ongoing", comment: "")
            statusLabel.textColor = UIColor.fontRed()
        }else if(!data.isLucky){
            statusBgView.backgroundColor = UIColor.placeholderGray()
            statusLabel.text = NSLocalizedString("Losing", comment: "")
            statusLabel.textColor = UIColor.white
        }else if(data.status == "win"){
            statusBgView.backgroundColor = UIColor.mainLightRed()
            statusLabel.text = NSLocalizedString("Winning", comment: "")
            statusLabel.textColor = UIColor.mainRed()
        }else{
            statusBgView.backgroundColor = UIColor.mainLightBlue()
            statusLabel.text = NSLocalizedString("Delivered", comment: "")
            statusLabel.textColor = UIColor.mainBlue()
        }
        self.addSubview(statusBgView)
        self.addSubview(statusLabel)
        
        //中奖标签
        if(data.isLucky){
            let winningImageView = UIImageView(frame: CGRect(x: statusBgView.frame.origin.x - 70 * screenScale, y: 0, width: 62 * screenScale, height: 43 * screenScale))
            winningImageView.image = UIImage(named: "image_watermark_winning")
            self.addSubview(winningImageView)
        }
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale, width: 70 * screenScale, height: 70 * screenScale))
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 8 * screenScale, y: imageView.frame.origin.y + 10 * screenScale, width: frame.width - paddingLeft - (imageView.frame.origin.x + imageView.frame.width + 8 * screenScale), height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentLabel.attributedText = contentText
        contentLabel.alignmentTop()
        self.addSubview(contentLabel)
        self.frame.size = CGSize(width: frame.width, height: 130 * screenScale)
        
        if(data.isLucky){
            //中奖
            if(data.status == "win"){
                //未兑奖 兑奖按钮
                deliverButton = UIButton(frame: CGRect(x: frame.width - paddingLeft - 80 * screenScale, y: imageView.frame.origin.y + imageView.frame.height + 16 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
                deliverButton!.layer.masksToBounds = true
                deliverButton!.layer.cornerRadius = deliverButton!.frame.height/2
                deliverButton!.layer.borderColor = UIColor.fontRed().cgColor
                deliverButton!.layer.borderWidth = 1
                deliverButton!.setTitle(NSLocalizedString("Deliver", comment: ""), for: UIControl.State.normal)
                deliverButton!.setTitleColor(UIColor.fontRed(), for: UIControl.State.normal)
                deliverButton!.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
                self.addSubview(deliverButton!)
                self.frame.size = CGSize(width: frame.width, height: 168 * screenScale)
            }else{
                //已兑奖
                if(data.receiveType == "gold"){
                    //兑金币 金币数
                    let coinsLabel = UILabel(frame: CGRect(x: paddingLeft, y: imageView.frame.origin.y + imageView.frame.height + 6 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
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
                    self.frame.size = CGSize(width: frame.width, height: 152 * screenScale)
                }else{
                    //兑实物 收货地址
                    let addressLabel = UILabel(frame: CGRect(x: paddingLeft, y: imageView.frame.origin.y + imageView.frame.height + 6 * screenScale, width: frame.width - paddingLeft * 2, height: 40 * screenScale))
                    addressLabel.numberOfLines = 2
                    addressLabel.textColor = UIColor.fontGray()
                    addressLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
                    let addressStyle = NSMutableParagraphStyle()
                    addressStyle.maximumLineHeight = 16 * screenScale
                    addressStyle.minimumLineHeight = 16 * screenScale
                    addressStyle.lineBreakMode = NSLineBreakMode.byTruncatingTail
                    let addressText: NSMutableAttributedString = NSMutableAttributedString(string: "\(data.detailInfo == nil ? "" : data.detailInfo!.address)", attributes: [NSAttributedString.Key.paragraphStyle : addressStyle])
                    addressLabel.attributedText = addressText
                    addressLabel.alignmentTop()
                    self.addSubview(addressLabel)
                    self.frame.size = CGSize(width: frame.width, height:addressLabel.frame.origin.y + addressLabel.frame.height + 16 * screenScale)
                }
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
