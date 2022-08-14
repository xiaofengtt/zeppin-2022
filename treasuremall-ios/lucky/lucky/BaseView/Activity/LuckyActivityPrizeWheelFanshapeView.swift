//
//  LuckyActivityPrizeWheelFanshapeView.swift
//  lucky
//  活动 积分转盘 商品扇形背景
//  Created by Farmer Zhu on 2020/10/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityPrizeWheelFanshapeView : UIView{
    
    private var color: UIColor!
    private var angle: CGFloat!
    
    init(frame: CGRect, color: UIColor, angle: CGFloat) {
        self.color = color
        self.angle = angle
        super.init(frame: frame)
        self.backgroundColor = UIColor.clear
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    override func draw(_ rect: CGRect) {
        let bgColor = color
        bgColor!.set()
        let path = UIBezierPath(arcCenter: CGPoint(x: frame.width/2, y: frame.height/2), radius: self.frame.height/2 * 0.87, startAngle: (CGFloat)(-1 * angle/360 * CGFloat.pi) - CGFloat.pi/2, endAngle: (CGFloat)(angle/360 * CGFloat.pi) - CGFloat.pi/2, clockwise: true)
        path.addLine(to: CGPoint(x: frame.width/2, y: frame.height/2))
        path.close()
        path.lineWidth = 1
        path.fill()
    }
}
