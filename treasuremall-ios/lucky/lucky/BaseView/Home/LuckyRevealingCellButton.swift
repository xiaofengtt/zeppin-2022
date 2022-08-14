//
//  LuckyRevealingCellButton.swift
//  lucky
//  开奖cell
//  Created by Farmer Zhu on 2020/9/2.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRevealingCellButton: UIButton{

    var data: LuckyLuckygameGoodsIssueModel!

    var selectedLine: CALayer!
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel) {
        super.init(frame: frame)
        self.data = data
        self.backgroundColor = UIColor.white
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 4 * screenScale
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: frame.width, height: frame.width))
        imageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage:  UIImage(named: "image_load_default"),  options: SDWebImageOptions.retryFailed,completed: nil)
        self.addSubview(imageView)
        
        if(globalFlagUser){
            //正式
            //金额tag
            var flagPriceTag = false
            if(data.betPerShare == 10 || data.betPerShare == 100){
                flagPriceTag = true
                let tagLabelBgImageView = UIImageView(frame: CGRect(x: imageView.frame.width - 38 * screenScale, y: 0, width: 38 * screenScale, height: 20 * screenScale))
                if(data.betPerShare == 100){
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_100")
                }else{
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_10")
                }
                imageView.addSubview(tagLabelBgImageView)
            }
            
            //新品tag
            if(data.tabs.contains("newgoods")){
                var imagePaddingRight: CGFloat = 33 * screenScale
                if(flagPriceTag){
                    imagePaddingRight = 76 * screenScale
                }
                let newImageView = UIImageView(frame: CGRect(x: imageView.frame.width - imagePaddingRight, y: 0, width: 33 * screenScale, height: 20 * screenScale))
                newImageView.image = UIImage(named: "image_goods_tag_new")
                imageView.addSubview(newImageView)
            }
        }
        
        if(data.status == "lottery" && globalFlagUser){
            //开奖中&正式
            let endLabel = UILabel()
            endLabel.text = NSLocalizedString("End in", comment: "")
            endLabel.textColor = UIColor.fontDarkGray()
            endLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            endLabel.sizeToFit()
            endLabel.frame = CGRect(x: (frame.width - (endLabel.frame.width + 16 * screenScale))/2 + 16 * screenScale, y: imageView.frame.origin.y + imageView.frame.height + 6 * screenScale, width: endLabel.frame.width, height: 20 * screenScale)
            self.addSubview(endLabel)
            
            let endImageView = UIImageView(frame: CGRect(x: (frame.width - (endLabel.frame.width + 16 * screenScale))/2, y: endLabel.frame.origin.y + 4 * screenScale , width: 10 * screenScale, height: 12 * screenScale))
            endImageView.image = UIImage(named: "image_clock")
            self.addSubview(endImageView)
            
            //倒计时
            let countDownView = LuckyCountDownView(frame: CGRect(x: 15 * screenScale, y: endLabel.frame.origin.y + endLabel.frame.height + 2 * screenScale, width: frame.width - 30 * screenScale, height: 20 * screenScale), finishedtime: data.finishedtime)
            self.addSubview(countDownView)
        }else{
            //非开奖状态
            if(globalFlagUser){
                //正式
                let nameLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 10 * screenScale, width: frame.width, height: 20 * screenScale))
                nameLabel.text = NSLocalizedString("Winner", comment: "")
                nameLabel.textColor = UIColor.fontRed()
                nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
                nameLabel.textAlignment = NSTextAlignment.center
                self.addSubview(nameLabel)
                
                let userLabel = UILabel(frame: CGRect(x: 0, y: nameLabel.frame.origin.y + nameLabel.frame.height, width: frame.width, height: 20 * screenScale))
                userLabel.text = data.nickname
                userLabel.textColor = UIColor.fontBlack()
                userLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
                userLabel.textAlignment = NSTextAlignment.center
                self.addSubview(userLabel)
            }else{
                //马甲
                let participantLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 14 * screenScale, width: frame.width, height: 20 * screenScale))
                participantLabel.textColor = UIColor.fontDarkGray()
                participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
                participantLabel.textAlignment = NSTextAlignment.center
                let textString = "\(NSLocalizedString("Participants", comment: "")):\(data.buyCount)"
                let participantText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
                participantText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainYellow()], range: NSRange(location: textString.count - String(data.buyCount).count, length: String(data.buyCount).count))
                participantLabel.attributedText = participantText
                self.addSubview(participantLabel)
            }
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
