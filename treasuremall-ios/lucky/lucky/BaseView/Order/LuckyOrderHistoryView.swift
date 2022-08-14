//
//  LuckyOrderHistoryView.swift
//  lucky
//  订单页历史Cell
//  Created by Farmer Zhu on 2020/9/10.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyOrderHistoryView: UIView{
    
    var data: LuckyFrontUserPaymentOrderModel!

    var buyButton: UIButton?
    var funcButton: UIButton?

    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyFrontUserPaymentOrderModel) {
        super.init(frame: frame)
        self.data = data
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: frame.width, height: 0))
        mainView.backgroundColor = UIColor.white
        
        //已开奖 显示开奖时间
        if(data.gameStatus != "betting" && data.gameStatus != "lottery" && data.finishedtime != 0){
            let timeLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: frame.width - paddingLeft * 2, height: 46 * screenScale))
            timeLabel.text = "\(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.finishedtime, format: "yyyy-MM-dd HH:mm"))"
            timeLabel.textColor = UIColor.fontBlack()
            timeLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            mainView.addSubview(timeLabel)
        }
        
        if(globalFlagUser){
            //主包 状态标签 获奖标签
            //状态标签
            let statusBgView = UIView(frame: CGRect(x: frame.width - 70 * screenScale, y: 10 * screenScale, width: 100 * screenScale, height: 26 * screenScale))
            statusBgView.layer.masksToBounds = true
            statusBgView.layer.cornerRadius = statusBgView.frame.height/2
            
            let statusLabel = UILabel(frame: CGRect(x: statusBgView.frame.origin.x, y: statusBgView.frame.origin.y, width: 70 * screenScale, height: statusBgView.frame.height))
            statusLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            statusLabel.textAlignment = NSTextAlignment.center
            
            if(data.gameStatus == "betting" || data.gameStatus == "lottery"){
                statusBgView.backgroundColor = UIColor.mainLightYellow()
                statusLabel.text = NSLocalizedString("Ongoing", comment: "")
                statusLabel.textColor = UIColor.fontRed()
            }else if(!data.isLucky){
                statusBgView.backgroundColor = UIColor.placeholderGray()
                statusLabel.text = NSLocalizedString("Losing", comment: "")
                statusLabel.textColor = UIColor.white
            }else if(data.isRecevice){
                statusBgView.backgroundColor = UIColor.mainLightBlue()
                statusLabel.text = NSLocalizedString("Exchanged", comment: "")
                statusLabel.textColor = UIColor.mainBlue()
            }else{
                statusBgView.backgroundColor = UIColor.mainLightRed()
                statusLabel.text = NSLocalizedString("Winning", comment: "")
                statusLabel.textColor = UIColor.mainRed()
            }
            mainView.addSubview(statusBgView)
            mainView.addSubview(statusLabel)
            
            if(data.isLucky){
                //中奖 获奖标签
                let winningImageView = UIImageView(frame: CGRect(x: statusBgView.frame.origin.x - 70 * screenScale, y: 0, width: 62 * screenScale, height: 43 * screenScale))
                winningImageView.image = UIImage(named: "image_watermark_winning")
                mainView.addSubview(winningImageView)
            }
        }
        
        //商品图
        let imageView = UIImageView()
        if(globalFlagUser || (data.gameStatus != "betting" && data.gameStatus != "lottery" && data.finishedtime != 0)){
            imageView.frame = CGRect(x: paddingLeft, y: 46 * screenScale, width: 90 * screenScale, height: 90 * screenScale)
        }else{
            imageView.frame = CGRect(x: paddingLeft, y: 16 * screenScale, width: 90 * screenScale, height: 90 * screenScale)
        }
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        mainView.addSubview(imageView)
        
        //商品内容
        let contentLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 12 * screenScale, y: imageView.frame.origin.y, width: frame.width - paddingLeft - (imageView.frame.origin.x + imageView.frame.width + 12 * screenScale), height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        if(data.gameType == "group2"){
            //PK模式标签
            let gameTypeImageView = UIImageView(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 2 * screenScale, width: 53 * screenScale, height: 16 * screenScale))
            gameTypeImageView.image = UIImage(named: "image_pk_type_label")
            mainView.addSubview(gameTypeImageView)
            style.firstLineHeadIndent = gameTypeImageView.frame.width + 4 * screenScale
        }
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentText.addAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        contentLabel.attributedText = contentText
        contentLabel.alignmentTop()
        mainView.addSubview(contentLabel)
        
        if(data.gameStatus == "betting"){
            //投注中
            let progressBar = LuckyProgressBar(frame: CGRect(x: contentLabel.frame.origin.x, y: imageView.frame.origin.y + imageView.frame.height - 6 * screenScale, width: contentLabel.frame.width, height: 4 * screenScale))
            //计算投注百分比
            if(data.gameType == "group2"){
                //PK模式
                progressBar.rate = Double(((data.shares / 2) - Int.valueOf(any: (data.groupShares[data.paymentGroup])))) / Double((data.shares / 2))
            }else{
                progressBar.rate = Double(data.betShares)/Double(data.shares)
            }
            mainView.addSubview(progressBar)
            
            //投注量
            let participantLabel = UILabel(frame: CGRect(x: progressBar.frame.origin.x, y: progressBar.frame.origin.y - 24 * screenScale, width: progressBar.frame.width, height: 20 * screenScale))
            participantLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
            participantLabel.textColor = UIColor.fontDarkGray()
            participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            participantLabel.sizeToFit()
            participantLabel.frame.size = CGSize(width: participantLabel.frame.width, height: 20 * screenScale)
            mainView.addSubview(participantLabel)
            
            if(data.gameType == "group2"){
                //PK模式 投注方图标
                let groupImageView = UIImageView(frame: CGRect(x: participantLabel.frame.origin.x + participantLabel.frame.width + 2 * screenScale, y: participantLabel.frame.origin.y + 3 * screenScale, width: 55 * screenScale, height: 14 * screenScale))
                if(data.paymentGroup == "lucky"){
                    groupImageView.image = UIImage(named: "image_order_gourp_red")
                }else{
                    groupImageView.image = UIImage(named: "image_order_gourp_blue")
                }
                mainView.addSubview(groupImageView)
            }
            
            //投注进度
            let progressRateLabel = UILabel(frame: CGRect(x: progressBar.frame.origin.x, y: progressBar.frame.origin.y - 24 * screenScale, width: progressBar.frame.width, height: 20 * screenScale))
            //计算百分比
            if(data.gameType == "group2"){
                progressRateLabel.text = "\(((data.shares / 2) - Int.valueOf(any: (data.groupShares[data.paymentGroup]))) * 100 / (data.shares / 2))%"
            }else{
                progressRateLabel.text = "\(NSLocalizedString("Progress", comment: "")):\(data.betShares * 100/data.shares)%"
            }
            progressRateLabel.textColor = participantLabel.textColor
            progressRateLabel.font = participantLabel.font
            progressRateLabel.textAlignment = NSTextAlignment.right
            mainView.addSubview(progressRateLabel)
            
            //再次购买按钮
            buyButton = UIButton(frame: CGRect(x: frame.width - paddingLeft - 80 * screenScale, y: imageView.frame.origin.y + imageView.frame.height + 16 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
            buyButton!.layer.masksToBounds = true
            buyButton!.layer.cornerRadius = buyButton!.frame.height/2
            buyButton!.layer.borderColor = UIColor.fontRed().cgColor
            buyButton!.layer.borderWidth = 1
            buyButton!.setTitle(NSLocalizedString("Buy More", comment: ""), for: UIControl.State.normal)
            buyButton!.setTitleColor(UIColor.fontRed(), for: UIControl.State.normal)
            buyButton!.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            mainView.addSubview(buyButton!)
            
            mainView.frame.size = CGSize(width: mainView.frame.width, height: buyButton!.frame.origin.y + buyButton!.frame.height + 16 * screenScale)
        }else if(data.gameStatus == "lottery"){
            //开奖中 倒计时
            let countDownView = LuckyCountDownLongView(frame: CGRect(x: contentLabel.frame.origin.x, y: imageView.frame.origin.y + imageView.frame.height - 22 * screenScale, width: 160 * screenScale, height: 20 * screenScale), finishedtime: data.finishedtime)
            mainView.addSubview(countDownView)
            
            let endImageView = UIImageView(frame: CGRect(x: countDownView.frame.origin.x, y: countDownView.frame.origin.y - 24 * screenScale, width: 16 * screenScale, height: 18 * screenScale))
            endImageView.image = UIImage(named: "image_clock")
            mainView.addSubview(endImageView)
            
            let endLabel = UILabel(frame: CGRect(x: endImageView.frame.origin.x + endImageView.frame.width + 6 * screenScale, y: endImageView.frame.origin.y, width: 100 * screenScale, height: endImageView.frame.height))
            endLabel.text = NSLocalizedString("End in", comment: "")
            endLabel.textColor = UIColor.fontDarkGray()
            endLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            mainView.addSubview(endLabel)
            
            mainView.frame.size = CGSize(width: mainView.frame.width, height: imageView.frame.origin.y + imageView.frame.height + 16 * screenScale)
        }else{
            //已开奖
            //投注量
            let participantLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 46 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
            participantLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
            participantLabel.textColor = UIColor.fontDarkGray()
            participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            participantLabel.sizeToFit()
            participantLabel.frame.size = CGSize(width: participantLabel.frame.width, height: 20 * screenScale)
            mainView.addSubview(participantLabel)
            
            if(data.gameType == "group2"){
                //PK模式 获胜方图
                let groupImageView = UIImageView(frame: CGRect(x: participantLabel.frame.origin.x + participantLabel.frame.width + 2 * screenScale, y: participantLabel.frame.origin.y + 3 * screenScale, width: 55 * screenScale, height: 14 * screenScale))
                if(data.paymentGroup == "lucky"){
                    groupImageView.image = UIImage(named: "image_order_gourp_red")
                }else{
                    groupImageView.image = UIImage(named: "image_order_gourp_blue")
                }
                mainView.addSubview(groupImageView)
            }
            
            //订单时间
            let orderTimeLabel = UILabel(frame: CGRect(x: participantLabel.frame.origin.x, y: participantLabel.frame.origin.y + participantLabel.frame.height + 2 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
            orderTimeLabel.text = "\(NSLocalizedString("Order time", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:mm"))"
            orderTimeLabel.textColor = participantLabel.textColor
            orderTimeLabel.font = participantLabel.font
            mainView.addSubview(orderTimeLabel)
            
            //再次购买按钮
            buyButton = UIButton(frame: CGRect(x: frame.width - paddingLeft - 80 * screenScale, y: imageView.frame.origin.y + imageView.frame.height + 16 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
            buyButton!.layer.masksToBounds = true
            buyButton!.layer.cornerRadius = buyButton!.frame.height/2
            buyButton!.layer.borderColor = UIColor.fontRed().cgColor
            buyButton!.layer.borderWidth = 1
            buyButton!.setTitle(NSLocalizedString("Buy More", comment: ""), for: UIControl.State.normal)
            buyButton!.setTitleColor(UIColor.fontRed(), for: UIControl.State.normal)
            buyButton!.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            mainView.addSubview(buyButton!)
            
            if(data.isLucky){
                //获奖
                if(data.isRecevice){
                    //已领奖
                    if(!data.isComment && data.gameType != "group2"){
                        //非PK模式且未晒单 晒单按钮
                        funcButton = UIButton(frame: CGRect(x: buyButton!.frame.origin.x - 12 * screenScale - buyButton!.frame.width, y: buyButton!.frame.origin.y, width: buyButton!.frame.width, height: buyButton!.frame.height))
                        funcButton!.layer.masksToBounds = true
                        funcButton!.layer.cornerRadius = buyButton!.layer.cornerRadius
                        funcButton!.layer.borderColor = buyButton!.layer.borderColor
                        funcButton!.layer.borderWidth = buyButton!.layer.borderWidth
                        funcButton!.setTitle(NSLocalizedString("Review", comment: ""), for: UIControl.State.normal)
                        funcButton!.setTitleColor(buyButton!.titleColor(for: UIControl.State.normal), for: UIControl.State.normal)
                        funcButton!.titleLabel?.font = buyButton!.titleLabel?.font
                        mainView.addSubview(funcButton!)
                    }
                }else{
                    //未领奖 兑奖按钮
                    funcButton = UIButton(frame: CGRect(x: buyButton!.frame.origin.x - 12 * screenScale - buyButton!.frame.width, y: buyButton!.frame.origin.y, width: buyButton!.frame.width, height: buyButton!.frame.height))
                    funcButton!.layer.masksToBounds = true
                    funcButton!.layer.cornerRadius = buyButton!.layer.cornerRadius
                    funcButton!.layer.borderColor = buyButton!.layer.borderColor
                    funcButton!.layer.borderWidth = buyButton!.layer.borderWidth
                    funcButton!.setTitle(NSLocalizedString("Deliver", comment: ""), for: UIControl.State.normal)
                    funcButton!.setTitleColor(buyButton!.titleColor(for: UIControl.State.normal), for: UIControl.State.normal)
                    funcButton!.titleLabel?.font = buyButton!.titleLabel?.font
                    mainView.addSubview(funcButton!)
                }
            }
            
            mainView.frame.size = CGSize(width: mainView.frame.width, height: buyButton!.frame.origin.y + buyButton!.frame.height + 16 * screenScale)
        }
        
        self.addSubview(mainView)
        self.frame.size = CGSize(width: frame.width, height: mainView.frame.origin.y + mainView.frame.height)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
