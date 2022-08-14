//
//  LuckyPersonalWinningView.swift
//  lucky
//  个人中心 中奖Cell
//  Created by Farmer Zhu on 2020/9/15.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPersonalWinningView: UIView{
    var data: LuckyWinningInfoModel!
    
    let paddingLeft: CGFloat = 10 * screenScale

    init(frame: CGRect, data: LuckyWinningInfoModel) {
        super.init(frame: frame)
        self.data = data
        
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
        
            let winningImageView = UIImageView(frame: CGRect(x: statusBgView.frame.origin.x - 70 * screenScale, y: 0, width: 62 * screenScale, height: 43 * screenScale))
            winningImageView.image = UIImage(named: "image_watermark_winning")
            mainView.addSubview(winningImageView)
        }
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: paddingLeft, y: titleLbael.frame.origin.y + titleLbael.frame.height, width: 90 * screenScale, height: 90 * screenScale))
        imageView.sd_setImage(with: URL(string: data.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        mainView.addSubview(imageView)
        
        //商品内容
        let contentLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 12 * screenScale, y: imageView.frame.origin.y, width: frame.width - paddingLeft - (imageView.frame.origin.x + imageView.frame.width + 12 * screenScale), height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        contentLabel.attributedText = contentText
        contentLabel.alignmentTop()
        mainView.addSubview(contentLabel)
        
        //投注数
        let participantLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + 46 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
        participantLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
        participantLabel.textColor = UIColor.fontDarkGray()
        participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(participantLabel)
        
        //幸运号
        let numberLabel = UILabel(frame: CGRect(x: participantLabel.frame.origin.x, y: participantLabel.frame.origin.y + participantLabel.frame.height, width: participantLabel.frame.width, height: 20 * screenScale))
        if(globalFlagUser){
            numberLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):\(data.luckynum)"
        }else{
            numberLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):\(data.luckynum)"
        }
        numberLabel.textColor = participantLabel.textColor
        numberLabel.font = participantLabel.font
        mainView.addSubview(numberLabel)
        
        self.addSubview(mainView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
