//
//  LuckyCategoriesGoodsCellView.swift
//  lucky
//  分类商品数据Cell
//  Created by Farmer Zhu on 2020/9/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCategoriesGoodsCellView: UIView{
    var data: LuckyLuckygameGoodsIssueModel!
    
    var button: UIButton!
    
    let padding: CGFloat = 10 * screenScale
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel, isSearch: Bool) {
        super.init(frame: frame)
        self.data = data
        
        let paddingTop = isSearch ? 16 * screenScale : padding
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: padding, y: paddingTop, width: frame.height - padding * 2, height: frame.height - paddingTop * 2))
        imageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        
        if(globalFlagUser){
            //正式
            var flagPriceTag = false
            
            //金额Tag 10 100
            if(data.betPerShare == 10 || data.betPerShare == 100){
                flagPriceTag = true
                let tagLabelBgImageView = UIImageView(frame: CGRect(x: imageView.frame.width - 30 * screenScale, y: 0, width: 30 * screenScale, height: 15 * screenScale))
                if(data.betPerShare == 100){
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_100")
                }else{
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_10")
                }
                imageView.addSubview(tagLabelBgImageView)
            }
            
            //新品tag
            if(data.tabs.contains("newgoods")){
                var imagePaddingRight: CGFloat = 25 * screenScale
                if(flagPriceTag){
                    imagePaddingRight = 58 * screenScale
                }
                let newImageView = UIImageView(frame: CGRect(x: imageView.frame.width - imagePaddingRight, y: 0, width: 25 * screenScale, height: 15 * screenScale))
                newImageView.image = UIImage(named: "image_goods_tag_new")
                imageView.addSubview(newImageView)
            }
        }
        
        //商品名
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + padding, y: 6 * screenScale, width: frame.width - padding - (imageView.frame.origin.x + imageView.frame.width + padding), height: 50 * screenScale))
        nameLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        nameLabel.attributedText = NSAttributedString(string: data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        nameLabel.alignmentTop()
        self.addSubview(nameLabel)
        
        //参加按钮
        button = UIButton(frame: CGRect(x: frame.width - padding - 60 * screenScale, y: imageView.frame.origin.y + imageView.frame.height - 24 * screenScale, width: 60 * screenScale, height: 24 * screenScale))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 4 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle(NSLocalizedString("Join", comment: ""), for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        self.addSubview(button)
        
        //进度
        let progressTitleLabel = UILabel()
        progressTitleLabel.text = "\(NSLocalizedString("Progress", comment: "")):"
        progressTitleLabel.textColor = UIColor.fontDarkGray()
        progressTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        progressTitleLabel.sizeToFit()
        progressTitleLabel.frame = CGRect(x: nameLabel.frame.origin.x, y: button.frame.origin.y, width: progressTitleLabel.frame.width, height: 16 * screenScale)
        self.addSubview(progressTitleLabel)
        
        let progressRateLabel = UILabel(frame: CGRect(x: progressTitleLabel.frame.origin.x + progressTitleLabel.frame.width, y: progressTitleLabel.frame.origin.y, width: button.frame.origin.x - padding - (progressTitleLabel.frame.origin.x + progressTitleLabel.frame.width), height: progressTitleLabel.frame.height))
        progressRateLabel.text = "\(data.betShares * 100/data.shares)%"
        progressRateLabel.textColor = UIColor.mainRed()
        progressRateLabel.font = progressTitleLabel.font
        self.addSubview(progressRateLabel)
        
        if(isSearch){
            //搜索结果中 显示具体进度数字
            let progressRightLabel = UILabel(frame: progressRateLabel.frame)
            progressRightLabel.textColor = UIColor.fontGray()
            progressRightLabel.font = progressTitleLabel.font
            progressRightLabel.textAlignment = NSTextAlignment.right
            let progressRightText: NSMutableAttributedString = NSMutableAttributedString(string: "\(data.betShares)/\(data.shares)")
            progressRightText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: String(data.betShares).count + 1))
            progressRightLabel.attributedText = progressRightText
            self.addSubview(progressRightLabel)
        }
        
        //进度条
        let progressBar = LuckyProgressBar(frame: CGRect(x: progressTitleLabel.frame.origin.x, y: button.frame.origin.y + button.frame.height - 3 * screenScale, width: button.frame.origin.x - padding - progressTitleLabel.frame.origin.x, height: 3 * screenScale))
        progressBar.rate = Double(data.betShares)/Double(data.shares)
        self.addSubview(progressBar)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: padding * 2, y: frame.height - 1, width: frame.width - padding * 2, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
