//
//  LuckyCommentCellView.swift
//  lucky
//  晒单cell
//  Created by Farmer Zhu on 2020/8/19.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCommentCellView: UIView{
    let paddingLeft: CGFloat = 10 * screenScale
    var imageUrlList: [String] = []
    var personalButton: UIButton!
    var comment: LuckyFrontUserCommentModel!
    
    init(frame: CGRect, comment: LuckyFrontUserCommentModel) {
        super.init(frame: frame)
        self.comment = comment
        
        //用户头像
        let iconView = UIImageView(frame: CGRect(x: paddingLeft, y: 15 * screenScale, width: 40 * screenScale, height: 40 * screenScale))
        iconView.isUserInteractionEnabled = true
        iconView.sd_setImage(with: URL(string: comment.imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        iconView.contentMode = UIView.ContentMode.scaleAspectFill
        iconView.layer.masksToBounds = true
        iconView.layer.cornerRadius = iconView.frame.width/2
        personalButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: iconView.frame.size))
        iconView.addSubview(personalButton)
        self.addSubview(iconView)
        
        //晒单时间
        let timeLabel = UILabel(frame: CGRect(x: self.frame.width - 150 * screenScale, y: iconView.frame.origin.y, width: 140 * screenScale, height: iconView.frame.height))
        timeLabel.text = LuckyUtils.timestampFormat(timestamp: comment.createtime, format: "yyyy-MM-dd HH:mm")
        timeLabel.textColor = UIColor.fontGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        timeLabel.textAlignment = NSTextAlignment.right
        self.addSubview(timeLabel)
        
        //用户名
        let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 10 * screenScale, y: iconView.frame.origin.y, width: timeLabel.frame.origin.x - iconView.frame.width - paddingLeft * 3, height: iconView.frame.height))
        nameLabel.text = comment.nickName
        nameLabel.textColor = UIColor.fontDarkGray()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.left
        self.addSubview(nameLabel)
        
        //晒单信息
        let messageLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: self.frame.width - iconView.frame.width - paddingLeft * 3, height: 0)))
        messageLabel.numberOfLines = 0
        messageLabel.text = comment.detail
        messageLabel.textColor = UIColor.fontBlack()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger())
        messageLabel.sizeToFit()
        messageLabel.frame = CGRect(x: nameLabel.frame.origin.x, y: iconView.frame.origin.y + iconView.frame.height + 10 * screenScale, width: messageLabel.frame.width, height: messageLabel.frame.height)
        self.addSubview(messageLabel)
        
        //晒单图
        let imagesView = UIView(frame: CGRect(x: messageLabel.frame.origin.x, y: messageLabel.frame.origin.y + messageLabel.frame.height + 10 * screenScale, width: self.frame.width - messageLabel.frame.origin.x - paddingLeft, height: 0))
        
        if(comment.imageList.count > 0 ){
            //有晒单图
            if(comment.imageList.count == 1){
                //只有一张 大图
                let imageDic = comment.imageList[0] as NSDictionary
                let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: imagesView.frame.width * 0.7, height: imagesView.frame.width * 0.7))
                imageView.sd_setImage(with: URL(string: String.valueOf(any: imageDic["url"])), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
                imageView.contentMode = UIView.ContentMode.scaleAspectFill
                imageView.layer.masksToBounds = true
                imageView.layer.cornerRadius = 4 * screenScale
                imagesView.addSubview(imageView)
                
                let imageButton = UIButton(frame: imageView.frame)
                imageButton.tag = 0
                imageButton.addTarget(self, action: #selector(prePicture(_:)), for: UIControl.Event.touchUpInside)
                imagesView.addSubview(imageButton)
                
                imageUrlList = [String.valueOf(any: imageDic["url"])]
                imagesView.frame.size = CGSize(width: imagesView.frame.width, height: imageView.frame.height)
            }else{
                //有多张 小图 每行三张
                let space: CGFloat = 10 * screenScale
                let rows: Int = (comment.imageList.count - 1) / 3
                let cellWidth: CGFloat = (imagesView.frame.width - space * 2)/3
                imageUrlList = []
                
                //循环添加晒单图
                for index in 0 ..< comment.imageList.count{
                    let imageDic = comment.imageList[index] as NSDictionary
                    let rowNum = index / 3
                    let columnNum = index % 3
                    
                    let imageView = UIImageView(frame: CGRect(x: (cellWidth + space) * CGFloat(columnNum), y: (cellWidth + space) * CGFloat(rowNum), width: cellWidth, height: cellWidth))
                    imageView.sd_setImage(with: URL(string: String.valueOf(any: imageDic["url"])), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
                    imageView.contentMode = UIView.ContentMode.scaleAspectFill
                    imageView.layer.masksToBounds = true
                    imageView.layer.cornerRadius = 4 * screenScale
                    imagesView.addSubview(imageView)
                    
                    let imageButton = UIButton(frame: imageView.frame)
                    imageButton.tag = index
                    imageButton.addTarget(self, action: #selector(prePicture(_:)), for: UIControl.Event.touchUpInside)
                    imagesView.addSubview(imageButton)
                    imageUrlList.append(String.valueOf(any: imageDic["url"]))
                }
                imagesView.frame.size = CGSize(width: imagesView.frame.width, height: cellWidth * CGFloat(rows + 1) + 10 * screenScale * CGFloat(rows))
            }
        }
        self.addSubview(imagesView)
        
        //中奖内容区域
        let contentView = UIView(frame: CGRect(x: messageLabel.frame.origin.x, y: imagesView.frame.origin.y + imagesView.frame.height + 10 * screenScale, width: imagesView.frame.width, height: 96 * screenScale))
        contentView.backgroundColor = UIColor.mainLightYellow()
        contentView.layer.masksToBounds = true
        contentView.layer.cornerRadius = 4 * screenScale
        //标题
        let contentTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 8 * screenScale, width: contentView.frame.width - paddingLeft * 2, height: 20 * screenScale))
        contentTitleLabel.text = "\(NSLocalizedString("Issue", comment: "")):\(comment.issueNum) | \(comment.title)"
        contentTitleLabel.textColor = UIColor.mainYellow()
        contentTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        contentView.addSubview(contentTitleLabel)
        //投注份数
        let contentPartLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentTitleLabel.frame.origin.y + contentTitleLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        contentPartLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(comment.buyCount)"
        contentPartLabel.textColor = UIColor.darkGray
        contentPartLabel.font = contentTitleLabel.font
        contentView.addSubview(contentPartLabel)
        //幸运号
        let contentNumberLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentPartLabel.frame.origin.y + contentPartLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        if(globalFlagUser){
            contentNumberLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):\(comment.luckynum)"
        }else{
            contentNumberLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):\(comment.luckynum)"
        }
        contentNumberLabel.textColor = contentPartLabel.textColor
        contentNumberLabel.font = contentPartLabel.font
        contentView.addSubview(contentNumberLabel)
        //开奖时间
        let contentTimeLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentNumberLabel.frame.origin.y + contentNumberLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        contentTimeLabel.text = "\(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: comment.winningTime, format: "yyyy-MM-dd HH:mm:ss"))"
        contentTimeLabel.textColor = contentPartLabel.textColor
        contentTimeLabel.font = contentPartLabel.font
        contentView.addSubview(contentTimeLabel)
        self.addSubview(contentView)
        self.frame.size = CGSize(width: frame.width, height: contentView.frame.origin.y + contentView.frame.height + 10 * screenScale)
        
        //底部分割
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: self.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //点击查看大图
    @objc func prePicture(_ sender: UIButton){
        let imageView = FJPreImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        
        imageView.showPreView(UIApplication.shared.keyWindow!, urls: imageUrlList, index: sender.tag)
    }
}
