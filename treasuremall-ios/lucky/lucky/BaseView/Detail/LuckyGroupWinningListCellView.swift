//
//  LuckyGroupWinningListCellView.swift
//  lucky
//  PK获胜记录Cell
//  Created by Farmer Zhu on 2020/9/29.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupWinningListCellView: UIView{

    var data: LuckyGroupWinningInfoModel!
    
    init(frame: CGRect, data: LuckyGroupWinningInfoModel, isFirst: Bool) {
        super.init(frame: frame)
        self.data = data
        
        //轴点
        let point = UIView(frame: CGRect(x: 12 * screenScale, y: 6 * screenScale, width: 8 * screenScale, height: 8 * screenScale))
        point.layer.masksToBounds = true
        point.layer.cornerRadius = point.frame.height/2
        point.backgroundColor = UIColor.mainYellow()
        self.addSubview(point)
        
        //轴线
        let leftLine = CALayer()
        if(isFirst){
            //第一个没上段
            leftLine.frame = CGRect(x: point.center.x, y: point.center.y, width: 1, height: frame.height - point.center.y)
        }else{
            leftLine.frame = CGRect(x: point.center.x, y: 0, width: 1, height: frame.height)
        }
        leftLine.backgroundColor = UIColor.mainYellow().cgColor
        self.layer.addSublayer(leftLine)
        
        //标题
        let label = UILabel(frame: CGRect(x: point.frame.origin.x + point.frame.width + 10 * screenScale, y: 0, width: frame.width - point.frame.origin.x + point.frame.width + 10 * screenScale, height: 20 * screenScale))
        label.text = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | \(NSLocalizedString("Results for", comment: "")):\(LuckyUtils.timestampFormat(timestamp: data.createtime, format: "yyyy-MM-dd HH:mm"))"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(label)
        
        //胜负图
        let winningImageView = UIImageView(frame: CGRect(x: (frame.width - 266 * screenScale)/2, y: label.frame.origin.y + label.frame.height, width: 266 * screenScale, height: 62 * screenScale))
        if(data.luckyGroup == "lucky"){
            //红队胜
            winningImageView.image = UIImage(named: "image_pk_winning_red")
        }else{
            //蓝队胜
            winningImageView.image = UIImage(named: "image_pk_winning_blue")
        }
        self.addSubview(winningImageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
