//
//  LuckyOrderWinningView.swift
//  lucky
//  订单页获奖Cell
//  Created by Farmer Zhu on 2020/9/9.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyOrderWinningView: UIView{
    var data: LuckyWinningInfoModel!
    
    var buyButton: UIButton!
    var funcButton: UIButton?
    
    let paddingLeft: CGFloat = 10 * screenScale

    init(frame: CGRect, data: LuckyWinningInfoModel) {
        super.init(frame: frame)
        self.data = data
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: frame.width, height: frame.height - 16 * screenScale))
        mainView.backgroundColor = UIColor.white
        
        //标题行
        let titleLbael = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: frame.width - paddingLeft * 2, height: 46 * screenScale))
        titleLbael.text = "\(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.winningTime, format: "yyyy-MM-dd HH:mm"))"
        titleLbael.textColor = UIColor.fontBlack()
        titleLbael.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(titleLbael)
        
        if(globalFlagUser){
            //主包 状态标签 获奖标签
            //状态标签
            let statusBgView = UIView(frame: CGRect(x: frame.width - 70 * screenScale, y: 10 * screenScale, width: 100 * screenScale, height: 26 * screenScale))
            statusBgView.layer.masksToBounds = true
            statusBgView.layer.cornerRadius = statusBgView.frame.height/2
            if(data.isRecevice){
                statusBgView.backgroundColor = UIColor.mainLightBlue()
            }else{
                statusBgView.backgroundColor = UIColor.mainLightRed()
            }
            mainView.addSubview(statusBgView)
            
           let statusLabel = UILabel(frame: CGRect(x: statusBgView.frame.origin.x, y: statusBgView.frame.origin.y, width: 70 * screenScale, height: statusBgView.frame.height))
            if(data.isRecevice){
                statusLabel.text = NSLocalizedString("Exchanged", comment: "")
                statusLabel.textColor = UIColor.mainBlue()
            }else{
                statusLabel.text = NSLocalizedString("Winning", comment: "")
                statusLabel.textColor = UIColor.mainRed()
            }
            statusLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            statusLabel.textAlignment = NSTextAlignment.center
            mainView.addSubview(statusLabel)
        
            //获奖标签
            let winningImageView = UIImageView(frame: CGRect(x: statusBgView.frame.origin.x - 70 * screenScale, y: 0, width: 62 * screenScale, height: 43 * screenScale))
            winningImageView.image = UIImage(named: "image_watermark_winning")
            mainView.addSubview(winningImageView)
        }
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: paddingLeft, y: titleLbael.frame.origin.y + titleLbael.frame.height, width: 90 * screenScale, height: 90 * screenScale))
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        mainView.addSubview(imageView)
        
        //商品信息
        let contentLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 12 * screenScale, y: imageView.frame.origin.y, width: frame.width - paddingLeft - (imageView.frame.origin.x + imageView.frame.width + 12 * screenScale), height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        if(data.gameType == "group2"){
            //PK模式标签
            let gameTypeImageView = UIImageView(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 2 * screenScale, width: 53 * screenScale, height: 16 * screenScale))
            gameTypeImageView.image = UIImage(named: "image_pk_type_label")
            mainView.addSubview(gameTypeImageView)
            style.firstLineHeadIndent = gameTypeImageView.frame.width + 4 * screenScale
        }
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentText.addAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        contentLabel.attributedText = contentText
        contentLabel.alignmentTop()
        mainView.addSubview(contentLabel)
        
        //投注量
        let participantLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 46 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
        participantLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
        participantLabel.textColor = UIColor.fontDarkGray()
        participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        participantLabel.sizeToFit()
        participantLabel.frame.size = CGSize(width: participantLabel.frame.width, height: 20 * screenScale)
        mainView.addSubview(participantLabel)
        
        if(data.gameType == "group2"){
            //Pk模式 获胜方图标
            let groupImageView = UIImageView(frame: CGRect(x: participantLabel.frame.origin.x + participantLabel.frame.width + 2 * screenScale, y: participantLabel.frame.origin.y + 3 * screenScale, width: 55 * screenScale, height: 14 * screenScale))
            if(data.luckyGroup == "lucky"){
                groupImageView.image = UIImage(named: "image_order_gourp_red")
            }else{
                groupImageView.image = UIImage(named: "image_order_gourp_blue")
            }
            mainView.addSubview(groupImageView)
        }
        
        //获奖信息
        let numberLabel = UILabel(frame: CGRect(x: participantLabel.frame.origin.x, y: participantLabel.frame.origin.y + participantLabel.frame.height, width: contentLabel.frame.width, height: 20 * screenScale))
        numberLabel.textColor = participantLabel.textColor
        numberLabel.font = participantLabel.font
        if(data.gameType == "group2"){
            //PK模式 获胜组
            let numberText: NSMutableAttributedString = NSMutableAttributedString(string: "\(NSLocalizedString("Winner", comment: "")):\(LuckyUtils.getTeamName(value: data.luckyGroup))")
            numberText.addAttributes([NSAttributedString.Key.foregroundColor : data.luckyGroup == "lucky" ? UIColor.groupRed(): UIColor.groupBlue()], range: NSRange(location: numberText.string.count - LuckyUtils.getTeamName(value: data.luckyGroup).count, length: LuckyUtils.getTeamName(value: data.luckyGroup).count))
            numberLabel.attributedText = numberText
        }else{
            //一般模式
            if(globalFlagUser){
                //主包
                numberLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):\(data.luckynum)"
            }else{
                //马甲
                numberLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):\(data.luckynum)"
            }
        }
        mainView.addSubview(numberLabel)
        
        if(data.gameType == "group2"){
            //PK模式 分得金币数
            let rewardsLabel = UILabel(frame: CGRect(x: participantLabel.frame.origin.x, y: numberLabel.frame.origin.y + numberLabel.frame.height, width: numberLabel.frame.width, height: 20 * screenScale))
            rewardsLabel.textColor = participantLabel.textColor
            rewardsLabel.font = participantLabel.font
            rewardsLabel.text = "\(NSLocalizedString("Rewards", comment: "")):\(LuckyUtils.coinFormat(amount: data.dealPrice)) \(NSLocalizedString("coins", comment: ""))"
            rewardsLabel.sizeToFit()
            rewardsLabel.frame.size = CGSize(width: rewardsLabel.frame.width, height: 20 * screenScale)
            mainView.addSubview(rewardsLabel)
            
            let rewardsImageView = UIImageView(frame: CGRect(x: rewardsLabel.frame.origin.x + rewardsLabel.frame.width + 2 * screenScale, y: rewardsLabel.frame.origin.y + 2 * screenScale, width: 16 * screenScale, height: 16 * screenScale))
            if(globalFlagUser){
                rewardsImageView.image = UIImage(named: "image_gold_coin")
            }else{
                rewardsImageView.image = UIImage(named: "image_gold_dollor")
            }
            mainView.addSubview(rewardsImageView)
        }
        
        //再次购买按钮
        buyButton = UIButton(frame: CGRect(x: frame.width - paddingLeft - 80 * screenScale, y: mainView.frame.height - 46 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
        buyButton.layer.masksToBounds = true
        buyButton.layer.cornerRadius = buyButton.frame.height/2
        buyButton.layer.borderColor = UIColor.fontRed().cgColor
        buyButton.layer.borderWidth = 1
        buyButton.setTitle(NSLocalizedString("Buy More", comment: ""), for: UIControl.State.normal)
        buyButton.setTitleColor(UIColor.fontRed(), for: UIControl.State.normal)
        buyButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(buyButton)
        
        if(data.isRecevice){
            //已兑奖
            if(!data.isComment && data.gameType != "group2"){
                //未评价且不是PK模式 评价按钮
                funcButton = UIButton(frame: CGRect(x: buyButton.frame.origin.x - 12 * screenScale - buyButton.frame.width, y: buyButton.frame.origin.y, width: buyButton.frame.width, height: buyButton.frame.height))
                funcButton?.layer.masksToBounds = true
                funcButton?.layer.cornerRadius = buyButton.layer.cornerRadius
                funcButton?.layer.borderColor = buyButton.layer.borderColor
                funcButton?.layer.borderWidth = buyButton.layer.borderWidth
                funcButton?.setTitle(NSLocalizedString("Review", comment: ""), for: UIControl.State.normal)
                funcButton?.setTitleColor(buyButton.titleColor(for: UIControl.State.normal), for: UIControl.State.normal)
                funcButton?.titleLabel?.font = buyButton.titleLabel?.font
                mainView.addSubview(funcButton!)
            }
        }else{
            //未兑奖 兑奖按钮
            funcButton = UIButton(frame: CGRect(x: buyButton.frame.origin.x - 12 * screenScale - buyButton.frame.width, y: buyButton.frame.origin.y, width: buyButton.frame.width, height: buyButton.frame.height))
            funcButton?.layer.masksToBounds = true
            funcButton?.layer.cornerRadius = buyButton.layer.cornerRadius
            funcButton?.layer.borderColor = buyButton.layer.borderColor
            funcButton?.layer.borderWidth = buyButton.layer.borderWidth
            funcButton?.setTitle(NSLocalizedString("Deliver", comment: ""), for: UIControl.State.normal)
            funcButton?.setTitleColor(buyButton.titleColor(for: UIControl.State.normal), for: UIControl.State.normal)
            funcButton?.titleLabel?.font = buyButton.titleLabel?.font
            mainView.addSubview(funcButton!)
        }
        
        self.addSubview(mainView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
