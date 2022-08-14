//
//  LuckyDottedLine.swift
//  lucky
//  虚线
//  Created by Farmer Zhu on 2020/8/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDottedLine : UIView{
    
    var color: UIColor!
    var direction: LuckyDottedLineDirection!
    
    enum LuckyDottedLineDirection{
        case horizontal
        case vertical
    }
    
    init(frame: CGRect, color: UIColor, direction: LuckyDottedLineDirection) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.clear
        self.color = color
        self.direction = direction
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    override func draw(_ rect: CGRect) {
        self.backgroundColor = UIColor.clear
        let lineWidht = (direction == LuckyDottedLineDirection.horizontal) ? self.frame.height : self.frame.width
        
        let context:CGContext = UIGraphicsGetCurrentContext()!
        
        context.setLineCap(CGLineCap.square)
        
        let lengths:[CGFloat] = [lineWidht*2, lineWidht*4]
        
        context.setStrokeColor(color.cgColor)
        context.setLineWidth(lineWidht)
        context.setLineDash(phase: 0, lengths: lengths)
        context.move(to: CGPoint(x: 0, y: 0))
        if(direction == LuckyDottedLineDirection.horizontal){
            context.addLine(to: CGPoint(x: self.frame.width, y: 0))
        }else{
            context.addLine(to: CGPoint(x: 0, y: self.frame.height))
        }
        context.strokePath()
    }
}
