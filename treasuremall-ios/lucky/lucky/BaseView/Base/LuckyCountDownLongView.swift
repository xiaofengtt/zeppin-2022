//
//  LuckyCountDownLongView.swift
//  lucky
//  长条倒计时器
//  Created by Farmer Zhu on 2020/9/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCountDownLongView: UIView{
    
    var finishedtime: Int!
    var timer: Timer?
    
    var minuteLabel1: UILabel!
    var minuteLabel2: UILabel!
    var secondLabel1: UILabel!
    var secondLabel2: UILabel!
    var millisecondLabel1: UILabel!
    var millisecondLabel2: UILabel!
    
    init(frame: CGRect, finishedtime: Int) {
        super.init(frame: frame)
        self.finishedtime = finishedtime
        
        let spaceWidth: CGFloat = (frame.width - 44 * screenScale * 3)/2
        
        //分显示十位
        minuteLabel1 = UILabel(frame: CGRect(x: 0, y: 0, width: 20 * screenScale, height: frame.height))
        minuteLabel1.layer.masksToBounds = true
        minuteLabel1.layer.cornerRadius = 4 * screenScale
        minuteLabel1.backgroundColor = UIColor.mainYellow()
        minuteLabel1.textColor = UIColor.fontBlack()
        minuteLabel1.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        minuteLabel1.textAlignment = NSTextAlignment.center
        self.addSubview(minuteLabel1)
        
        //分显示个位
        minuteLabel2 = UILabel(frame: CGRect(x: minuteLabel1.frame.origin.x + minuteLabel1.frame.width + 4 * screenScale, y: 0, width: minuteLabel1.frame.width, height: frame.height))
        minuteLabel2.layer.masksToBounds = true
        minuteLabel2.layer.cornerRadius = minuteLabel1.layer.cornerRadius
        minuteLabel2.backgroundColor = minuteLabel1.backgroundColor
        minuteLabel2.textColor = minuteLabel1.textColor
        minuteLabel2.font = minuteLabel1.font
        minuteLabel2.textAlignment = minuteLabel1.textAlignment
        self.addSubview(minuteLabel2)
        
        let spaceLabel1 = UILabel()
        spaceLabel1.text = ":"
        spaceLabel1.textColor = UIColor.fontBlack()
        spaceLabel1.font = UIFont.mainFont(size: UIFont.fontSizeMiddle())
        spaceLabel1.sizeToFit()
        spaceLabel1.frame = CGRect(x: minuteLabel2.frame.origin.x + minuteLabel2.frame.width + (spaceWidth - spaceLabel1.frame.width)/2, y: (frame.height - spaceLabel1.frame.height)/2, width: spaceLabel1.frame.width, height: spaceLabel1.frame.height)
        self.addSubview(spaceLabel1)
        
        //秒显示十位
        secondLabel1 = UILabel(frame: CGRect(x: minuteLabel2.frame.origin.x + minuteLabel2.frame.width + spaceWidth, y: minuteLabel1.frame.origin.y, width: minuteLabel1.frame.width, height: minuteLabel1.frame.height))
        secondLabel1.layer.masksToBounds = true
        secondLabel1.layer.cornerRadius = minuteLabel1.layer.cornerRadius
        secondLabel1.backgroundColor = minuteLabel1.backgroundColor
        secondLabel1.textColor = minuteLabel1.textColor
        secondLabel1.font = minuteLabel1.font
        secondLabel1.textAlignment = minuteLabel1.textAlignment
        self.addSubview(secondLabel1)
        
        //秒显示个位
        secondLabel2 = UILabel(frame: CGRect(x: secondLabel1.frame.origin.x + secondLabel1.frame.width + 4 * screenScale, y: minuteLabel1.frame.origin.y, width: minuteLabel1.frame.width, height: minuteLabel1.frame.height))
        secondLabel2.layer.masksToBounds = true
        secondLabel2.layer.cornerRadius = minuteLabel1.layer.cornerRadius
        secondLabel2.backgroundColor = minuteLabel1.backgroundColor
        secondLabel2.textColor = minuteLabel1.textColor
        secondLabel2.font = minuteLabel1.font
        secondLabel2.textAlignment = minuteLabel1.textAlignment
        self.addSubview(secondLabel2)
        
        let spaceLabel2 = UILabel(frame: CGRect(x: secondLabel2.frame.origin.x + secondLabel2.frame.width + (spaceWidth - spaceLabel1.frame.width)/2, y: spaceLabel1.frame.origin.y, width: spaceLabel1.frame.width, height: spaceLabel1.frame.height))
        spaceLabel2.text = spaceLabel1.text
        spaceLabel2.textColor = spaceLabel1.textColor
        spaceLabel2.font = spaceLabel1.font
        self.addSubview(spaceLabel2)
        
        //毫秒显示十位
        millisecondLabel1 = UILabel(frame: CGRect(x: secondLabel2.frame.origin.x + secondLabel2.frame.width + spaceWidth, y: minuteLabel1.frame.origin.y, width: minuteLabel1.frame.width, height: minuteLabel1.frame.height))
        millisecondLabel1.layer.masksToBounds = true
        millisecondLabel1.layer.cornerRadius = minuteLabel1.layer.cornerRadius
        millisecondLabel1.backgroundColor = minuteLabel1.backgroundColor
        millisecondLabel1.textColor = minuteLabel1.textColor
        millisecondLabel1.font = minuteLabel1.font
        millisecondLabel1.textAlignment = minuteLabel1.textAlignment
        self.addSubview(millisecondLabel1)
        
        //毫秒显示个位
        millisecondLabel2 = UILabel(frame: CGRect(x: millisecondLabel1.frame.origin.x + millisecondLabel1.frame.width + 4 * screenScale, y: minuteLabel1.frame.origin.y, width: minuteLabel1.frame.width, height: minuteLabel1.frame.height))
        millisecondLabel2.layer.masksToBounds = true
        millisecondLabel2.layer.cornerRadius = minuteLabel1.layer.cornerRadius
        millisecondLabel2.backgroundColor = minuteLabel1.backgroundColor
        millisecondLabel2.textColor = minuteLabel1.textColor
        millisecondLabel2.font = minuteLabel1.font
        millisecondLabel2.textAlignment = minuteLabel1.textAlignment
        self.addSubview(millisecondLabel2)
        
        //设置初始时间
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
        minuteLabel1.text = String(format:"%02d", timeline / (60 * 1000))[0]
        minuteLabel2.text = String(format:"%02d", timeline / (60 * 1000))[1]
        secondLabel1.text = String(format:"%02d", (timeline % (60 * 1000)) / 1000)[0]
        secondLabel2.text = String(format:"%02d", (timeline % (60 * 1000)) / 1000)[1]
        millisecondLabel1.text = String(format:"%02d", (timeline % 1000) / 10)[0]
        millisecondLabel2.text = String(format:"%02d", (timeline % 1000) / 10)[1]
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
