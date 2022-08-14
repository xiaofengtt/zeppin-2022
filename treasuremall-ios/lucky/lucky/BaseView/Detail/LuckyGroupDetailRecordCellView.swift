//
//  LuckyGroupDetailRecordCellView.swift
//  lucky
//  PK投注数据Cell
//  Created by Farmer Zhu on 2020/9/29.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupDetailRecordCellView: UIView{
    
    init(frame: CGRect, redData: LuckyFrontUserPaymentOrderModel?, blueData: LuckyFrontUserPaymentOrderModel?, isEnd: Bool) {
        super.init(frame: frame)
        self.backgroundColor = UIColor(red: 255/255, green: 247/255, blue: 242/255, alpha: 1)
        
        //分割线
        let spaceLine = CALayer()
        if(isEnd){
            spaceLine.frame = CGRect(x: frame.width/2, y: 0, width: 1, height: frame.height - 16 * screenScale)
        }else{
            spaceLine.frame = CGRect(x: frame.width/2, y: 0, width: 1, height: frame.height)
        }
        spaceLine.backgroundColor = UIColor(red: 255/255, green: 238/255, blue: 216/255, alpha: 1).cgColor
        self.layer.addSublayer(spaceLine)
        
        if(redData != nil){
            //有红队数据
            
            //头像
            let redImageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: 0, width: 24 * screenScale, height: 24 * screenScale))
            redImageView.layer.masksToBounds = true
            redImageView.layer.cornerRadius = redImageView.frame.height/2
            redImageView.contentMode = UIView.ContentMode.scaleAspectFill
            redImageView.sd_setImage(with: URL(string: redData!.imageURL), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            self.addSubview(redImageView)
            
            if(redData!.isLucky){
                //红队获胜
                let vipImageView = UIImageView(frame: CGRect(x: redImageView.frame.origin.x - 4 * screenScale, y: redImageView.frame.origin.y + 18 * screenScale, width: 32 * screenScale, height: 10 * screenScale))
                vipImageView.image = UIImage(named: "image_detail_pk_mvp")
                self.addSubview(vipImageView)
            }
            
            //用户名
            let redNameLabel = UILabel(frame: CGRect(x: redImageView.frame.origin.x + redImageView.frame.width + 4 * screenScale, y: redImageView.frame.origin.y, width: frame.width/2 - (redImageView.frame.origin.x + redImageView.frame.width + 4 * screenScale), height: redImageView.frame.height))
            redNameLabel.text = redData!.nickname
            redNameLabel.textColor = UIColor.groupRed()
            redNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            self.addSubview(redNameLabel)
            
            //投注份数
            let redParticipantLabel = UILabel(frame: CGRect(x: redNameLabel.frame.origin.x, y: redNameLabel.frame.origin.y + redNameLabel.frame.height + 5 * screenScale, width: redNameLabel.frame.width, height: 20 * screenScale))
            redParticipantLabel.textColor = UIColor.fontBlack()
            redParticipantLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            let textString = "\(NSLocalizedString("Quantity", comment: "")):\(redData!.buyCount)"
            let participantText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
            participantText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.fontYellow()], range: NSRange(location: textString.count - String(redData!.buyCount).count, length: String(redData!.buyCount).count))
            redParticipantLabel.attributedText = participantText
            self.addSubview(redParticipantLabel)
            
            //投注时间
            let redTimeLabel = UILabel(frame: CGRect(x: redNameLabel.frame.origin.x, y: redParticipantLabel.frame.origin.y + redParticipantLabel.frame.height + 3 * screenScale, width: redNameLabel.frame.width, height: 20 * screenScale))
            redTimeLabel.text = LuckyUtils.timestampFormat(timestamp: redData!.createtime, format: "yyyy-MM-dd HH:mm")
            redTimeLabel.textColor = UIColor.fontBlack()
            redTimeLabel.font = redParticipantLabel.font
            self.addSubview(redTimeLabel)
        }
        
        if(blueData != nil){
            //有蓝队数据
            
            //头像
            let blueImageView = UIImageView(frame: CGRect(x:frame.width/2 + 16 * screenScale, y: 0, width: 24 * screenScale, height: 24 * screenScale))
            blueImageView.layer.masksToBounds = true
            blueImageView.layer.cornerRadius = blueImageView.frame.height/2
            blueImageView.contentMode = UIView.ContentMode.scaleAspectFill
            blueImageView.sd_setImage(with: URL(string: blueData!.imageURL), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            self.addSubview(blueImageView)
            
            if(blueData!.isLucky){
                //蓝队获胜
                let vipImageView = UIImageView(frame: CGRect(x: blueImageView.frame.origin.x - 4 * screenScale, y: blueImageView.frame.origin.y + 18 * screenScale, width: 32 * screenScale, height: 10 * screenScale))
                vipImageView.image = UIImage(named: "image_detail_pk_mvp")
                self.addSubview(vipImageView)
            }
            
            //用户名
            let blueNameLabel = UILabel(frame: CGRect(x: blueImageView.frame.origin.x + blueImageView.frame.width + 4 * screenScale, y: blueImageView.frame.origin.y, width: frame.width - (blueImageView.frame.origin.x + blueImageView.frame.width + 4 * screenScale), height: blueImageView.frame.height))
            blueNameLabel.text = blueData!.nickname
            blueNameLabel.textColor = UIColor.groupRed()
            blueNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            self.addSubview(blueNameLabel)
            
            //投注份数
            let blueParticipantLabel = UILabel(frame: CGRect(x: blueNameLabel.frame.origin.x, y: blueNameLabel.frame.origin.y + blueNameLabel.frame.height + 5 * screenScale, width: blueNameLabel.frame.width, height: 20 * screenScale))
            blueParticipantLabel.textColor = UIColor.fontBlack()
            blueParticipantLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            let textString = "\(NSLocalizedString("Quantity", comment: "")):\(blueData!.buyCount)"
            let participantText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
            participantText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainYellow()], range: NSRange(location: textString.count - String(blueData!.buyCount).count, length: String(blueData!.buyCount).count))
            blueParticipantLabel.attributedText = participantText
            self.addSubview(blueParticipantLabel)
            
            //投注时间
            let blueTimeLabel = UILabel(frame: CGRect(x: blueNameLabel.frame.origin.x, y: blueParticipantLabel.frame.origin.y + blueParticipantLabel.frame.height + 3 * screenScale, width: blueNameLabel.frame.width, height: 20 * screenScale))
            blueTimeLabel.text = LuckyUtils.timestampFormat(timestamp: blueData!.createtime, format: "yyyy-MM-dd HH:mm")
            blueTimeLabel.textColor = UIColor.fontBlack()
            blueTimeLabel.font = blueParticipantLabel.font
            self.addSubview(blueTimeLabel)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
