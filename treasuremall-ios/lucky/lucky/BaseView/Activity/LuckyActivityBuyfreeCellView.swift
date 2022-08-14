//
//  LuckyActivityBuyfreeCellView.swift
//  lucky
//  活动页零元购Cell
//  Created by Farmer Zhu on 2020/10/22.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityBuyfreeCellView: UIView{
    
    var data: LuckyActivityBuyfreeModel!
    
    var progressBar: LuckyProgressBar!
    var progressLabel: UILabel!
    var buttonView: UIView!
    var button: UIButton!
    
    init(frame: CGRect, data: LuckyActivityBuyfreeModel, isEnded: Bool) {
        self.data = data
        super.init(frame: frame)
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: (frame.height - 120 * screenScale)/2, width: 120 * screenScale, height: 120 * screenScale))
        imageView.sd_setImage(with: URL(string: data.goodsCoverUrl), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        self.addSubview(imageView)
        
        //商品名
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 6 * screenScale, y: imageView.frame.origin.y, width: frame.width - 16 * screenScale - (imageView.frame.origin.x + imageView.frame.width + 6 * screenScale), height: 20 * screenScale))
        nameLabel.text = data.goodsTitle
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(nameLabel)
        
        //进度条
        progressBar = LuckyProgressBar(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height + 20 * screenScale, width: nameLabel.frame.width, height: 6 * screenScale))
        progressBar.backgroundColor = UIColor(red: 255/255, green: 241/255, blue: 223/255, alpha: 1)
        progressBar.rate = Double(data.betShares)/Double(data.shares)
        self.addSubview(progressBar)
        
        progressLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: progressBar.frame.origin.y + progressBar.frame.height + 8 * screenScale, width: nameLabel.frame.width, height: 20 * screenScale))
        progressLabel.text = "\(data.betShares)/\(data.shares)"
        progressLabel.textColor = UIColor.activityMainColor()
        progressLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        progressLabel.textAlignment = NSTextAlignment.right
        self.addSubview(progressLabel)
        
        //0元
        let freeAmountLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: imageView.frame.origin.y + imageView.frame.height - 26 * screenScale, width: 0, height: 26 * screenScale))
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        if let currencySymbol = globalCurrencyRate?.currencySymbol[globalCurrencyCode]{
            symbol = String.valueOf(any: currencySymbol)
        }
        freeAmountLabel.text = "\(symbol) 0"
        freeAmountLabel.textColor = UIColor.activityMainColor()
        freeAmountLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        freeAmountLabel.sizeToFit()
        freeAmountLabel.frame.size = CGSize(width: freeAmountLabel.frame.width, height: 26 * screenScale)
        self.addSubview(freeAmountLabel)
        
        //原价
        let realAmountLabel = UILabel(frame: CGRect(x: freeAmountLabel.frame.origin.x + freeAmountLabel.frame.width + 6 * screenScale, y: freeAmountLabel.frame.origin.y, width: 0, height: freeAmountLabel.frame.height))
        realAmountLabel.text = "\(LuckyUtils.localCurrencyFormat(amount: data.goodsPrice))"
        realAmountLabel.textColor = UIColor(red: 147/255, green: 147/255, blue: 147/255, alpha: 1)
        realAmountLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller())
        realAmountLabel.sizeToFit()
        realAmountLabel.frame.size = CGSize(width: realAmountLabel.frame.width, height: freeAmountLabel.frame.height)
        let deleteLine = CALayer()
        deleteLine.frame = CGRect(x: 0, y: realAmountLabel.frame.height/2 + 1 * screenScale, width: realAmountLabel.frame.width, height: 1)
        deleteLine.backgroundColor = realAmountLabel.textColor.cgColor
        realAmountLabel.layer.addSublayer(deleteLine)
        self.addSubview(realAmountLabel)
        
        //底部
        buttonView = UIView(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width - 74 * screenScale, y: imageView.frame.origin.y + imageView.frame.height - 32 * screenScale, width: 74 * screenScale, height: 32 * screenScale))
        buttonView.layer.shadowOffset = CGSize(width: 0, height: 1)
        buttonView.layer.shadowOpacity = 1
        buttonView.layer.shadowRadius = 5
        //参加按钮
        button = UIButton(frame: CGRect(origin: CGPoint.zero, size: buttonView.frame.size))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 4 * screenScale
        button.setBackgroundImage(UIImage.getImageByColor(UIColor(red: 250/255, green: 66/255, blue: 102/255, alpha: 1)), for: UIControl.State.normal)
        button.setBackgroundImage(UIImage.getImageByColor(UIColor(red: 165/255, green: 165/255, blue: 165/255, alpha: 1)), for: UIControl.State.disabled)
        button.setTitle(NSLocalizedString("Join", comment: ""), for: UIControl.State.normal)
        button.setTitleColor(UIColor.white, for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        setEnable(isEnabled: data.isFirstBuy, betShares: data.betShares)
        buttonView.addSubview(button)
        self.addSubview(buttonView)
        
        //不是最后一个底部分割线
        if(!isEnded){
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: imageView.frame.origin.x, y: frame.height - 1, width: frame.width - imageView.frame.origin.x * 2, height: 1)
            bottomLine.backgroundColor = UIColor(red: 255/255, green: 234/255, blue: 240/255, alpha: 1).cgColor
            self.layer.addSublayer(bottomLine)
        }
    }
    
    //设置状态
    func setEnable(isEnabled: Bool, betShares: Int){
        data.isFirstBuy = isEnabled
        data.betShares = betShares
        
        //进度条
        progressBar.rate = Double(data.betShares)/Double(data.shares)
        progressLabel.text = "\(data.betShares)/\(data.shares)"
        
        //参加按钮
        button.isEnabled = isEnabled
        if(isEnabled){
            buttonView.layer.shadowColor = UIColor(red: 0.93, green: 0.14, blue: 0.37, alpha: 0.5).cgColor
        }else{
            buttonView.layer.shadowColor = UIColor.clear.cgColor
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
