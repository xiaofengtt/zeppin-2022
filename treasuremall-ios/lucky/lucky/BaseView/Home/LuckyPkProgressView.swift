//
//  LuckyPkProgressView.swift
//  lucky
//  PK进度条
//  Created by Farmer Zhu on 2020/9/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPkProgressView: UIView{
    var bgLayer: CALayer!
    var progressBar: UIView!
    var progressLayer: CAGradientLayer?
    
    //队伍
    var team: PkTeam!
    //队伍位置
    var alignment: PkAlignment!
    //开奖状态
    var isFailed : Bool = false
    
    var rate: Double{
        //设置百分比
        willSet{
            if(alignment == PkAlignment.left){
                //左方队
                progressBar.frame = CGRect(x: 0, y: 0, width: self.frame.width * CGFloat(newValue), height: self.frame.height)
                progressBar.setCornerRadius(byRoundingCorners: [UIRectCorner.topRight, UIRectCorner.bottomRight], cornerRadius: frame.height/2)
            }else{
                //右方队
                progressBar.frame = CGRect(x: self.frame.width - (self.frame.width * CGFloat(newValue)), y: 0, width: self.frame.width * CGFloat(newValue), height: self.frame.height)
                progressBar.setCornerRadius(byRoundingCorners: [UIRectCorner.topLeft, UIRectCorner.bottomLeft], cornerRadius: frame.height/2)
            }
            //重绘实际进度条
            progressLayer?.removeFromSuperlayer()
            progressLayer = CAGradientLayer()
            progressLayer!.frame = CGRect(origin: CGPoint.zero, size: progressBar.frame.size)
            progressLayer!.backgroundColor = UIColor.red.cgColor
            if(isFailed){
                //已开奖并失败
                progressLayer!.colors = [UIColor(red: 222/255, green: 222/255, blue: 222/255, alpha: 1).cgColor, UIColor(red: 179/255, green: 179/255, blue: 179/255, alpha: 1).cgColor]
            }else{
                //其他状态
                if(team == PkTeam.red){
                    //红队
                    progressLayer!.colors = [UIColor(red: 0.93, green: 0.01, blue: 0.31, alpha: 1).cgColor, UIColor(red: 1, green: 0.51, blue: 0.86, alpha: 1).cgColor]
                }else{
                    //蓝队
                    progressLayer!.colors = [UIColor(red: 0.3, green: 0.7, blue: 1, alpha: 1).cgColor, UIColor(red: 0.17, green: 0.29, blue: 1, alpha: 1).cgColor]
                }
            }
            progressLayer!.locations = [0, 1]
            progressLayer!.startPoint = CGPoint.zero
            progressLayer!.endPoint = CGPoint(x: 1, y: 0)
            progressBar.layer.addSublayer(progressLayer!)
        }
    }
    
    //队伍枚举
    enum PkTeam {
        case red
        case blue
    }
    
    //所在位置枚举
    enum PkAlignment {
        case left
        case right
    }
    
    init(frame: CGRect, team: PkTeam, alignment: PkAlignment) {
        self.rate = 0
        super.init(frame: frame)
        self.alignment = alignment
        self.team = team
        self.layer.masksToBounds = true
        if(alignment == PkAlignment.left){
            //左侧队伍
            self.setCornerRadius(byRoundingCorners: [UIRectCorner.topRight, UIRectCorner.bottomRight], cornerRadius: frame.height/2)
        }else{
            //右侧队伍
            self.setCornerRadius(byRoundingCorners: [UIRectCorner.topLeft, UIRectCorner.bottomLeft], cornerRadius: frame.height/2)
        }
        
        //进度条底框
        bgLayer = CALayer()
        bgLayer.frame = CGRect(origin: CGPoint.zero, size: frame.size)
        bgLayer.backgroundColor = UIColor.white.withAlphaComponent(0.25).cgColor
        self.layer.addSublayer(bgLayer)
        
        //实际进度
        progressBar = UIView(frame: CGRect(x: 0, y: 0, width: 0, height: frame.height))
        progressBar.layer.masksToBounds = true
        progressBar.backgroundColor = UIColor.yellow
        self.addSubview(progressBar)
    }
    
    required init(coder aDecoder: NSCoder) {
        self.rate = 0
        super.init(coder: aDecoder)!
    }
    
}
