//
//  LuckyActivityOrderCheckinCellView.swift
//  lucky
//  活动 签到订单Cell
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityOrderCheckinCellView: UIView{
    
    var data: LuckyFrontUserCheckinHistoryModel!
    
    init(frame: CGRect, data: LuckyFrontUserCheckinHistoryModel) {
        self.data = data
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        
        //头部标题
        let titleLabel = UILabel(frame: CGRect(x: 16 * screenScale, y: 16 * screenScale, width: frame.width - 32 * screenScale, height: 20 * screenScale))
        titleLabel.text = data.prizeTitle
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        self.addSubview(titleLabel)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 6 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height))
        contentLabel.text = LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:mm")
        contentLabel.textColor = UIColor.fontGray()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(contentLabel)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
