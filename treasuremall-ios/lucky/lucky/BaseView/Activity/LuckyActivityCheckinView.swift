//
//  LuckyActivityCheckinView.swift
//  lucky
//  活动 签到框
//  Created by Farmer Zhu on 2020/10/22.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityCheckinView: UIView{
    
    var data: LuckyActivityModel!
    var dataList: [LuckyActivityCheckinPrizeModel]!
    var days: Int!
    var cellList: [LuckyActivityCheckinCellView] = []
    
    var messageLabel: UILabel!
    var button: UIButton!
    
    init(frame: CGRect, data: LuckyActivityModel, dataList: [LuckyActivityCheckinPrizeModel], days: Int) {
        self.data = data
        self.dataList = dataList
        self.days = days
        super.init(frame: frame)
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 4 * screenScale
        self.backgroundColor = UIColor.white
        
        //头部消息
        let messageImageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: 18 * screenScale, width: 16 * screenScale, height: 16 * screenScale))
        messageImageView.image = UIImage(named: "image_activity_rewards_message")
        self.addSubview(messageImageView)
        messageLabel = UILabel(frame: CGRect(x: messageImageView.frame.origin.x + messageImageView.frame.width + 8 * screenScale, y: messageImageView.frame.origin.y, width: frame.width - (messageImageView.frame.origin.x + messageImageView.frame.width + 8 * screenScale), height: messageImageView.frame.height))
        messageLabel.text = "\(NSLocalizedString("Continuous sign in", comment: "")) \(days) \(NSLocalizedString("days", comment: ""))"
        messageLabel.textColor = UIColor.fontBlack()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.addSubview(messageLabel)
        
        //主区域 最多7个 首行4 二行3
        let mainView = UIView(frame: CGRect(x: messageImageView.frame.origin.x, y: messageImageView.frame.origin.y + messageImageView.frame.height + 22 * screenScale, width: frame.width - messageImageView.frame.origin.x * 2, height: 165 * screenScale))
        let cellPadding: CGFloat = 16 * screenScale
        let cellHeight: CGFloat = 75 * screenScale
        let cellWidth: CGFloat = (mainView.frame.width - cellPadding * 3)/4
        
        //根据数据量 绘制每天Cell
        if(dataList.count > 0){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: 0, y: 0, width: cellWidth, height: cellHeight), data: dataList[0])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 1){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: cellWidth + cellPadding, y: 0, width: cellWidth, height: cellHeight), data: dataList[1])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 2){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: (cellWidth + cellPadding) * 2, y: 0, width: cellWidth, height: cellHeight), data: dataList[2])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 3){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: (cellWidth + cellPadding) * 3, y: 0, width: cellWidth, height: cellHeight), data: dataList[3])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 4){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: (mainView.frame.width - cellWidth)/2 - (cellWidth + cellPadding), y: cellHeight + 15 * screenScale, width: cellWidth, height: cellHeight), data: dataList[4])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 5){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: (mainView.frame.width - cellWidth)/2, y: cellHeight + 15 * screenScale, width: cellWidth, height: cellHeight), data: dataList[5])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        
        if(dataList.count > 6){
            let cellView = LuckyActivityCheckinCellView(frame: CGRect(x: (mainView.frame.width - cellWidth)/2 + (cellWidth + cellPadding), y: cellHeight + 15 * screenScale, width: cellWidth, height: cellHeight), data: dataList[6])
            cellList.append(cellView)
            mainView.addSubview(cellView)
        }
        self.addSubview(mainView)
        
        //签到按钮
        button = UIButton(frame: CGRect(x: (frame.width - 242 * screenScale)/2, y: mainView.frame.origin.y + mainView.frame.height + 30 * screenScale, width: 242 * screenScale, height: 45 * screenScale))
        button.setBackgroundImage(UIImage(named: "image_activity_rewards_checkin_button"), for: UIControl.State.normal)
        button.setBackgroundImage(UIImage(named: "image_activity_rewards_checkin_button_disable"), for: UIControl.State.disabled)
        button.setTitle(NSLocalizedString("Daily Sign-in", comment: ""), for: UIControl.State.normal)
        button.setTitle(NSLocalizedString("Come again tomorrow", comment: ""), for: UIControl.State.disabled)
        button.setTitleColor(UIColor(red: 211/255, green: 53/255, blue: 0/255, alpha: 1), for: UIControl.State.normal)
        button.setTitleColor(UIColor(red: 147/255, green: 147/255, blue: 147/255, alpha: 1), for: UIControl.State.disabled)
        button.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        button.isEnabled = data.isPartake
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //设置签到天数
    func setFinished(dayNum: Int){
        if(cellList.count > dayNum){
            self.days = dayNum
            self.button.isEnabled = false
            messageLabel.text = "\(NSLocalizedString("Continuous sign in", comment: "")) \(days!) \(NSLocalizedString("days", comment: ""))"
            cellList[dayNum].setFinished()
        }
    }
}
