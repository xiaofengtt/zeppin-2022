//
//  LuckyMessageCellView.swift
//  lucky
//  系统信息cell
//  Created by Farmer Zhu on 2020/8/31.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMessageCellView: UIView{
    var message: LuckyFrontUserMessageModel!
    var messageView: UIView!
    var titleLabel: UILabel!
    var readPoint: UIView!
    var messageLabel: UILabel!
    var openView: UIView!
    var openImageView: UIImageView!
    
    let paddingLeft: CGFloat = 10 * screenScale

    init(frame: CGRect, message: LuckyFrontUserMessageModel) {
        super.init(frame: frame)
        self.message = message
        
        //日期
        let dateLabel = UILabel()
        dateLabel.backgroundColor = UIColor.placeholderGray()
        dateLabel.numberOfLines = 1
        dateLabel.text = LuckyUtils.timestampFormat(timestamp: message.createtime, format: "MMM dd HH:mm")
        dateLabel.textColor = UIColor.white
        dateLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        dateLabel.textAlignment = NSTextAlignment.center
        dateLabel.sizeToFit()
        dateLabel.frame = CGRect(x: (frame.width - (dateLabel.frame.width + 28 * screenScale))/2, y: 12 * screenScale, width: dateLabel.frame.width + 28 * screenScale, height: 22 * screenScale)
        dateLabel.layer.masksToBounds = true
        dateLabel.layer.cornerRadius = dateLabel.frame.height/2
        self.addSubview(dateLabel)
        
        //信息框
        messageView = UIView(frame: CGRect(x: paddingLeft, y: dateLabel.frame.origin.y + dateLabel.frame.height + 12 * screenScale, width: frame.width - paddingLeft * 2, height: 110 * screenScale))
        messageView.backgroundColor = UIColor.white
        messageView.layer.masksToBounds = true
        messageView.layer.cornerRadius = 4 * screenScale
        
        //标题
        titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 12 * screenScale, width: messageView.frame.width - paddingLeft * 2 - 20 * screenScale, height: 20 * screenScale))
        titleLabel.text = message.title
        if(message.status == "normal"){
            titleLabel.textColor = UIColor.mainYellow()
        }else{
            titleLabel.textColor = UIColor.fontBlack()
        }
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        messageView.addSubview(titleLabel)
        
        //未读红点
        readPoint = UIView(frame: CGRect(x: messageView.frame.width - paddingLeft - 6 * screenScale, y: titleLabel.frame.origin.y + (titleLabel.frame.height - 6 * screenScale)/2, width: 6 * screenScale, height: 6 * screenScale))
        readPoint.layer.masksToBounds = true
        readPoint.layer.cornerRadius = readPoint.frame.height/2
        readPoint.backgroundColor = UIColor.mainRed()
        if(message.status == "normal"){
            readPoint.isHidden = false
        }else{
            readPoint.isHidden = true
        }
        messageView.addSubview(readPoint)
        
        //展开
        openView = UIView(frame: CGRect(x: 0, y: messageView.frame.height - 34 * screenScale, width: messageView.frame.width, height: 34 * screenScale))
        let openTopLine = CALayer()
        openTopLine.frame = CGRect(x: 0, y: 0, width: openView.frame.width, height: 1)
        openTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        openView.layer.addSublayer(openTopLine)
        openImageView = UIImageView(frame: CGRect(x: (openView.frame.width - 12 * screenScale)/2, y: (openView.frame.height - 13 * screenScale)/2, width: 12 * screenScale, height: 13 * screenScale))
        openImageView.image = UIImage(named: "image_message_open")
        openView.addSubview(openImageView)
        messageView.addSubview(openView)
        
        //内容
        messageLabel = UILabel(frame: CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 8 * screenScale, width: messageView.frame.width - paddingLeft * 2, height: 20 * screenScale))
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        messageLabel.attributedText = NSAttributedString(string: message.content, attributes: [NSAttributedString.Key.paragraphStyle : style])
        messageLabel.numberOfLines = 1
        messageLabel.textColor = UIColor.fontBlack()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        messageView.addSubview(messageLabel)
        
        self.addSubview(messageView)
        self.frame.size = CGSize(width: self.frame.width, height: messageView.frame.origin.y + messageView.frame.height)
        
        //设置展开状态
        if(message.isOpen){
            open()
        }else{
            close()
        }
    }
    
    //展开信息
    func open(){
        messageLabel.numberOfLines = 0
        messageLabel.sizeToFit()
        messageLabel.frame = CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 8 * screenScale, width: messageView.frame.width - paddingLeft * 2, height: messageLabel.frame.height)
        messageView.frame.size = CGSize(width: messageView.frame.width, height: messageLabel.frame.origin.y + messageLabel.frame.height + 42 * screenScale)
        openView.frame.origin = CGPoint(x: 0, y: messageView.frame.height - 34 * screenScale)
        openImageView.image = UIImage(named: "image_message_open")
        message.isOpen = true
        self.frame.size = CGSize(width: self.frame.width, height: messageView.frame.origin.y + messageView.frame.height)
    }
    
    //收起信息
    func close(){
        messageView.frame.size = CGSize(width: messageView.frame.width, height: 110 * screenScale)
        openImageView.image = UIImage(named: "image_message_close")
        openView.frame.origin = CGPoint(x: 0, y: messageView.frame.height - 34 * screenScale)
        messageLabel.numberOfLines = 1
        messageLabel.frame = CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 8 * screenScale, width: messageView.frame.width - paddingLeft * 2, height: 20 * screenScale)
        message.isOpen = false
        self.frame.size = CGSize(width: self.frame.width, height: messageView.frame.origin.y + messageView.frame.height)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
