//
//  LuckyHomeGoodsCellView.swift
//  lucky
//  首页商品cell
//  Created by Farmer Zhu on 2020/9/1.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyHomeGoodsCellButton: UIButton{
    
    var button: UIButton!
    var data: LuckyLuckygameGoodsIssueModel!
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel) {
        super.init(frame: frame)
        self.data = data
        self.backgroundColor = UIColor.white
        self.layer.borderColor = UIColor.backgroundGray().cgColor
        self.layer.borderWidth = 1
        
        //商品图
        let goodsImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: frame.width, height: frame.width))
        goodsImageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(goodsImageView)
        
        if(globalFlagUser){
            //正式
            var flagPriceTag = false
            
            //金额tag
            if(data.betPerShare == 10 || data.betPerShare == 100){
                flagPriceTag = true
                let tagLabelBgImageView = UIImageView(frame: CGRect(x: goodsImageView.frame.width - 38 * screenScale, y: 0, width: 38 * screenScale, height: 20 * screenScale))
                if(data.betPerShare == 100){
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_100")
                }else{
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_10")
                }
                goodsImageView.addSubview(tagLabelBgImageView)
            }
            
            //新品tag
            if(data.tabs.contains("newgoods")){
                var imagePaddingRight: CGFloat = 33 * screenScale
                if(flagPriceTag){
                    imagePaddingRight = 76 * screenScale
                }
                let newImageView = UIImageView(frame: CGRect(x: goodsImageView.frame.width - imagePaddingRight, y: 0, width: 33 * screenScale, height: 20 * screenScale))
                newImageView.image = UIImage(named: "image_goods_tag_new")
                goodsImageView.addSubview(newImageView)
            }
        }
        
        //商品名
        let nameLabel = UILabel(frame: CGRect(x: paddingLeft, y: goodsImageView.frame.origin.y + goodsImageView.frame.height + 10 * screenScale, width: frame.width - paddingLeft * 2, height: 40 * screenScale))
        nameLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        nameLabel.attributedText = NSAttributedString(string: data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        nameLabel.alignmentTop()
        self.addSubview(nameLabel)
        
        //进度
        let progressNameLabel = UILabel(frame: CGRect(x: paddingLeft, y: nameLabel.frame.origin.y + 40 * screenScale + 4 * screenScale, width: frame.width, height: 20 * screenScale))
        progressNameLabel.text = "\(NSLocalizedString("Progress", comment: "")):"
        progressNameLabel.textColor = UIColor.fontGray()
        progressNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        progressNameLabel.sizeToFit()
        progressNameLabel.frame.size = CGSize(width: progressNameLabel.frame.width, height: 20 * screenScale)
        self.addSubview(progressNameLabel)
        
        let progressRateLabel = UILabel(frame: CGRect(x: progressNameLabel.frame.origin.x + progressNameLabel.frame.width + 2 * screenScale, y: progressNameLabel.frame.origin.y, width: 100 * screenScale, height: progressNameLabel.frame.height))
        progressRateLabel.text = "\(data.betShares * 100/data.shares)%"
        progressRateLabel.textColor = UIColor.mainRed()
        progressRateLabel.font = progressNameLabel.font
        self.addSubview(progressRateLabel)
        
        let progressRightLabel = UILabel(frame: CGRect(x: frame.width/2, y: progressNameLabel.frame.origin.y, width: frame.width/2 - paddingLeft, height: progressNameLabel.frame.height))
        progressRightLabel.textColor = UIColor.fontGray()
        progressRightLabel.font = progressNameLabel.font
        progressRightLabel.textAlignment = NSTextAlignment.right
        let progressRightText: NSMutableAttributedString = NSMutableAttributedString(string: "\(data.betShares)/\(data.shares)")
        progressRightText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: String(data.betShares).count + 1))
        progressRightLabel.attributedText = progressRightText
        self.addSubview(progressRightLabel)
        
        let progressBar = LuckyProgressBar(frame: CGRect(x: paddingLeft, y: progressNameLabel.frame.origin.y + progressNameLabel.frame.height + 4 * screenScale, width: frame.width - paddingLeft * 2, height: 4 * screenScale))
        progressBar.rate = Double(data.betShares)/Double(data.shares)
        self.addSubview(progressBar)
        
        //买入按钮
        button = UIButton(frame: CGRect(x: paddingLeft, y: frame.height - 42 * screenScale, width: frame.width - paddingLeft * 2, height: 32 * screenScale))
        button.layer.shadowColor = UIColor(red: 1, green: 0.53, blue: 0.02, alpha: 0.3).cgColor
        button.layer.shadowOffset = CGSize(width: 0, height: 2)
        button.layer.shadowOpacity = 1
        button.layer.shadowRadius = 4
        button.layer.cornerRadius = 6 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle(NSLocalizedString("Join", comment: ""), for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
