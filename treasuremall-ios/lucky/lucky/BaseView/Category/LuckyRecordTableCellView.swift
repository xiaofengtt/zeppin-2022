//
//  LuckyRecordTableCellView.swift
//  lucky
//  开奖列表cell
//  Created by Farmer Zhu on 2020/9/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRecordTableCellView: UIView{
    var data: LuckyLuckygameGoodsIssueModel!
    
    let padding: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel) {
        super.init(frame: frame)
        self.data = data
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: padding, y: padding, width: frame.height - padding * 2, height: frame.height - padding * 2))
        imageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        
        if(globalFlagUser){
            //正式
            var flagPriceTag = false
            
            //金额tag 10 100
            if(data.betPerShare == 10 || data.betPerShare == 100){
                flagPriceTag = true
                let tagLabelBgImageView = UIImageView(frame: CGRect(x: imageView.frame.width - 38 * screenScale, y: 0, width: 38 * screenScale, height: 20 * screenScale))
                if(data.betPerShare == 100){
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_100")
                }else{
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_10")
                }
                imageView.addSubview(tagLabelBgImageView)
            }
            
            //新品tag
            if(data.tabs.contains("newgoods")){
                var imagePaddingRight: CGFloat = 33 * screenScale
                if(flagPriceTag){
                    imagePaddingRight = 76 * screenScale
                }
                let newImageView = UIImageView(frame: CGRect(x: imageView.frame.width - imagePaddingRight, y: 0, width: 33 * screenScale, height: 20 * screenScale))
                newImageView.image = UIImage(named: "image_goods_tag_new")
                imageView.addSubview(newImageView)
            }
        }
        
        
        //开奖状态tag
        let statusTagLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + padding, y: 14 * screenScale, width: 50 * screenScale, height: 16 * screenScale))
        statusTagLabel.layer.masksToBounds = true
        statusTagLabel.layer.cornerRadius = 4 * screenScale
        statusTagLabel.layer.borderWidth = 1 * screenScale
        if(data.status == "finished"){
            //开奖完成
            statusTagLabel.layer.borderColor =  UIColor(red: 4/255, green: 152/255, blue: 255/255, alpha: 1).cgColor
            statusTagLabel.backgroundColor = UIColor.mainLightBlue()
            statusTagLabel.text = NSLocalizedString("Ended", comment: "")
            statusTagLabel.textColor = UIColor(red: 4/255, green: 152/255, blue: 255/255, alpha: 1)
        }else{
            //开奖中
            statusTagLabel.layer.borderColor =  UIColor.mainRed().cgColor
            statusTagLabel.backgroundColor = UIColor(red: 255/255, green: 233/255, blue: 222/255, alpha: 1)
            statusTagLabel.text = NSLocalizedString("Ongoing", comment: "")
            statusTagLabel.textColor = UIColor.mainRed()
        }
        statusTagLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        statusTagLabel.textAlignment = NSTextAlignment.center
        self.addSubview(statusTagLabel)
        
        //商品名
        let nameLabel = UILabel(frame: CGRect(x: statusTagLabel.frame.origin.x, y: 10 * screenScale, width: frame.width - padding - statusTagLabel.frame.origin.x, height: 48 * screenScale))
        nameLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        style.firstLineHeadIndent = 54 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        nameLabel.attributedText = NSAttributedString(string: data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        nameLabel.alignmentTop()
        self.addSubview(nameLabel)
        
        if(data.status == "finished"){
            //开奖完成
            
            //获奖者
            let winnerTitleLabel = UILabel()
            if(globalFlagUser){
                //正式
                winnerTitleLabel.text = "\(NSLocalizedString("Winner", comment: "")):"
            }else{
                //马甲
                winnerTitleLabel.text = "\(NSLocalizedString("Price", comment: "")):"
            }
            winnerTitleLabel.textColor = UIColor.fontGray()
            winnerTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            winnerTitleLabel.sizeToFit()
            winnerTitleLabel.frame = CGRect(x: statusTagLabel.frame.origin.x, y: 54 * screenScale, width: winnerTitleLabel.frame.width, height: 18 * screenScale)
            self.addSubview(winnerTitleLabel)
            
            let winnerContentLabel = UILabel(frame: CGRect(x: winnerTitleLabel.frame.origin.x + winnerTitleLabel.frame.width, y: winnerTitleLabel.frame.origin.y, width: frame.width - padding - (winnerTitleLabel.frame.origin.x + winnerTitleLabel.frame.width), height: winnerTitleLabel.frame.height))
            if(globalFlagUser){
                //正式
                winnerContentLabel.text = data.nickname
            }else{
                //马甲
                winnerContentLabel.text = "$\(LuckyUtils.moneyFormat(amount: data.betPerShare))"
            }
            winnerContentLabel.textColor = UIColor.mainRed()
            winnerContentLabel.font = winnerTitleLabel.font
            self.addSubview(winnerContentLabel)
            
            //幸运号
            let numTitleLabel = UILabel()
            if(globalFlagUser){
                //正式
                numTitleLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):"
            }else{
                //马甲
                numTitleLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):"
            }
            numTitleLabel.textColor = winnerTitleLabel.textColor
            numTitleLabel.font = winnerTitleLabel.font
            numTitleLabel.sizeToFit()
            numTitleLabel.frame = CGRect(x: winnerTitleLabel.frame.origin.x, y: winnerTitleLabel.frame.origin.y + winnerTitleLabel.frame.height, width: numTitleLabel.frame.width, height: winnerTitleLabel.frame.height)
            self.addSubview(numTitleLabel)
            
            let numContentLabel = UILabel(frame: CGRect(x: numTitleLabel.frame.origin.x + numTitleLabel.frame.width, y: numTitleLabel.frame.origin.y, width: frame.width - padding - (numTitleLabel.frame.origin.x + numTitleLabel.frame.width), height: numTitleLabel.frame.height))
            numContentLabel.text = String(data.luckyNumber)
            numContentLabel.textColor = winnerContentLabel.textColor
            numContentLabel.font = numTitleLabel.font
            self.addSubview(numContentLabel)
            
            //投注量
            let participantLabel = UILabel(frame: CGRect(x: winnerTitleLabel.frame.origin.x, y: numTitleLabel.frame.origin.y + numTitleLabel.frame.height, width: frame.width - winnerTitleLabel.frame.origin.x, height: winnerTitleLabel.frame.height))
            participantLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
            participantLabel.textColor = winnerTitleLabel.textColor
            participantLabel.font = winnerTitleLabel.font
            self.addSubview(participantLabel)
            
            //开奖时间
            let timeLabel = UILabel(frame: CGRect(x: winnerTitleLabel.frame.origin.x, y: participantLabel.frame.origin.y + participantLabel.frame.height, width: participantLabel.frame.width, height: participantLabel.frame.height))
            timeLabel.text = "\(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.finishedtime, format: "yyyy-MM-dd HH:mm:ss"))"
            timeLabel.textColor = participantLabel.textColor
            timeLabel.font = participantLabel.font
            self.addSubview(timeLabel)
        }else{
            //开奖中
            
            //结束时间
            let endImageView = UIImageView(frame: CGRect(x: statusTagLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height + 16 * screenScale, width: 16 * screenScale, height: 18 * screenScale))
            endImageView.image = UIImage(named: "image_clock")
            self.addSubview(endImageView)
            
            let endLabel = UILabel(frame: CGRect(x: endImageView.frame.origin.x + endImageView.frame.width + 6 * screenScale, y: endImageView.frame.origin.y, width: 100 * screenScale, height: endImageView.frame.height))
            endLabel.text = NSLocalizedString("End in", comment: "")
            endLabel.textColor = UIColor.fontDarkGray()
            endLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            self.addSubview(endLabel)
            
            //倒计时
            let countDownView = LuckyCountDownLongView(frame: CGRect(x: statusTagLabel.frame.origin.x, y: endLabel.frame.origin.y + endLabel.frame.height + 12 * screenScale, width: 160 * screenScale, height: 26 * screenScale), finishedtime: data.finishedtime)
            self.addSubview(countDownView)
        }
        
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
