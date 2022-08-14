//
//  LuckyActivityOrderExchangeView.swift
//  lucky
//  活动 订单兑奖框
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityOrderExchangeView: UIView{
    
    var uuid: String = ""
    var type: String = ""
    
    var imageView: UIImageView!
    var nameLabel: UILabel!
    var priceLabel: UILabel!
    var dealPriceLabel: UILabel!
    var shippingButton: UIButton!
    var coinButton: UIButton!
    
    init(parent: UIView) {
        super.init(frame: parent.frame)
        self.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        self.layer.zPosition = 0.9
        
        //主框体
        let mainView = UIView(frame: CGRect(x: (parent.frame.width - 288 * screenScale)/2, y: 0, width: 288 * screenScale, height: 0))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 4 * screenScale
        mainView.backgroundColor = UIColor.white
        
        //头
        let headView = UIView(frame: CGRect(x: 0, y: 0, width: mainView.frame.width, height: 100 * screenScale))
        headView.backgroundColor = UIColor.mainLightYellow()
        
        //头部图
        imageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: 16 * screenScale, width: 68 * screenScale, height: 68 * screenScale))
        headView.addSubview(imageView)
        
        //商品名
        nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: imageView.frame.origin.y, width: mainView.frame.width - 16 * screenScale - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale), height: 40 * screenScale))
        nameLabel.numberOfLines = 2
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        headView.addSubview(nameLabel)
        
        //价格
        priceLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: imageView.frame.origin.y + imageView.frame.height - 18 * screenScale, width: nameLabel.frame.width, height: 20 * screenScale))
        priceLabel.textColor = UIColor.fontDarkGray()
        priceLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        headView.addSubview(priceLabel)
        
        //数量
        let numberLabel = UILabel(frame: priceLabel.frame)
        numberLabel.text = "x1"
        numberLabel.textColor = priceLabel.textColor
        numberLabel.font = priceLabel.font
        numberLabel.textAlignment = NSTextAlignment.right
        headView.addSubview(numberLabel)
        mainView.addSubview(headView)
        
        //总价值
        dealPriceLabel = UILabel(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 18 * screenScale, width: mainView.frame.width, height: 36 * screenScale))
        dealPriceLabel.textColor = UIColor.fontRed()
        dealPriceLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        dealPriceLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(dealPriceLabel)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: 0, y: dealPriceLabel.frame.origin.y + dealPriceLabel.frame.height, width: mainView.frame.width, height: 16 * screenScale))
        contentLabel.text = NSLocalizedString("Coins for exchange", comment: "")
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(contentLabel)
        
        //兑实物按钮
        shippingButton = UIButton(frame: CGRect(x: 10 * screenScale, y: contentLabel.frame.origin.y + contentLabel.frame.height + 30 * screenScale, width: (mainView.frame.width - 30 * screenScale)/2, height: 44 * screenScale))
        shippingButton.layer.masksToBounds = true
        shippingButton.layer.cornerRadius = 6 * screenScale
        shippingButton.layer.borderWidth = 1
        shippingButton.layer.borderColor = UIColor.mainYellow().cgColor
        shippingButton.setTitle(NSLocalizedString("Shipping", comment: ""), for: UIControl.State.normal)
        shippingButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        shippingButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(shippingButton)
        
        //兑金币按钮
        coinButton = UIButton(frame: CGRect(x: shippingButton.frame.origin.x + shippingButton.frame.width + 10 * screenScale, y: shippingButton.frame.origin.y, width: shippingButton.frame.width, height: shippingButton.frame.height))
        coinButton.layer.masksToBounds = true
        coinButton.layer.cornerRadius = shippingButton.layer.cornerRadius
        coinButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 10, bottom: 0, right: 10)
        coinButton.backgroundColor = UIColor.mainYellow()
        coinButton.setTitle(NSLocalizedString("Exchange for coins", comment: ""), for: UIControl.State.normal)
        coinButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        coinButton.titleLabel?.font = shippingButton.titleLabel?.font
        coinButton.titleLabel?.lineBreakMode = NSLineBreakMode.byWordWrapping
        coinButton.titleLabel?.textAlignment = NSTextAlignment.center
        mainView.addSubview(coinButton)
        
        //提示消息
        let messageView = UIView(frame: CGRect(x: 10 * screenScale, y: shippingButton.frame.origin.y + shippingButton.frame.height + 20 * screenScale, width: mainView.frame.width - 20 * screenScale, height: 0))
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: messageView.frame.width, height: 0))
        messageLabel.numberOfLines = 0
        messageLabel.text = NSLocalizedString("exchange message", comment: "")
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmallest() * screenScale)
        messageLabel.sizeToFit()
        messageView.addSubview(messageLabel)
        messageView.frame.size = CGSize(width: messageView.frame.width, height: messageLabel.frame.origin.y + messageLabel.frame.height)
        
        mainView.addSubview(messageView)
        mainView.frame = CGRect(x: mainView.frame.origin.x, y: (parent.frame.height - (messageView.frame.origin.y + messageView.frame.height))/2, width: mainView.frame.width, height: messageView.frame.origin.y + messageView.frame.height + 20 * screenScale)
        self.addSubview(mainView)
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: 0, y: 0, width: 28 * screenScale, height: 28 * screenScale))
        closeButton.center = CGPoint(x: mainView.frame.origin.x + mainView.frame.width, y: mainView.frame.origin.y)
        closeButton.setImage(UIImage(named: "image_close_white"), for: UIControl.State.normal)
        closeButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        self.addSubview(closeButton)
    }
    
    //积分抽奖模式 设置数据
    func setScorelotteryData(data: LuckyFrontUserScorelotteryHistoryModel){
        uuid = data.uuid
        type = "scorelottery"
        
        //奖品图
        imageView.sd_setImage(with: URL(string: data.prizeCover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        
        //奖品内容
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let nameText: NSMutableAttributedString = NSMutableAttributedString(string: data.prizeTitle, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.attributedText = nameText
        nameLabel.alignmentTop()
        
        //价格
        priceLabel.text = "\(LuckyUtils.localCurrencyFormat(amount: data.price))"
        dealPriceLabel.text = LuckyUtils.coinFormat(amount: data.dealPrice)
    }
    
    //零元购模式 设置数据
    func setBuyfreeData(data: LuckyFrontUserBuyfreeOrderModel){
        uuid = data.uuid
        type = "buyfree"
        
        //奖品图
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        
        //奖品内容
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let nameText: NSMutableAttributedString = NSMutableAttributedString(string: data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.attributedText = nameText
        nameLabel.alignmentTop()
        
        //价格
        priceLabel.text = "\(LuckyUtils.moneyFullFormat(amount: data.price))"
        dealPriceLabel.text = LuckyUtils.coinFormat(amount: data.dealPrice)
    }
    
    //关闭
    @objc private func close(){
        self.removeFromSuperview()
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
