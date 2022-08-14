//
//  LuckyActivityCheckinRulesView.swift
//  lucky
//  活动 签到规则
//  Created by Farmer Zhu on 2020/10/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityCheckinRulesView: UIView{

    init() {
        super.init(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
        self.layer.zPosition = 0.6
        self.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        
        //主框体
        let mainView = UIView(frame: CGRect(x: (screenWidth - 283 * screenScale)/2, y: 0, width: 283 * screenScale, height: 0))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 10 * screenScale
        mainView.backgroundColor = UIColor.white
        
        //头部图
        let headerImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: mainView.frame.width, height: 74 * screenScale))
        headerImageView.image = UIImage(named: "image_activity_rewards_rules_title")
        mainView.addSubview(headerImageView)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: 16 * screenScale, y: headerImageView.frame.origin.y + headerImageView.frame.height + 16 * screenScale, width: mainView.frame.width - 32 * screenScale, height: 0))
        contentLabel.numberOfLines = 0
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        style.paragraphSpacing = 6 * screenScale
        let contentString = NSLocalizedString("checkin rules", comment: "")
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentLabel.attributedText = contentText
        contentLabel.sizeToFit()
        mainView.addSubview(contentLabel)
        
        mainView.frame = CGRect(x: mainView.frame.origin.x, y: (screenHeight - (contentLabel.frame.origin.y + contentLabel.frame.height + 20 * screenScale))/2 - 30 * screenScale, width: mainView.frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 20 * screenScale)
        self.addSubview(mainView)
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: (screenWidth - 28 * screenScale)/2, y: mainView.frame.origin.y + mainView.frame.height + 30 * screenScale, width: 28 * screenScale, height: 28 * screenScale))
        closeButton.setImage(UIImage(named: "image_alert_close"), for: UIControl.State.normal)
        closeButton.addTarget(self, action: #selector(removeView), for: UIControl.Event.touchUpInside)
        self.addSubview(closeButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //关闭
    @objc func removeView(){
        self.removeFromSuperview()
    }
}
