//
//  LuckyDetailRecordCellView.swift
//  lucky
//  详情页投注记录cell
//  Created by Farmer Zhu on 2020/9/21.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailRecordCellView: UIView{

    var data: LuckyFrontUserPaymentOrderModel!
    var imageButton: UIButton!
    
    init(frame: CGRect, data: LuckyFrontUserPaymentOrderModel) {
        super.init(frame: frame)
        self.data = data
        
        //头像
        let imageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: (frame.height - 36 * screenScale)/2, width: 36 * screenScale, height: 36 * screenScale))
        imageView.layer.masksToBounds = true
        imageView.layer.cornerRadius = imageView.frame.height/2
        imageView.contentMode = UIView.ContentMode.scaleAspectFill
        imageView.sd_setImage(with: URL(string: data.imageURL), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        imageButton = UIButton(frame: imageView.frame)
        self.addSubview(imageButton)
        
        //用户名
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 8 * screenScale, y: 0, width: frame.width/2 - (imageView.frame.origin.x + imageView.frame.width + 8 * screenScale), height: frame.height))
        nameLabel.text = data.nickname
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(nameLabel)
        
        //投注份数
        let participantLabel = UILabel(frame: CGRect(x: frame.width/2, y: (frame.height - 44 * screenScale)/2, width: frame.width/2 - 10 * screenScale, height: 20 * screenScale))
        participantLabel.textColor = UIColor.fontBlack()
        participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        participantLabel.textAlignment = NSTextAlignment.right
        let textString = "\(NSLocalizedString("Quantity", comment: "")):\(data.buyCount)"
        let participantText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
        participantText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: textString.count - String(data.buyCount).count, length: String(data.buyCount).count))
        participantLabel.attributedText = participantText
        self.addSubview(participantLabel)
        
        //投注时间
        let timeLabel = UILabel(frame: CGRect(x: participantLabel.frame.origin.x, y: participantLabel.frame.origin.y + participantLabel.frame.height + 4 * screenScale, width: participantLabel.frame.width, height: participantLabel.frame.height))
        timeLabel.text = "\(LuckyUtils.timestampFormat(timestamp: data.createtime, format: "MMM dd,yyyy HH:mm:ss")).\(data.createtime % 1000)"
        timeLabel.textColor = UIColor.fontGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        timeLabel.textAlignment = NSTextAlignment.right
        self.addSubview(timeLabel)
        
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
