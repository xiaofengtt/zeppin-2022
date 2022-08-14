//
//  LuckyTranscationCellView.swift
//  lucky
//  交易信息Cell
//  Created by Farmer Zhu on 2020/9/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTranscationCellView: UIView{
    
    var data: LuckyFrontUserHistoryModel!
    
    init(frame: CGRect, data: LuckyFrontUserHistoryModel) {
        super.init(frame: frame)
        self.data = data
        
        //圆点
        let typePoint = UIView(frame: CGRect(x: 10 * screenScale, y: 20 * screenScale, width: 10 * screenScale, height: 10 * screenScale))
        typePoint.layer.masksToBounds = true
        typePoint.layer.cornerRadius = typePoint.frame.height/2
        if(data.type == "user_add"){
            //加钱绿
            typePoint.backgroundColor = UIColor.mainGreen()
        }else{
            //减钱红
            typePoint.backgroundColor = UIColor.mainRed()
        }
        self.addSubview(typePoint)
        
        //金币图片
        let coinImageView = UIImageView(frame: CGRect(x: frame.width - 26 * screenScale, y: 17 * screenScale, width: 16 * screenScale, height: 16 * screenScale))
        if(globalFlagUser){
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        self.addSubview(coinImageView)
        
        //金额
        let amountLabel = UILabel(frame: CGRect(x: coinImageView.frame.origin.x - 104 * screenScale, y: 15 * screenScale, width: 100 * screenScale, height: 20 * screenScale))
        amountLabel.text = "\(data.type == "user_add" ? "+" : "-")\(LuckyUtils.coinFormat(amount: data.dAmount))"
        if(data.type == "user_add"){
            amountLabel.textColor = UIColor.mainGreen()
        }else{
            amountLabel.textColor = UIColor.fontBlack()
        }
        amountLabel.font = UIFont.boldFont(size: UIFont.fontSizeBiggest() * screenScale)
        amountLabel.textAlignment = NSTextAlignment.right
        self.addSubview(amountLabel)
        
        //类型
        let typeLabel = UILabel(frame: CGRect(x: typePoint.frame.origin.x + typePoint.frame.width + 4 * screenScale, y: amountLabel.frame.origin.y, width: amountLabel.frame.origin.x - (typePoint.frame.origin.x + typePoint.frame.width + 4 * screenScale), height: amountLabel.frame.height))
        typeLabel.text = LuckyUtils.getHistoryType(type: data.orderType)
        typeLabel.textColor = UIColor.fontBlack()
        typeLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(typeLabel)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: typeLabel.frame.origin.x, y: typeLabel.frame.origin.y + typeLabel.frame.height + 4 * screenScale, width: typeLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = data.remark
        contentLabel.textColor = UIColor.fontGray()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(contentLabel)
        
        //时间
        let timeLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: contentLabel.frame.origin.y + contentLabel.frame.height + 1 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
        timeLabel.text = LuckyUtils.timestampFormat(timestamp: data.createtime, format: "MM-dd HH:mm")
        timeLabel.textColor = contentLabel.textColor
        timeLabel.font = contentLabel.font
        self.addSubview(timeLabel)
        
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
