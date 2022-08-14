//
//  LuckyCountDownView.swift
//  lucky
//  倒计时器
//  Created by Farmer Zhu on 2020/9/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCountDownView: UIView{
    
    var finishedtime: Int!
    var timer: Timer?
    
    var minuteLabel: UILabel!
    var secondLabel: UILabel!
    var millisecondLabel: UILabel!
    
    init(frame: CGRect, finishedtime: Int) {
        super.init(frame: frame)
        self.finishedtime = finishedtime
        
        let spaceWidth: CGFloat = (frame.width - frame.height * 3)/2
        
        //分显示
        minuteLabel = UILabel(frame: CGRect(x: 0, y: 0, width: frame.height, height: frame.height))
        minuteLabel.layer.masksToBounds = true
        minuteLabel.layer.cornerRadius = 4 * screenScale
        minuteLabel.backgroundColor = UIColor.mainYellow()
        minuteLabel.textColor = UIColor.fontBlack()
        minuteLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        minuteLabel.textAlignment = NSTextAlignment.center
        self.addSubview(minuteLabel)
        
        let spaceLabel1 = UILabel()
        spaceLabel1.text = ":"
        spaceLabel1.textColor = UIColor.fontBlack()
        spaceLabel1.font = UIFont.mainFont(size: UIFont.fontSizeMiddle())
        spaceLabel1.sizeToFit()
        spaceLabel1.frame = CGRect(x: minuteLabel.frame.origin.x + minuteLabel.frame.width + (spaceWidth - spaceLabel1.frame.width)/2, y: (frame.height - spaceLabel1.frame.height)/2, width: spaceLabel1.frame.width, height: spaceLabel1.frame.height)
        self.addSubview(spaceLabel1)
        
        //秒显示
        secondLabel = UILabel(frame: CGRect(x: minuteLabel.frame.origin.x + minuteLabel.frame.width + spaceWidth, y: minuteLabel.frame.origin.y, width: minuteLabel.frame.width, height: minuteLabel.frame.height))
        secondLabel.layer.masksToBounds = true
        secondLabel.layer.cornerRadius = minuteLabel.layer.cornerRadius
        secondLabel.backgroundColor = minuteLabel.backgroundColor
        secondLabel.textColor = minuteLabel.textColor
        secondLabel.font = minuteLabel.font
        secondLabel.textAlignment = minuteLabel.textAlignment
        self.addSubview(secondLabel)
        
        let spaceLabel2 = UILabel(frame: CGRect(x: secondLabel.frame.origin.x + secondLabel.frame.width + (spaceWidth - spaceLabel1.frame.width)/2, y: spaceLabel1.frame.origin.y, width: spaceLabel1.frame.width, height: spaceLabel1.frame.height))
        spaceLabel2.text = spaceLabel1.text
        spaceLabel2.textColor = spaceLabel1.textColor
        spaceLabel2.font = spaceLabel1.font
        self.addSubview(spaceLabel2)
        
        //毫秒显示
        millisecondLabel = UILabel(frame: CGRect(x: frame.width - minuteLabel.frame.width, y: minuteLabel.frame.origin.y, width: minuteLabel.frame.width, height: minuteLabel.frame.height))
        millisecondLabel.layer.masksToBounds = true
        millisecondLabel.layer.cornerRadius = minuteLabel.layer.cornerRadius
        millisecondLabel.backgroundColor = minuteLabel.backgroundColor
        millisecondLabel.textColor = minuteLabel.textColor
        millisecondLabel.font = minuteLabel.font
        millisecondLabel.textAlignment = minuteLabel.textAlignment
        self.addSubview(millisecondLabel)
        
        //初始时间
        setTime()
        
        //开始倒计时 10毫秒变一次
        timer = Timer.scheduledTimer(withTimeInterval: 0.01, repeats: true) { (timer) in
            self.setTime()
        }
        RunLoop.current.add(timer!, forMode: RunLoop.Mode.common)
    }
    
    //设置时间
    func setTime(){
        var timeline = finishedtime - Date().timestamp
        
        if(timeline <= 0){
            timeline = 0
            timer?.invalidate()
        }
        minuteLabel.text = String(format:"%02d", timeline / (60 * 1000))
        secondLabel.text = String(format:"%02d", (timeline % (60 * 1000)) / 1000)
        millisecondLabel.text = String(format:"%02d", (timeline % 1000) / 10)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
