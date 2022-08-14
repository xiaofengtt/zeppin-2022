//
//  LuckyActivityPrizeWheelView.swift
//  lucky
//  活动 积分抽奖转盘
//  Created by Farmer Zhu on 2020/10/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

//转盘代理
protocol LuckyActivityPrizeWheelViewDelegate{
    //显示抽奖结果
    func showScorelotteryResult(index: Int)
}

class LuckyActivityPrizeWheelView: UIView{
    
    var delegate: LuckyActivityPrizeWheelViewDelegate?
    var data: LuckyActivityModel!
    var prizeList: [LuckyActivityScorelotteryPrizeModel]!
    
    var prizeWheelView: UIView!
    var startButton: UIButton!
    
    var rollTimer: Timer?
    var endIndex: Int?
    var rollIndex: Int = 0
    var duration: TimeInterval = 1
    
    init(frame: CGRect, data: LuckyActivityModel, prizeList: [LuckyActivityScorelotteryPrizeModel]) {
        self.data = data
        self.prizeList = prizeList
        super.init(frame: frame)
        
        //转盘区
        prizeWheelView = UIView(frame: CGRect(x: (frame.width - frame.height)/2, y: 0, width: frame.height, height: frame.height))
        //背景
        let bgImageView = UIImageView(frame:CGRect(origin: CGPoint.zero, size: prizeWheelView.frame.size))
        bgImageView.image = UIImage(named: "image_activity_scorelottery_prize_wheel_bg")
        prizeWheelView.addSubview(bgImageView)
        
        if(prizeList.count > 0){
            //有奖品信息
            
            //计算占用角度
            let angle: CGFloat = CGFloat(360) / CGFloat(prizeList.count)
            let rotationAngle: CGFloat = CGFloat.pi * angle / 180
            
            //循环在转盘中添加奖品
            for i in 0 ..< prizeList.count{
                let cellView = LuckyActivityPrizeWheelCellView(frame: CGRect(x: (prizeWheelView.frame.width - frame.height)/2, y: 0, width: frame.height, height: frame.height), prize: prizeList[i], isSingular: (i + 1)%2 == 1, angle: angle)
                cellView.transform = CGAffineTransform(rotationAngle: CGFloat(i) * rotationAngle)
                prizeWheelView.addSubview(cellView)
            }
        }
        self.addSubview(prizeWheelView)
        
        //中心指针
        let pointImageView = UIImageView(frame: CGRect(x: (frame.width - 74 * screenScale)/2, y: 100 * screenScale, width: 74 * screenScale, height: 99 * screenScale))
        pointImageView.image = UIImage(named: "image_activity_scorelottery_prize_wheel_pointer")
        let pointLabel = UILabel(frame: CGRect(x: 0, y: 65 * screenScale, width: pointImageView.frame.width, height: 20 * screenScale))
        pointLabel.text = "\(String.valueOf(any: data.config?["scoreLine"])) \(NSLocalizedString("points", comment: ""))"
        pointLabel.textColor = UIColor(red: 179/255, green: 71/255, blue: 35/255, alpha: 1)
        pointLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmallest() * screenScale)
        pointLabel.textAlignment = NSTextAlignment.center
        pointImageView.addSubview(pointLabel)
        self.addSubview(pointImageView)
        
        //开始按钮
        startButton = UIButton(frame: CGRect(x: (frame.width - 74 * screenScale)/2, y: (frame.height - 74 * screenScale)/2, width: 74 * screenScale, height: 74 * screenScale))
        self.addSubview(startButton)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //开始抽奖
    func startRoll(){
        //先转一圈
        self.rollWhole()
        rollTimer = Timer.scheduledTimer(withTimeInterval: duration, repeats: true) { (timer) in
            if(self.rollIndex > 2 && self.endIndex != nil){
                //转两圈以上 且结果已获取到 停止到奖品区域
                self.rollStop()
                self.rollTimer?.invalidate()
                self.rollTimer = nil
            }else{
                //否则转整圈
                self.rollWhole()
            }
            //转圈计数
            self.rollIndex = self.rollIndex + 1
        }
        RunLoop.current.add(rollTimer!, forMode: RunLoop.Mode.common)
    }
    
    //转动整圈
    func rollWhole(){
        //转一圈动画
        UIView.animate(withDuration: duration, delay: 0, options: [.curveLinear], animations: {
            //连续转两个半圈
            self.prizeWheelView.transform = CGAffineTransform(rotationAngle: CGFloat.pi)
            self.prizeWheelView.transform = CGAffineTransform(rotationAngle: 0)
        }, completion: nil)
    }
    
    //停止转动
    func rollStop(){
        //计算停止角度
        let stopIndex = prizeList.count - self.endIndex!
        let tDuration = Double(duration) * Double(stopIndex)/Double(self.prizeList.count)
        UIView.animate(withDuration: TimeInterval(tDuration), delay: 0, options: [.curveLinear], animations: {
            var endAngle: CGFloat = CGFloat(stopIndex)/CGFloat(self.prizeList.count) * 2 * CGFloat.pi
            
            if(stopIndex > self.prizeList.count/2){
                //后半圈 先转半圈 再转角度
                endAngle = endAngle - CGFloat.pi
                self.prizeWheelView.transform = CGAffineTransform(rotationAngle: CGFloat.pi)
                self.prizeWheelView.transform = CGAffineTransform(rotationAngle: CGFloat.pi + endAngle)
            }else{
                //前半圈 转到角度
                self.prizeWheelView.transform = CGAffineTransform(rotationAngle: endAngle)
            }
        }, completion: nil)
        //转盘结束1s后 显示结果
        let timer = Timer.scheduledTimer(withTimeInterval: 1 + tDuration, repeats: false) { (timer) in
            self.showResult()
        }
        RunLoop.current.add(timer, forMode: RunLoop.Mode.common)
    }
    
    //显示结果
    func showResult(){
        self.delegate?.showScorelotteryResult(index: self.endIndex!)
        self.refresh()
    }
    
    //重置
    func refresh(){
        endIndex = nil
        rollIndex = 0
        prizeWheelView.transform = CGAffineTransform(rotationAngle: 0)
    }
}
