//
//  LuckyActivityWinningAlertView.swift
//  lucky
//  活动 获奖提示
//  Created by Farmer Zhu on 2020/10/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityWinningAlertView: UIView{
    
    init(type: String, name: String, days: Int?) {
        super.init(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
        self.layer.zPosition = 0.6
        self.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 46 * screenScale, y: (screenHeight - 222 * screenScale)/2, width: screenWidth - 46 * screenScale * 2, height: 222 * screenScale))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 10 * screenScale
        mainView.backgroundColor = UIColor.white
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 82 * screenScale, width: mainView.frame.width, height: 20 * screenScale))
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        if(type == "checkin"){
            //签到
            let dayNum = days == nil ? 1 : days!
            let titleString = "\(NSLocalizedString("Continuous sign in", comment: "")) \(dayNum) \(NSLocalizedString("days", comment: ""))"
            let titleText = NSMutableAttributedString(string: titleString)
            let endString = " \(NSLocalizedString("days", comment: ""))"
            titleText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.activityMainColor()], range: NSRange(location: titleString.count - endString.count - 1, length: 1))
            titleLabel.attributedText = titleText
        }else if(type == "scorelottery"){
            //积分抽奖
            titleLabel.text = NSLocalizedString("You Won", comment: "")
        }
        mainView.addSubview(titleLabel)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 16 * screenScale, width: mainView.frame.width, height: 24 * screenScale))
        contentLabel.textColor = UIColor.activityMainColor()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        if(type == "checkin"){
            //签到
            let contentText = NSMutableAttributedString(string: "\(NSLocalizedString("You won", comment: "")) \(name)")
            let headString = "\(NSLocalizedString("You won", comment: ""))"
            contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.fontBlack()], range: NSRange(location: 0, length: headString.count))
            contentLabel.attributedText = contentText
        }else if(type == "scorelottery"){
            //积分抽奖
            contentLabel.text = name
        }
        mainView.addSubview(contentLabel)
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: (mainView.frame.width - 200 * screenScale)/2, y: mainView.frame.height - 56 * screenScale, width: 200 * screenScale, height: 36 * screenScale))
        closeButton.layer.masksToBounds = true
        closeButton.layer.cornerRadius = closeButton.frame.height/2
        closeButton.layer.borderWidth = 1
        closeButton.layer.borderColor = UIColor.activityMainColor().cgColor
        closeButton.setTitle(NSLocalizedString("OK", comment: ""), for: UIControl.State.normal)
        closeButton.setTitleColor(UIColor.activityMainColor(), for: UIControl.State.normal)
        closeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        closeButton.addTarget(self, action: #selector(removeView), for: UIControl.Event.touchUpInside)
        mainView.addSubview(closeButton)
        self.addSubview(mainView)
        
        //标题图
        let titleImageView = UIImageView(frame: CGRect(x: (screenWidth - 172 * screenScale)/2, y: mainView.frame.origin.y - 80 * screenScale, width: 172 * screenScale, height: 172 * screenScale))
        if(type == "checkin"){
            titleImageView.image = UIImage(named: "image_activity_winning_checkin")
        }else if(type == "scorelottery"){
            titleImageView.image = UIImage(named: "image_activity_winning_scorelottery")
        }
        self.addSubview(titleImageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //关闭
    @objc func removeView(){
        self.removeFromSuperview()
    }
}
