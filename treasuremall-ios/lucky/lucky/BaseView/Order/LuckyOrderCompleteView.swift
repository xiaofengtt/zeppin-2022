//
//  LuckyOrderCompleteView.swift
//  lucky
//  订单页完成Cell
//  Created by Farmer Zhu on 2020/9/9.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyOrderCompleteView: UIView{
    var data: LuckyWinningInfoModel!
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyWinningInfoModel) {
        super.init(frame: frame)
        self.data = data
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: frame.width, height: frame.height - 16 * screenScale))
        mainView.backgroundColor = UIColor.white
        
        //标题行
        let titleLbael = UILabel(frame: CGRect(x: paddingLeft, y: 12 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
        titleLbael.text = "\(NSLocalizedString("Exchanged at", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:mm"))"
        titleLbael.textColor = UIColor.fontBlack()
        titleLbael.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(titleLbael)
        
        if(globalFlagUser){
            //主包 状态标签
            let statusLabel = UILabel(frame: titleLbael.frame)
            if(data.type == "gold"){
                statusLabel.text = NSLocalizedString("Completed", comment: "")
            }else{
                if(data.status == "confirm" || data.status == "return"){
                    statusLabel.text = NSLocalizedString("Completed", comment: "")
                }else{
                    statusLabel.text = NSLocalizedString("Ongoing", comment: "")
                }
            }
            statusLabel.textColor = UIColor.mainRed()
            statusLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            statusLabel.textAlignment = NSTextAlignment.right
            mainView.addSubview(statusLabel)
        }
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: paddingLeft, y: titleLbael.frame.origin.y + titleLbael.frame.height + 12 * screenScale, width: mainView.frame.height - 16 * screenScale - (titleLbael.frame.origin.y + titleLbael.frame.height + 12 * screenScale), height: mainView.frame.height - 16 * screenScale - (titleLbael.frame.origin.y + titleLbael.frame.height + 12 * screenScale)))
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        mainView.addSubview(imageView)
        
        //商品内容
        let contentLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 12 * screenScale, y: imageView.frame.origin.y, width: frame.width - paddingLeft - (imageView.frame.origin.x + imageView.frame.width + 12 * screenScale), height: 60 * screenScale))
        contentLabel.numberOfLines = 3
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        if(data.gameType == "group2"){
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
        
        if(data.type == "gold"){
            //兑换金币 显示兑换额
            let coinTextLabel = UILabel()
            coinTextLabel.text = "\(NSLocalizedString("Coins exchanged", comment: "")):"
            coinTextLabel.textColor = UIColor.fontDarkGray()
            coinTextLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            coinTextLabel.sizeToFit()
            coinTextLabel.frame = CGRect(x: contentLabel.frame.origin.x, y: imageView.frame.origin.y + imageView.frame.height - 20 * screenScale, width: coinTextLabel.frame.width, height: 20 * screenScale)
            mainView.addSubview(coinTextLabel)
            
            let coinNumLabel = UILabel()
            coinNumLabel.text = LuckyUtils.coinFormat(amount: data.dealPrice)
            coinNumLabel.textColor = UIColor.mainRed()
            coinNumLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
            coinNumLabel.sizeToFit()
            coinNumLabel.frame = CGRect(x: coinTextLabel.frame.origin.x + coinTextLabel.frame.width + 2 * screenScale, y: coinTextLabel.frame.origin.y, width: coinNumLabel.frame.width, height: coinTextLabel.frame.height)
            mainView.addSubview(coinNumLabel)
            
            let coinImageView = UIImageView(frame: CGRect(x: coinNumLabel.frame.origin.x + coinNumLabel.frame.width + 2 * screenScale, y: coinTextLabel.frame.origin.y + (coinTextLabel.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
            if(globalFlagUser){
                coinImageView.image = UIImage(named: "image_gold_coin")
            }else{
                coinImageView.image = UIImage(named: "image_gold_dollor")
            }
            mainView.addSubview(coinImageView)
        }else {
            //兑换实物 显示地址
            let addressLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 60 * screenScale, width: contentLabel.frame.width, height: 40 * screenScale))
            addressLabel.text = data.detailInfo?.address
            addressLabel.textColor = UIColor.fontDarkGray()
            addressLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            mainView.addSubview(addressLabel)
        }
        
        self.addSubview(mainView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
