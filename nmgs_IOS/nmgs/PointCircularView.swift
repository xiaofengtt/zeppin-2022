//
//  PointCircularView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/7.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class ProgressCurcuitView: UIView {
    
    override func draw(_ rect: CGRect) {
        let components: [CGFloat] = [0/255, 148/255, 255/255, 1.0, 0/255, 148/255, 255/255, 0.0]
        let space = CGColorSpaceCreateDeviceRGB()   
        let gradient = CGGradient(colorSpace: space, colorComponents: components, locations: nil, count: 2)
        let context = UIGraphicsGetCurrentContext()
        let center = CGPoint(x: rect.width/2, y: rect.height/2)
        context?.drawRadialGradient(gradient!, startCenter: center, startRadius: rect.width/3, endCenter: center, endRadius: rect.width/2, options: CGGradientDrawingOptions.drawsBeforeStartLocation)
        bgAnimation()
    }
    
    func bgAnimation(){
        let opacityAnimaton = CAKeyframeAnimation(keyPath: "opacity")
        opacityAnimaton.keyTimes = [0, 0.5, 1]
        opacityAnimaton.values = [1, 0, 1]
        let animation = CAAnimationGroup()
        animation.animations = [opacityAnimaton]
        animation.duration = 2
        animation.repeatCount = HUGE
        animation.isRemovedOnCompletion = false
        self.layer.add(animation, forKey: "animation")
    }
}
