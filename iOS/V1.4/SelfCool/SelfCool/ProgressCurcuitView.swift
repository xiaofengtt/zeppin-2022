//
//  ProgressView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/24.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ProgressCurcuitView: UIView {
    var endAngle : CGFloat?
    let pi : CGFloat = 3.1415926
    func radians(degrees :CGFloat) -> CGFloat {
        return degrees * pi / 180;
    }
    
    override func drawRect(rect: CGRect) {
        let innerRadius : CGFloat = rect.width / 2 - 4
        let outerRadius : CGFloat = rect.width / 2 - 6
        let data = radians(endAngle!)
        
        let ctx = UIGraphicsGetCurrentContext();
        let center : CGPoint = CGPointMake(rect.width/2, rect.height/2)
        CGContextSetFillColorWithColor(ctx, UIColor(red: 0, green: 100/255, blue: 0, alpha: 1).CGColor)
        CGContextSetLineWidth(ctx, 1)
        CGContextSetLineCap(ctx, CGLineCap.Round)
        CGContextSetAllowsAntialiasing(ctx, true)
        let path :CGMutablePathRef = CGPathCreateMutable()
        CGPathAddRelativeArc(path, nil, center.x, center.y, innerRadius, pi / 2, data)
        CGPathAddRelativeArc(path, nil
            , center.x, center.y, outerRadius, data + (pi / 2), -data)
        CGPathAddLineToPoint(path, nil, center.x, center.y - innerRadius)
        CGContextAddPath(ctx, path)
        CGContextFillPath(ctx)
        let endPoint = self.getEndPoint(center, radians: (innerRadius + outerRadius) / 2, angel: data)
        CGContextAddArc(ctx, endPoint.x , endPoint.y, 3, 0, 2 * pi, 0)
        CGContextDrawPath(ctx, CGPathDrawingMode.Fill)
        CGContextSetFillColorWithColor(ctx, UIColor(red: 0, green: 100/255, blue: 0, alpha: 0.4).CGColor)
        CGContextAddArc(ctx, endPoint.x , endPoint.y, 5, 0, 2 * pi, 0)
        CGContextDrawPath(ctx, CGPathDrawingMode.Fill)
    }
    
    func getEndPoint(center: CGPoint , radians : CGFloat , angel : CGFloat) ->CGPoint{
        let rAngel = angel + pi
        let x = center.x + radians * sin(rAngel)
        let y = center.y - radians * cos(rAngel)
        return CGPoint(x: x, y: y)
    }
    
    func setEndAngle(endAngle:CGFloat){
        self.endAngle = endAngle
    }

}