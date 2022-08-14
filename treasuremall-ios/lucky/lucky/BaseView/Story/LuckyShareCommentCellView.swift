//
//  LuckyShareCommentCellView.swift
//  lucky
//  我的晒单Cell
//  Created by Farmer Zhu on 2020/8/26.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyShareCommentCellView: UIView{
    
    var comment: LuckyFrontUserCommentModel!
    
    var goodsButton: UIButton!
    
    let paddingLeft: CGFloat = 10 * screenScale
    var imageUrlList: [String] = []
    
    init(frame: CGRect, comment: LuckyFrontUserCommentModel) {
        super.init(frame: frame)
        self.comment = comment
        self.backgroundColor = UIColor.white
        
        //奖品信息
        let goodsView = UIView(frame: CGRect(x: paddingLeft, y: 16 * screenScale, width: frame.width - paddingLeft * 2, height: 100 * screenScale))
        goodsView.backgroundColor = UIColor.mainLightYellow()
        goodsView.layer.masksToBounds = true
        goodsView.layer.cornerRadius = 4 * screenScale
        
        //奖品图
        let goodsImageView = UIImageView(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: 80 * screenScale, height: 80 * screenScale))
        goodsImageView.sd_setImage(with: URL(string: comment.coverImg), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        goodsView.addSubview(goodsImageView)
        
        //标题
        let contentTitleLabel = UILabel(frame: CGRect(x: goodsImageView.frame.origin.x + goodsImageView.frame.width + paddingLeft, y: 8 * screenScale, width: goodsView.frame.width - (goodsImageView.frame.origin.x + goodsImageView.frame.width + paddingLeft * 2), height: 20 * screenScale))
        contentTitleLabel.text = "\(NSLocalizedString("Issue", comment: "")):\(comment.issueNum) | \(comment.title)"
        contentTitleLabel.textColor = UIColor.mainYellow()
        contentTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        goodsView.addSubview(contentTitleLabel)
        //投注量
        let contentPartLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentTitleLabel.frame.origin.y + contentTitleLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        contentPartLabel.text = "\(NSLocalizedString("Quantity", comment: "")):\(comment.buyCount)"
        contentPartLabel.textColor = UIColor.darkGray
        contentPartLabel.font = contentTitleLabel.font
        goodsView.addSubview(contentPartLabel)
        //幸运号
        let contentNumberLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentPartLabel.frame.origin.y + contentPartLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        if(globalFlagUser){
            //主包
            contentNumberLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):\(comment.luckynum)"
        }else{
            //马甲
            contentNumberLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):\(comment.luckynum)"
        }
        contentNumberLabel.textColor = contentPartLabel.textColor
        contentNumberLabel.font = contentPartLabel.font
        goodsView.addSubview(contentNumberLabel)
        //开奖时间
        let contentTimeLabel = UILabel(frame: CGRect(x: contentTitleLabel.frame.origin.x, y: contentNumberLabel.frame.origin.y + contentNumberLabel.frame.height, width: contentTitleLabel.frame.width, height: contentTitleLabel.frame.height))
        contentTimeLabel.text = "\(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: comment.winningTime, format: "yyyy-MM-dd HH:mm:ss"))"
        contentTimeLabel.textColor = contentPartLabel.textColor
        contentTimeLabel.font = contentPartLabel.font
        goodsView.addSubview(contentTimeLabel)
        
        goodsButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: goodsView.frame.size))
        goodsView.addSubview(goodsButton)
        self.addSubview(goodsView)
        
        //晒单信息
        let messageLabel = UILabel(frame: CGRect(x: paddingLeft, y: goodsView.frame.origin.y + goodsView.frame.height + 16 * screenScale, width: frame.width - 2 * paddingLeft, height: 0))
        messageLabel.numberOfLines = 0
        messageLabel.text = comment.detail
        messageLabel.textColor = UIColor.fontBlack()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger())
        messageLabel.sizeToFit()
        self.addSubview(messageLabel)
        
        //晒单图
        let imagesView = UIView(frame: CGRect(x: paddingLeft, y: messageLabel.frame.origin.y + messageLabel.frame.height + 6 * screenScale, width: frame.width - paddingLeft * 2, height: 0))
        if(comment.imageList.count > 0 ){
            //有晒单图
            if(comment.imageList.count == 1){
                //只有一张 大图
                let imageDic = comment.imageList[0] as NSDictionary
                let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: imagesView.frame.width * 0.7, height: imagesView.frame.width * 0.7))
                imageView.sd_setImage(with: URL(string: String.valueOf(any: imageDic["url"])), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed)
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
                //有多张 小图
                let space: CGFloat = 10 * screenScale
                let rows: Int = (comment.imageList.count - 1) / 3
                let cellWidth: CGFloat = (imagesView.frame.width - space * 2)/3
                imageUrlList = []

                //循环添加图片 每行三个
                for index in 0 ..< comment.imageList.count{
                    let imageDic = comment.imageList[index] as NSDictionary
                    let rowNum = index / 3
                    let columnNum = index % 3

                    let imageView = UIImageView(frame: CGRect(x: (cellWidth + space) * CGFloat(columnNum), y: (cellWidth + space) * CGFloat(rowNum), width: cellWidth, height: cellWidth))
                    imageView.sd_setImage(with: URL(string: String.valueOf(any: imageDic["url"])), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed)
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
        
        //晒单时间
        let timeLabel = UILabel(frame: CGRect(x: paddingLeft, y: imagesView.frame.origin.y + imagesView.frame.height + 10 * screenScale, width: frame.width - paddingLeft * 2, height: 20 * screenScale))
        timeLabel.text = LuckyUtils.timestampFormat(timestamp: comment.createtime, format: "yyyy-MM-dd HH:mm")
        timeLabel.textColor = UIColor.fontGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        self.addSubview(timeLabel)
        self.frame.size = CGSize(width: frame.width, height: timeLabel.frame.origin.y + timeLabel.frame.height + 10 * screenScale)
        
        //底部分割
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: self.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //点图片放大显示
    @objc func prePicture(_ sender: UIButton){
        let imageView = FJPreImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        
        imageView.showPreView(UIApplication.shared.keyWindow!, urls: imageUrlList, index: sender.tag)
    }
}
