//
//  LuckyDetailWinningCellView.swift
//  lucky
//  详情页往期获胜Cell
//  Created by Farmer Zhu on 2020/9/21.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailWinningCellView: UIView{

    var data: LuckyWinningInfoModel!
    var imageButton: UIButton!
    
    init(frame: CGRect, data: LuckyWinningInfoModel) {
        super.init(frame: frame)
        self.data = data
        
        //期数
        let periodLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 12 * screenScale, width: frame.width - 20 * screenScale, height: 20 * screenScale))
        periodLabel.text = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | \(NSLocalizedString("Results for", comment: "")): \(LuckyUtils.timestampFormat(timestamp: data.winningTime, format: "MMM dd,yyyy HH:mm"))"
        periodLabel.textColor = UIColor.fontBlack()
        periodLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(periodLabel)
        
        //用户头像
        let imageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: periodLabel.frame.origin.y + periodLabel.frame.height - 4 * screenScale + (frame.height - (periodLabel.frame.origin.y + periodLabel.frame.height - 4 * screenScale) - 50 * screenScale)/2, width: 50 * screenScale, height: 50 * screenScale))
        imageView.layer.masksToBounds = true
        imageView.layer.cornerRadius = imageView.frame.height/2
        imageView.sd_setImage(with: URL(string: data.imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        imageButton = UIButton(frame: imageView.frame)
        self.addSubview(imageButton)
        
        //用户名标题
        let userNameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 30 * screenScale, y: periodLabel.frame.origin.y + periodLabel.frame.height + 10 * screenScale, width: 74 * screenScale, height: 20 * screenScale))
        userNameLabel.text = "\(NSLocalizedString("Winner", comment: "")):"
        userNameLabel.textColor = UIColor.fontGray()
        userNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(userNameLabel)
        
        //showID标题
        let showIdNameLabel = UILabel(frame: CGRect(x: userNameLabel.frame.origin.x, y: userNameLabel.frame.origin.y + userNameLabel.frame.height + 4 * screenScale, width: userNameLabel.frame.width, height: userNameLabel.frame.height))
        showIdNameLabel.text = "\(NSLocalizedString("ID", comment: "")):"
        showIdNameLabel.textColor = userNameLabel.textColor
        showIdNameLabel.font = userNameLabel.font
        self.addSubview(showIdNameLabel)
        
        //购买份数标题
        let participantNameLabel = UILabel(frame: CGRect(x: userNameLabel.frame.origin.x, y: showIdNameLabel.frame.origin.y + showIdNameLabel.frame.height + 4 * screenScale, width: userNameLabel.frame.width, height: userNameLabel.frame.height))
        participantNameLabel.text = "\(NSLocalizedString("Quantity", comment: "")):"
        participantNameLabel.textColor = userNameLabel.textColor
        participantNameLabel.font = userNameLabel.font
        self.addSubview(participantNameLabel)
        
        //中奖幸运号标题
        let luckyNumberNameLabel = UILabel(frame: CGRect(x: userNameLabel.frame.origin.x, y: participantNameLabel.frame.origin.y + participantNameLabel.frame.height + 4 * screenScale, width: userNameLabel.frame.width, height: userNameLabel.frame.height))
        if(globalFlagUser){
            //正式
            luckyNumberNameLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):"
        }else{
            //马甲
            luckyNumberNameLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):"
        }
        luckyNumberNameLabel.textColor = userNameLabel.textColor
        luckyNumberNameLabel.font = userNameLabel.font
        self.addSubview(luckyNumberNameLabel)
        
        //用户名值
        let userValueLabel = UILabel(frame: CGRect(x: userNameLabel.frame.origin.x + userNameLabel.frame.width, y: userNameLabel.frame.origin.y, width: frame.width - (userNameLabel.frame.origin.x + userNameLabel.frame.width), height: userNameLabel.frame.height))
        userValueLabel.text = String(data.nickname)
        userValueLabel.textColor = UIColor.fontBlack()
        userValueLabel.font = userNameLabel.font
        self.addSubview(userValueLabel)
        
        //showid值
        let showIdValueLabel = UILabel(frame: CGRect(x: userValueLabel.frame.origin.x, y: showIdNameLabel.frame.origin.y, width: userValueLabel.frame.width, height: showIdNameLabel.frame.height))
        showIdValueLabel.text = String(data.showId)
        showIdValueLabel.textColor = userValueLabel.textColor
        showIdValueLabel.font = userValueLabel.font
        self.addSubview(showIdValueLabel)
        
        //购买份数值
        let participantValueLabel = UILabel(frame: CGRect(x: userValueLabel.frame.origin.x, y: participantNameLabel.frame.origin.y, width: userValueLabel.frame.width, height: participantNameLabel.frame.height))
        participantValueLabel.text = String(data.buyCount)
        participantValueLabel.textColor = UIColor.mainRed()
        participantValueLabel.font = userValueLabel.font
        self.addSubview(participantValueLabel)
        
        //幸运号值
        let luckyNumberValueLabel = UILabel(frame: CGRect(x: userValueLabel.frame.origin.x, y: luckyNumberNameLabel.frame.origin.y, width: userValueLabel.frame.width, height: luckyNumberNameLabel.frame.height))
        luckyNumberValueLabel.text = String(data.luckynum)
        luckyNumberValueLabel.textColor = participantValueLabel.textColor
        luckyNumberValueLabel.font = userValueLabel.font
        self.addSubview(luckyNumberValueLabel)
        
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
