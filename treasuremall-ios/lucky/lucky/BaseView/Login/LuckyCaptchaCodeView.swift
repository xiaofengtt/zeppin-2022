//
//  LuckyCaptchaCodeView.swift
//  lucky
//  图形验证码
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
class LuckyCaptchaCodeView : UIView{
    var mainView = UIView()
    override init(frame: CGRect) {
        super.init(frame: frame)
        mainView.frame = CGRect(origin: CGPoint.zero, size: frame.size)
        self.addSubview(mainView)
        self.backgroundColor = UIColor(red: 49/255, green: 43/255, blue: 86/255, alpha: 1)
    }
    
    //加载验证码
    func load(captcha: String){
        mainView.removeFromSuperview()
        mainView = UIView(frame: mainView.frame)
        self.addSubview(mainView)
        let length: CGFloat = CGFloat(captcha.length)
        //画三条随机这档线
        for _ in 0 ..< 3{
            let path = UIBezierPath()
            let px = CGFloat(Int.random(from: 0, to: Int(self.frame.width/2)))
            let py = CGFloat(Int.random(from: 0, to: Int(self.frame.height)))
            path.move(to: CGPoint(x: px, y: py))
            let ptx =  self.frame.width/2 + CGFloat(Int.random(from: 0, to: Int(self.frame.width/2)))
            let pty = CGFloat(Int.random(from: 0, to: Int(self.frame.height)))
            path.addLine(to: CGPoint(x: ptx, y: pty))
            let layer = CAShapeLayer()
            layer.strokeColor = UIColor.white.cgColor
            layer.lineWidth = 1 * screenScale
            layer.strokeEnd = 1
            layer.fillColor = UIColor.clear.cgColor
            layer.path = path.cgPath
            mainView.layer.addSublayer(layer)
        }
        //循环绘制验证码
        for i in 0 ..< captcha.length{
            let label = UILabel(frame: CGRect(x: CGFloat(i) * frame.width/length, y: 0, width: frame.width/length, height: frame.height))
            label.text = captcha[i]
            label.textColor = UIColor.white
            let fontSize = Int.random(from: Int(14 * screenScale), to: Int(24 * screenScale))
            label.font = UIFont.mediumFont(size: CGFloat(fontSize))
            label.sizeToFit()
            var randWidth = Int(frame.width/length - CGFloat(label.frame.width))
            if(randWidth < 1){
                randWidth = 0
            }
            var randHeight = Int(frame.height - CGFloat(label.frame.height))
            if(randHeight < 0){
                randHeight = 0
            }
            label.frame.origin = CGPoint(x: CGFloat(i) * frame.width/length + CGFloat(Int.random(from: 0, to: randWidth)), y: CGFloat(Int.random(from: 0, to: randHeight)))
            //旋转
            let r = Double(Int.random(from: 0, to: 8))
            label.transform = CGAffineTransform(rotationAngle: CGFloat(r-4)/10)
            mainView.addSubview(label)
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
